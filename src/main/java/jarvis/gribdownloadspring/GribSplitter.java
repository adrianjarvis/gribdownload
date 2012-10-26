package jarvis.gribdownloadspring;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.camel.EndpointInject;
import org.apache.camel.Message;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.file.GenericFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ucar.grib.grib2.*;
import ucar.unidata.io.RandomAccessFile;

/*
 * #%L
 * Camel for GFS data via FTP
 * %%
 * Copyright (C) 2012 Adrian Jarvis
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
public class GribSplitter {

    private static final Logger LOGGER = LoggerFactory.getLogger(GribSplitter.class);
    private File outputDirectory;
    @EndpointInject(uri="direct:split")
    ProducerTemplate producerTemplate;
    
    public GribSplitter(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public void setProducerTemplate(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }

    public void splitMessage(Message message) throws IOException {
        GenericFile file = message.getBody(GenericFile.class);
        Object fileObject = file.getFile();
        if (fileObject instanceof File) {
            splitFile((File) fileObject);
        } else {
            LOGGER.warn("Unable to handle file of type {}", fileObject.getClass());
        }
    }
    
    public void splitFile(File file) throws IOException {
        RandomAccessFile randomAccessFile = null;
        int fileCounter = 0;
        try {
            randomAccessFile = new RandomAccessFile(file.getAbsolutePath(), "r");
            Grib2Input grib2Input = new Grib2Input(randomAccessFile);
            randomAccessFile.seek(0L);
            grib2Input.scan(false, false);
            List<Grib2Record> records = grib2Input.getRecords();
            for (Grib2Record record : records) {
                byte[] recordBytes = readRecordAsBytes(record, randomAccessFile);
                String newFileName = file.getName() + "_" + fileCounter;
                File newFile = new File(outputDirectory, newFileName);
                writeNewFile(newFile, recordBytes);
                Map<String, Object> headers = createHeaders(record); 
                headers.put("Original File Name", file.getName());
                producerTemplate.sendBodyAndHeaders(newFile, headers);
                fileCounter++;
            }
        } finally {
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
        }
        LOGGER.info("Split file {} into {} records", file.getName(), fileCounter);
    }

    private byte[] readRecordAsBytes(Grib2Record record, RandomAccessFile randomAccessFile) throws IOException {
        Grib2IndicatorSection is = record.getIs();
        long startPosition = is.getStartPos();
        long endPosition = is.getEndPos();
        randomAccessFile.seek(startPosition);
        final long recordLength = endPosition - startPosition;
        byte[] recordBytes = randomAccessFile.readBytes((int) recordLength);
        randomAccessFile.seek(endPosition);
        return recordBytes;
    }

    
    private void writeNewFile(File newFile, byte[] recordBytes) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(newFile);
            fileOutputStream.write(recordBytes);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
        LOGGER.debug("Wrote {} bytes to file {}", recordBytes.length, newFile.getName());
    }

    private Map<String, Object> createHeaders(Grib2Record record) {
        Map<String, Object> result = new HashMap<String, Object>();
        Grib2ProductDefinitionSection pdS = record.getPDS();
        Grib2Pds pdsVars = pdS.getPdsVars();
        result.put("Forecast Date", pdsVars.getForecastDate());
        result.put("Forecast Time", pdsVars.getForecastTime());
        result.put("Parameter Category", pdsVars.getParameterCategory());
        result.put("Parameter Number", pdsVars.getParameterNumber());
        return result;
    }
}

package jarvis.gribdownloadspring;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ucar.grib.grib2.*;
import ucar.unidata.io.RandomAccessFile;

/*
 * #%L
 * Camel for GFS data via FTP
 * %%
 * Copyright (C) 2012 Adrian Jarvis Software
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

    public GribSplitter(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }
    
    public List<File> splitFile(File file) throws IOException {
        List<File> result = new ArrayList<File>();
        RandomAccessFile randomAccessFile = null;
        int fileCounter = 0;
        try {
            randomAccessFile = new RandomAccessFile(file.getAbsolutePath(), "r");
            Grib2Input grib2Input = new Grib2Input(randomAccessFile);
            randomAccessFile.seek(0L);
            grib2Input.scan(false, false);
            List<Grib2Record> records = grib2Input.getRecords();
            for (Grib2Record record : records) {
                Grib2IndicatorSection is = record.getIs();
                long startPosition = is.getStartPos();
                long endPosition = is.getEndPos();
                randomAccessFile.seek(startPosition);
                final long recordLength = endPosition - startPosition;
                byte[] recordBytes = randomAccessFile.readBytes((int) recordLength);
                fileCounter++;
                String newFileName = file.getName() + "_" + fileCounter;
                File newFile = new File(outputDirectory, newFileName);
                writeNewFile(newFile, recordBytes);
                result.add(newFile);
                randomAccessFile.seek(endPosition);
            }
        } finally {
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
        }
        return result;
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
}

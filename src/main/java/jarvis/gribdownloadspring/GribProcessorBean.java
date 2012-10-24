package jarvis.gribdownloadspring;

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


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import org.apache.camel.Message;
import org.apache.camel.component.file.GenericFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ucar.unidata.io.RandomAccessFile;
import ucar.grib.GribChecker;

/**
 *
 * @author Adge
 */
public class GribProcessorBean {

    public static final String FILE_MODE_READONLY = "r";
    private static final Logger LOGGER = LoggerFactory.getLogger(GribProcessorBean.class);

    public void process(Message message) {
        GenericFile file = message.getBody(GenericFile.class);
        final String filePath = file.getAbsoluteFilePath();
        RandomAccessFile accessFile = null;
        try {
            accessFile = new RandomAccessFile(filePath, FILE_MODE_READONLY);
            int edition = GribChecker.getEdition(accessFile);
            LOGGER.info("file: {}, edition {}", filePath, edition);
        } catch (IOException ex) {
            LOGGER.error("Unable to read file: " + filePath, ex);
        } finally {
            if (accessFile != null) {
                try {
                accessFile.close();
                } catch (IOException ex) {
                    LOGGER.warn("Unable to close file", ex);
                }
            }
        }
    }
}

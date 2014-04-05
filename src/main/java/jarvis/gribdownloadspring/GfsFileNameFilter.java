package jarvis.gribdownloadspring;

/*
 * #%L
 * Camel for GFS data via FTP
 * %%
 * Copyright (C) 2012 - 2014 Adrian Jarvis
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


import java.util.regex.Pattern;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Adge
 */
public class GfsFileNameFilter<T> implements GenericFileFilter<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericFileFilter.class);

    @Override
    public boolean accept(GenericFile<T> file) {
        boolean result = false;
        if (file.isDirectory()) {
            if (Pattern.matches("RD\\.\\d{8}", file.getFileName())) {
                result = true;
            } else if (Pattern.matches("RD\\.\\d{8}/PT\\.grid_DF\\.gr2", file.getFileName())) {
                result = true;
            }
        } else if (Pattern.matches("fh\\.000\\d_tl\\.press_gr\\.1p0deg", file.getFileNameOnly())) {
            result = true;
        }
        if (result) {
            LOGGER.info("file: {}, {}", file.getFileName(), file.getFileNameOnly());
        }
        return result;
    }
    
}

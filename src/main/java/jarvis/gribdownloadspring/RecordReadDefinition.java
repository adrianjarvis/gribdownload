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


import ucar.grib.grib2.Grib2Record;
import ucar.unidata.io.RandomAccessFile;

public class RecordReadDefinition {
    private final Grib2Record record;
    private final RandomAccessFile randomAccessFile;

    public RecordReadDefinition(Grib2Record record, RandomAccessFile randomAccessFile) {
        this.record = record;
        this.randomAccessFile = randomAccessFile;
    }

    public Grib2Record getRecord() {
        return record;
    }

    public RandomAccessFile getRandomAccessFile() {
        return randomAccessFile;
    }
}

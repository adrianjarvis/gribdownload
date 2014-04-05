package jarvis.gribdownloadspring;

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

package jarvis.gribdownloadspring;

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

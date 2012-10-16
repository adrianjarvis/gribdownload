package jarvis.gribdownloadspring;

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

    public GfsFileNameFilter() {
        LOGGER.info("Created!");
    }

    
    
    @Override
    public boolean accept(GenericFile<T> file) {
        boolean result = false;
        if (file.isDirectory()) {
            if (Pattern.matches("RD\\.\\d{8}", file.getFileName())) {
                result = true;
            } else if (Pattern.matches("RD\\.\\d{8}/PT.grid_DF.gr2", file.getFileName())) {
                result = true;
            }
        } else if (Pattern.matches("fh\\.00\\d\\d_tl\\.press_gr\\.1p0deg", file.getFileNameOnly())) {
            result = true;
        }
        if (result) {
            LOGGER.info("file: {}, {}", file.getFileName(), file.getFileNameOnly());
        }
        return result;
    }
    
}

package jarvis.gribdownloadspring;

import org.apache.camel.Message;
import org.apache.camel.component.file.GenericFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Adge
 */
public class GribProcessorBean {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GribProcessorBean.class);
    
    public void process(Message message) {
        LOGGER.info("Received message {}:{}", message.getMessageId(), message.getHeaders());
        LOGGER.info("Body: {}", message.getBody());
        GenericFile file = message.getBody(GenericFile.class);
        LOGGER.info("File type {}", file.getFile().getClass().getName());
    }
    
}

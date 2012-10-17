package jarvis.gribdownloadspring;

import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Adge
 */
public class GfsRouteBuilder  extends SpringRouteBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(GfsRouteBuilder.class);
    
    @Override
    public void configure() throws Exception {
        from("ftp://tgftp.nws.noaa.gov/SL.us008001/ST.opnl/MT.gfs_CY.00/?recursive=true&filter=#gfsfilter")
                .to("file://target/temp_grib?recursive=true").to("bean:gribProcessing");
        LOGGER.info("Route defined");
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jarvis.gribdownloadspring;

import org.apache.camel.builder.RouteBuilder;

/**
 *
 * @author Adge
 */
public class GfsRouteBuilder  extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("ftp://tgftp.nws.noaa.gov/SL.us008001/ST.opnl/MT.gfs_CY.00/?recursive=true&filter=#gfsfilter")
                .log("Downloading file")
                .to("file://target/temp_grib?recursive=true");
    }
    
}

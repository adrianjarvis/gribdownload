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
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GfsRouteBuilder  extends SpringRouteBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(GfsRouteBuilder.class);
    
    @Override
    public void configure() throws Exception {
        from("ftp://tgftp.nws.noaa.gov/SL.us008001/ST.opnl/MT.gfs_CY.00/?recursive=true&filter=#gfsfilter")
                .to("file://target/temp_grib?recursive=true");
        from("file://target/temp_grib?recursive=true").split().method("gribSplitter").to("file://target/done");
    }
    
}

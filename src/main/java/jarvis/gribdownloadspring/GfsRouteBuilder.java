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


import org.apache.camel.Predicate;
import org.apache.camel.spring.SpringRouteBuilder;

public class GfsRouteBuilder  extends SpringRouteBuilder {

    @Override
    public void configure() throws Exception {
        Predicate isWantedParameter = header("Parameter Number").in(0,1,2);
        from("ftp://tgftp.nws.noaa.gov/SL.us008001/ST.opnl/MT.gfs_CY.12/?recursive=true&filter=#gfsfilter")
            .to("file://target/temp_grib");
        from("file://target/temp_grib?recursive=true").to("bean:gribSplitter");
        from("direct:split").log("${header[Parameter]}, ${header[Forecast Date]}, ${header[Level1]}-${header[Level2]}")
                .choice()
                    .when(isWantedParameter).to("file:target/special_subset?fileName=${header[Original File Name]}_${header[Record Number]}")
                .otherwise()
                    .to("file:target/the_rest?fileName=${header[Original File Name]}_${header[Record Number]}").end();
                
    }
    
}

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


import java.io.File;
import java.io.IOException;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Adge
 */
@Configuration
public class AppConfig extends SingleRouteCamelConfiguration {

    @Bean
    public GfsFileNameFilter gfsfilter() {
        return new GfsFileNameFilter();
    }
    
    @Bean
    public GribSplitter gribSplitter() throws IOException {
        String pathname = "target/split";
        final File splitterOutputDir = new File(pathname);
        if (!splitterOutputDir.mkdir()) {
            throw new IOException("Unable to create directory " + pathname);
        }

        return new GribSplitter(splitterOutputDir);
    }
    
    @Override
    public RouteBuilder route() {
        return new GfsRouteBuilder();
 }
}

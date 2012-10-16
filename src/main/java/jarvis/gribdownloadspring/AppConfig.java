/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jarvis.gribdownloadspring;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.springframework.beans.factory.InitializingBean;
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
    
    @Override
    public RouteBuilder route() {
        return new GfsRouteBuilder();
 }
}
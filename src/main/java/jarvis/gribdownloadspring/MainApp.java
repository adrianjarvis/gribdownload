/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jarvis.gribdownloadspring;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author Adge
 */
public class MainApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String... args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        SpringCamelContext bean = context.getBean(SpringCamelContext.class);
        bean.start();
        
    }
}

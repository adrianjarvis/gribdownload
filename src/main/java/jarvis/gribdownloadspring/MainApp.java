package jarvis.gribdownloadspring;

import org.apache.camel.spring.javaconfig.Main;

/**
 *
 * @author Adge
 */
public class MainApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String... args) throws Exception {
        Main main = new Main();
        main.setConfigClassesString(AppConfig.class.getName());
        main.run(args);
    }
}

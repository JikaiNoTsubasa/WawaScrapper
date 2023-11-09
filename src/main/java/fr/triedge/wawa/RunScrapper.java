package fr.triedge.wawa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;

@SpringBootApplication
@EnableScheduling
public class RunScrapper extends SpringBootServletInitializer {

    public static void main(String[] args) {
        System.setProperty("https.protocols","TLSv1.2");
        int port = 8085;
        if (args.length > 0){
            for (int i = 0; i < args.length-1; i++) {
                if (args[i].equalsIgnoreCase("-port")){
                    if (args[i+1] != null){
                        port = Integer.parseInt(args[i+1]);
                    }
                }
            }
        }
        SpringApplication app = new SpringApplication(RunScrapper.class);
        Properties prop = new Properties();
        prop.setProperty("server.port", String.valueOf(port));
        app.setDefaultProperties(prop);
        app.run(args);
    }


}

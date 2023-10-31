package fr.triedge.wawa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class RunScrapper {

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
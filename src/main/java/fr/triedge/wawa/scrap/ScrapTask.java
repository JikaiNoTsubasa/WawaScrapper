package fr.triedge.wawa.scrap;

import fr.triedge.wawa.database.DB;
import fr.triedge.wawa.model.Entry;
import fr.triedge.wawa.utils.Vars;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.FixedRateTask;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class ScrapTask {

    @Scheduled(fixedRate = 3600000)
    public void execute(){
        if (Vars.SCRAP_ENABLED){
            PageScrapper scrapper = new PageScrapper();
            try {
                System.out.println("Scrapping website "+Vars.WEBSITE+" ...");
                ArrayList<Entry> entries = scrapper.scrapWebsite(50);
                System.out.println("Scrapping finished");
                for (Entry e : entries){
                    Entry dbEnt = DB.getInstance().getEntry(e.getId());
                    if (dbEnt == null){
                        DB.getInstance().insertEntry(e);
                        System.out.println("Inserted: "+e);
                    }
                }
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Finished task");

        }else{
            System.out.println("Task disabled");
        }
    }
}

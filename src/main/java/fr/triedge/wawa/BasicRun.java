package fr.triedge.wawa;

import fr.triedge.wawa.model.Entry;
import fr.triedge.wawa.scrap.PageScrapper;
import fr.triedge.wawa.scrap.ScrapTask;

import java.io.IOException;
import java.util.ArrayList;

public class BasicRun {
    public static void main(String[] args) {
        PageScrapper ps = new PageScrapper();
        //ps.parse("https://www.wawacity.kim/?p=films&s=blu-ray_1080p-720p");
        //ps.parse("https://www.wawacity.kim/?p=films&s=blu-ray_1080p-720p&page=2");
        //ArrayList<Entry> result = ps.scrapWebsite("https://www.wawacity.kim", 10);
        //result.forEach(System.out::println);
        //System.out.println("Total: "+result.size());
        ScrapTask st = new ScrapTask();
        st.execute();
    }
}

package fr.triedge.wawa.api;

import fr.triedge.wawa.database.DB;
import fr.triedge.wawa.model.Entry;
import fr.triedge.wawa.tpl.Template;
import fr.triedge.wawa.utils.Vars;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class SearchController {

    @GetMapping("/searchentry")
    public ModelAndView searchEntry(
            @RequestParam(value = "word")String word
    ){
        System.out.println("Search requested for: "+word);
        ModelAndView model = new ModelAndView("searchResult.html");
        if (word != null){
            try {
                Template tpl = new Template("/html/homeTable.html");
                StringBuilder tmp = new StringBuilder();
                ArrayList<Entry> results = DB.getInstance().search(word);
                System.out.println("Result count: "+results.size());
                for (Entry e : results){
                    tpl.setParameter("##IMG##", Vars.WEBSITE+e.getImage());
                    tpl.setParameter("##URL##",Vars.WEBSITE+e.getUrl());
                    tpl.setParameter("##TITLE##",e.getTitle());
                    tmp.append(tpl.generate());
                    System.out.println("Found: "+e);
                }
                model.addObject("results", tmp.toString());
            } catch (SQLException e) {
                model.addObject("error", "Could not get results. "+e.getMessage());
                e.printStackTrace();
            }
        }else{
            System.out.println("Search term is null");
        }
        return model;
    }
}

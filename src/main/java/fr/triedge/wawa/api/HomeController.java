package fr.triedge.wawa.api;

import fr.triedge.wawa.database.DB;
import fr.triedge.wawa.model.Entry;
import fr.triedge.wawa.scrap.PageScrapper;
import fr.triedge.wawa.tpl.Template;
import fr.triedge.wawa.utils.Vars;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class HomeController {

    @GetMapping("/")
    public ModelAndView home(){
        ModelAndView model = new ModelAndView("home.html");

        try {
            ArrayList<Entry> list = DB.getInstance().getEntriesByPage(0, 100);
            model.addObject("blogs", list);
            model.addObject("website", Vars.WEBSITE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return model;
    }

    @GetMapping("/ajaxhome")
    public ResponseEntity<?> ajaxHome(
            @RequestParam(value = "page", required = false)Integer page,
            @RequestParam(value = "max", required = false)Integer max
    ){
        System.out.println("Ajax entries");
        int currentPage = (page != null)?page:0;
        int perPage = (max != null)?max:Vars.PERPAGE;

        Template tpl = new Template("/html/homeTable.html");
        StringBuilder tmp = new StringBuilder();

        try {
            ArrayList<Entry> list = DB.getInstance().getEntriesByPage(currentPage, perPage);
            for (Entry e : list){
                tpl.setParameter("##IMG##",Vars.WEBSITE+e.getImage());
                tpl.setParameter("##URL##",Vars.WEBSITE+e.getUrl());
                tpl.setParameter("##TITLE##",e.getTitle());
                tmp.append(tpl.generate());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(tmp.toString());
    }

}

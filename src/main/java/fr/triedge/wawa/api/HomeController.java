package fr.triedge.wawa.api;

import fr.triedge.wawa.database.DB;
import fr.triedge.wawa.model.Entry;
import fr.triedge.wawa.scrap.PageScrapper;
import fr.triedge.wawa.utils.Vars;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
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

}

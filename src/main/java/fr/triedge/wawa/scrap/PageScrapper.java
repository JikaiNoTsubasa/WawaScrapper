package fr.triedge.wawa.scrap;

import fr.triedge.wawa.model.Entry;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class PageScrapper {

    public final String URL_COMPLETE                = "?p=films&s=blu-ray_1080p-720p";
    public final String URL_PAGE                    = "&page=";

    public ArrayList<Entry> parse(String url) throws IOException {
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36 Edg/114.0.1823.82";
        Document doc = Jsoup.connect(url).userAgent(userAgent).sslSocketFactory(SSLHelper.socketFactory()).get();
        ArrayList<Entry> entries = new ArrayList<>();

        Elements blocks = doc.getElementsByClass("wa-sub-block");
        Iterator<Element> it = blocks.iterator();
        while(it.hasNext()){
            Entry ent = new Entry();
            Element e = it.next();

            // Title and link
            Elements titleel = e.getElementsByClass("wa-sub-block-title");
            String u = titleel.select("a").attr("href");
            String titleStr = Jsoup.parse(titleel.html()).text();
            ent.setTitle(titleStr);
            ent.setUrl(u);

            // ID
            String idlink = u.replace("?p=film&id=","").split("-")[0];
            ent.setId(Integer.parseInt(idlink));

            // Image
            ent.setImage(e.getElementsByClass("img-responsive").attr("src"));

            entries.add(ent);

        }
        return entries;
    }

    public ArrayList<Entry> scrapWebsite(String url, int pages) throws IOException {
        ArrayList<Entry> entries = new ArrayList<>();
        String fullUrl = url+=URL_COMPLETE;
        entries.addAll(parse(fullUrl));
        if (pages > 1){
            for (int p = 2;p<=pages;++p){
                entries.addAll(parse(fullUrl + URL_PAGE + p));
            }
        }
        return entries;
    }
}

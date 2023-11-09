package fr.triedge.wawa.model;

import fr.triedge.wawa.utils.Vars;

public class Entry {
    private int id;
    private String url;
    private String title;
    private String image;
    private String category = Vars.CAT_FILM;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}

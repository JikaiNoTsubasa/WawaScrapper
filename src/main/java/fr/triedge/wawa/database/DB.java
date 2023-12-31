package fr.triedge.wawa.database;

import fr.triedge.wawa.model.Entry;
import fr.triedge.wawa.utils.PWDManager;

import java.sql.*;
import java.util.ArrayList;

public class DB {
    private static DB instance;
    private Connection connection;

    private DB(){}

    public static DB getInstance(){
        if (instance == null){
            instance = new DB();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()){
            resetConnection();
        }
        return connection;
    }

    public void resetConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        PWDManager manager = new PWDManager();
        String pwd = manager.decode("JGJpdXNlclMjODg=");
        String host = "triedge.ovh";
        if (System.getProperty("host") != null){
            host = System.getProperty("host");
        }
        connection = DriverManager.getConnection("jdbc:mysql://"+host+"/amadeus","amadeus",pwd);
    }

    public void insertEntry(Entry ent) throws SQLException {
        String sql = "insert into wawa_entry(entry_id,entry_title,entry_url,entry_img,entry_category)values(?,?,?,?,?)";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.setInt(1,ent.getId());
        stmt.setString(2,ent.getTitle());
        stmt.setString(3,ent.getUrl());
        stmt.setString(4,ent.getImage());
        stmt.setString(5,ent.getCategory());
        stmt.executeUpdate();
        stmt.close();
    }

    public Entry getEntry(int id) throws SQLException {
        Entry ent = null;
        String sql = "select * from wawa_entry where entry_id=?";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.setInt(1,id);
        ResultSet res = stmt.executeQuery();
        if (res.next()){
            ent = createEntry(res);
        }
        res.close();
        stmt.close();
        return ent;
    }

    public ArrayList<Entry> getEntriesByPage(int currentPage, int maxPerPage) throws SQLException {
        ArrayList<Entry> entries = new ArrayList<>();
        String sql = "select * from wawa_entry order by entry_id desc limit ? offset ?";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.setInt(1,maxPerPage);
        stmt.setInt(2,currentPage);
        ResultSet res = stmt.executeQuery();
        while (res.next()){
            entries.add(createEntry(res));
        }
        res.close();
        stmt.close();
        return entries;
    }

    private Entry createEntry(ResultSet res) throws SQLException {
        Entry ent = new Entry();
        ent.setId(res.getInt("entry_id"));
        ent.setTitle(res.getString("entry_title"));
        ent.setUrl(res.getString("entry_url"));
        ent.setImage(res.getString("entry_img"));
        ent.setCategory(res.getString("entry_category"));
        return ent;
    }

    public ArrayList<Entry> search(String word) throws SQLException {
        if (word == null || word.length() == 0)
            return null;
        ArrayList<Entry> entries = new ArrayList<>();
        String sql = "select * from wawa_entry  where lower(entry_title) like ? order by entry_id";
        PreparedStatement stmt = getConnection().prepareStatement(sql);
        stmt.setString(1,"%"+word.toLowerCase()+"%");
        ResultSet res = stmt.executeQuery();
        while (res.next()){
            entries.add(createEntry(res));
        }
        res.close();
        stmt.close();
        return entries;
    }
}

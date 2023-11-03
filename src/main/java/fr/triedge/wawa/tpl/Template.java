package fr.triedge.wawa.tpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Template {
    private String path;
    private String originalFileContent;
    private HashMap<String, String> parameters = new HashMap<>();

    public Template(String path){
        this.path = path;
        try {
            this.originalFileContent = readHtmlFile(this.path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String generate() {
        //System.out.println(this.originalFileContent);
        String text = this.originalFileContent;
        for(Map.Entry<String, String> e : this.parameters.entrySet()){
            text = text.replaceAll(e.getKey(), e.getValue());
        }
        return text;
    }

    public Template setParameter(String key, String value){
        if (key != null){
            if (value == null){
                value = "";
            }
            this.parameters.put(key, value);
        }
        return this;
    }

    public Template setParameter(String key, int value){
        if (key != null)
            this.parameters.put(key, String.valueOf(value));
        return this;
    }

    private String readHtmlFile(final String name) throws FileNotFoundException {
        File file = getResourceFile(name);
        Scanner scan = new Scanner(file);
        StringBuilder tmp = new StringBuilder();
        while(scan.hasNextLine()){
            tmp.append(scan.nextLine());
        }
        scan.close();
        return tmp.toString();
    }

    private File getResourceFile(final String fileName)
    {
        URL url = this.getClass()
                .getClassLoader()
                .getResource(fileName);
        if(url == null) {
            throw new IllegalArgumentException(fileName + " is not found 1");
        }
        File file = new File(url.getFile());
        return file;
    }
}

package com.example.user.kanbanapp;

public class File {
    private String name;
    private String link;
    public File(){}

    public File(String name, String link){
        super();
        this.name = name;
        this.link = link;
    }
    public String getName() {
        return name;
    }
    public String getLink() {
        return link;
    }
}

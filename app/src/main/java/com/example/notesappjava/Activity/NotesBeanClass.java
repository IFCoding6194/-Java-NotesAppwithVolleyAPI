package com.example.notesappjava.Activity;

public class NotesBeanClass {
    private String title,dec,id;

    public NotesBeanClass(String title, String dec, String id) {
        this.title = title;
        this.dec = dec;
        this.id = id;
    }

    public NotesBeanClass(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

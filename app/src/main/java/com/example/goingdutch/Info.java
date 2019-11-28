package com.example.goingdutch;

import java.io.Serializable;
import java.util.ArrayList;

public class Info implements Serializable {
    String title, list;
    ArrayList<String> content;

    public Info(String title, String list, ArrayList<String> content){
        this.title = title;
        this.list = list;
        this.content = content;
    }

    public String getTitle(){
        return title;
    }

    public String getList(){
        return list;
    }

    public ArrayList<String> getContent(){
        return content;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setList(String list){
        this.list = list;
    }

    public void setContent(ArrayList<String> content){
        this.content = content;
    }
}

package com.ivshinaleksei.githubviewer.domain;


import java.util.Date;

public class Comment {
    public String title;
    public String message;
    public Date createdDate;

    public Comment(){}

    public Comment(String title,String message,Date createdDate){
        this.title = title;
        this.message = message;
        this.createdDate = createdDate;
    }
}

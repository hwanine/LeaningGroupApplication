package com.example.leaninggroupapplication;

public class Comments {
    String commentWriter;
    String comment;


    public String getCommentWriter() {
        return commentWriter;
    }

    public void setCommentWriter(String commentWriter) {
        this.commentWriter = commentWriter;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Comments(String commentWriter, String comment) {
        this.commentWriter = commentWriter;
        this.comment = comment;
    }
}

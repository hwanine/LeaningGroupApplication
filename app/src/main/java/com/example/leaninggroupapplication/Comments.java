package com.example.leaninggroupapplication;

public class Comments {
    String commentWriter;
    String comment;
    String comment_time;

    public String getCommentWriter() { //댓글 작성자
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

    public Comments(String commentWriter, String comment,String comment_time) {
        this.commentWriter = commentWriter;
        this.comment = comment;
        this.comment_time =comment_time;
    }
}

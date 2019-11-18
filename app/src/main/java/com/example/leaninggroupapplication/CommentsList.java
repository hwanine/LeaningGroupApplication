package com.example.leaninggroupapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CommentsList extends BaseAdapter {
    ArrayList<Comments> comments;
    Context context;

    public CommentsList(ArrayList<Comments> comments, Context context){
        this.comments = comments;
        this.context = context;
    }

    //댓글 수
    @Override
    public int getCount() {
        return comments.size();
    }

    //데이터 보내기
    @Override
    public Object getItem(int i) {
        return comments.get(i);
    }

    //위치
    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.comments,viewGroup,false);
        }

        TextView commentWriter= view.findViewById(R.id.commentWriter);
        TextView comment=view.findViewById(R.id.comment);

        commentWriter.setText(comments.get(i).commentWriter);
        comment.setText(comments.get(i).comment);

        return view;
    }


}

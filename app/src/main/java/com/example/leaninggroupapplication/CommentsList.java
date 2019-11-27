package com.example.leaninggroupapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CommentsList extends BaseAdapter {
    ArrayList<Comments> comments;
    //Context context;

    public CommentsList(ArrayList<Comments> comments){

        this.comments = comments;
        //this.context = context;
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
    public View getView(int i, View view, ViewGroup parent) {

        /*if(view==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.comments,viewGroup,false);
        }*/
        final Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.comment_list_custom, parent, false);

        TextView commentWriter= (TextView)view.findViewById(R.id.comment_userNicknameTextview);
        TextView comment=(TextView)view.findViewById(R.id.comment_comment_content);
        TextView commentTime=(TextView)view.findViewById(R.id.comment_dateTime);

        //계속 갱신 시킬것
        commentWriter.setText(comments.get(i).commentWriter);
        Log.d("호출",comments.get(i).commentWriter);
        comment.setText(comments.get(i).comment);
        Log.d("호출2",comments.get(i).comment);
        commentTime.setText(comments.get(i).comment_time);

        return view;
    }


}

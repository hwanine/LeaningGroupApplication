package com.example.leaninggroupapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    LayoutInflater inflater = null;
    private ArrayList<createGroupSummaryObject> groupSummaryObject;
   // private ArrayList<CheckAttendingActivity> checkAttenders = null;

    private int listCntGroupSummary = 0;
    //private int listCntCheckAttender = 0;

    public ListAdapter(ArrayList<createGroupSummaryObject> groupSummaryObject){

        this.groupSummaryObject = groupSummaryObject;
        //listCntGroupSummary = groupSummaryObjects.size();

    }

    @Override
    public int getCount(){
        return groupSummaryObject.size();
    }

    @Override
    public Object getItem(int position){
        return groupSummaryObject.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView == null){

            final Context context = parent.getContext();
            if(inflater == null){
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.activity_create_group_summary_object, parent, false);
        }

        TextView Title = (TextView) convertView.findViewById(R.id.Title);
        TextView GroupDate = (TextView) convertView.findViewById(R.id.GroupDate);
        TextView Writer = (TextView) convertView.findViewById(R.id.Writer);
        TextView GroupNum = (TextView) convertView.findViewById(R.id.GroupNumber);
        TextView GroupMemOfNumber = (TextView) convertView.findViewById(R.id.GroupNumOfMem);

        Title.setText(groupSummaryObject.get(position).Title);
        GroupDate.setText(groupSummaryObject.get(position).GroupDate);
        Writer.setText(groupSummaryObject.get(position).Writer);
        GroupNum.setText(groupSummaryObject.get(position).GroupNum);
        GroupMemOfNumber.setText(groupSummaryObject.get(position).GroupNumOfMem);

        return convertView;
    }

}

package com.example.leaninggroupapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CheckAttendingListAdapter extends BaseAdapter{ //모임시 참석한 사람을 볼 수 있도록 하는 참여자 목록에 대한 정보를 리스트뷰로 만드는 어댑터

    LayoutInflater inflater = null;
    private ArrayList<CheckAttendingActivity> checkAttendingActivities;
    private int listCntCheckAttender = 0;

    public CheckAttendingListAdapter(ArrayList<CheckAttendingActivity>  checkAttendingActivities){

        this.checkAttendingActivities = checkAttendingActivities;

    }

    @Override
    public int getCount(){
        return checkAttendingActivities.size();
    }

    @Override
    public Object getItem(int position){
        return checkAttendingActivities.get(position);
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
            convertView = inflater.inflate(R.layout.activity_check_attending, parent, false);
        }

        TextView Name = (TextView) convertView.findViewById(R.id.attenderName);
        TextView EMail = (TextView) convertView.findViewById(R.id.attenderEmail);
        TextView SchoolNumber = (TextView) convertView.findViewById(R.id.attenderSchoolNumber);
        Button ReportOn = (Button) convertView.findViewById(R.id.reportButton);

        Name.setText(checkAttendingActivities.get(position).Name);
        EMail.setText(checkAttendingActivities.get(position).EMail);
        SchoolNumber.setText(checkAttendingActivities.get(position).SchoolNumber);
        ReportOn.setOnClickListener(checkAttendingActivities.get(position).ReportOn);

        convertView.setTag(""+position);
        return convertView;
    }


}

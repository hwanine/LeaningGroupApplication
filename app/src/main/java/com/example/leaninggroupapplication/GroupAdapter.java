package com.example.leaninggroupapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class GroupAdapter extends BaseAdapter {
    private final List<GroupList> mData;

    public GroupAdapter(List<GroupList> mData) {
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_logcheck_item, parent, false);

            TextView numText = (TextView) convertView.findViewById(R.id.group_num);
            TextView numName = (TextView) convertView.findViewById(R.id.group_name);
            TextView numType = (TextView) convertView.findViewById(R.id.group_type);
            TextView numDate = (TextView) convertView.findViewById(R.id.group_date);
            TextView numstTime = (TextView) convertView.findViewById(R.id.group_starttime);
            TextView numenTime = (TextView) convertView.findViewById(R.id.group_endtime);
            holder.numText = numText;
            holder.numName = numName;
            holder.numType = numType;
            holder.numDate = numDate;
            holder.numstTime = numstTime;
            holder.numenTime = numenTime;
            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();

        GroupList group = mData.get(position);
        holder.numText.setText(group.getGroup_num());
        holder.numName.setText(group.getGroup_name());
        holder.numType.setText(group.getGroup_type());
        holder.numDate.setText(group.getGroup_date());
        holder.numstTime.setText(group.getGroup_endtime());
        holder.numenTime.setText(group.getGroup_starttime());
        return convertView;
    }

    static class ViewHolder{
        TextView numText;
        TextView numName;
        TextView numType;
        TextView numDate;
        TextView numstTime;
        TextView numenTime;
    }
}
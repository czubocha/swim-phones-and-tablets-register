package com.example.tomaszczubocha.cw_4_2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tomasz Czubocha on 12.04.2016.
 */
public class RowAdapter extends ArrayAdapter<RowBean> {

    Context context;
    int layoutResourceId;
    ArrayList<RowBean> data = null;

    public RowAdapter(Context context, int layoutResourceId, ArrayList<RowBean> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RowBeanHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RowBeanHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.tekstekranu = (TextView) row.findViewById(R.id.tekstekranu);
            holder.tekstpudelka = (TextView) row.findViewById(R.id.tekstpudelka);
            holder.layout = (FrameLayout) row.findViewById(R.id.row_layout);

            row.setTag(holder);
        }
        else
        {
            holder = (RowBeanHolder)row.getTag();
        }

        RowBean object = data.get(position);
        holder.txtTitle.setText(object.title);
        holder.imgIcon.setImageResource(object.icon);
        holder.tekstekranu.setText(object.ekran);
        holder.tekstpudelka.setText(object.pudelko);
        holder.layout.setBackgroundColor(object.kolor);

        return row;
    }

    static class RowBeanHolder
    {
        FrameLayout layout;
        ImageView imgIcon;
        TextView txtTitle;
        TextView tekstekranu;
        TextView tekstpudelka;
    }

}

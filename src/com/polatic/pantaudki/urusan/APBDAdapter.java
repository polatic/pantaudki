/*
 * Copyright (C) PT. Polatic Informatika Indonesia
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.polatic.pantaudki.urusan;

import com.polatic.pantaudki.R;
import com.polatic.pantaudki.model.APBDModel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class APBDAdapter extends BaseAdapter {
    Context mContext;
    List<APBDModel> listAPBDModel;
    private ViewHolder holder;

    public APBDAdapter(Context context, List<APBDModel> listAPBDModel) {
        this.mContext = context;
        this.listAPBDModel = listAPBDModel;
    }

    private class ViewHolder {
        TextView titleTextView;
    }

    public void updateList(List<APBDModel> listAPBDModel) {
        // TODO Auto-generated method stub
        this.listAPBDModel = listAPBDModel;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listAPBDModel.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listAPBDModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return listAPBDModel.indexOf(getItem(position));
    }

    public void addList(List<APBDModel> listAPBDModel) {
        this.listAPBDModel.addAll(listAPBDModel);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bitmap image = null;

        LayoutInflater mInflater = (LayoutInflater)mContext
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.urusan_list_item, null);
            holder = new ViewHolder();

            holder.titleTextView = (TextView)convertView.findViewById(R.id.urusan_listview_title);
           

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        APBDModel urusan = (APBDModel)getItem(position);
        holder.titleTextView.setText(urusan.getKegiatanName());

        return convertView;
    }
}

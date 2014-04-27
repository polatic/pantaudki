/*
 * Copyright (C) PT. Polatic Informatika Indonesia
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.polatic.pantaudki.urusan;

import com.polatic.pantaudki.R;
import com.polatic.pantaudki.model.UrusanModel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class UrusanAdapter extends BaseAdapter {
    Context mContext;
    List<UrusanModel> listUrusanModel;
    private ViewHolder holder;

    public UrusanAdapter(Context context, List<UrusanModel> listUrusanModel) {
        this.mContext = context;
        this.listUrusanModel = listUrusanModel;
    }

    private class ViewHolder {
        TextView titleTextView;
    }

    public void updateList(List<UrusanModel> listUrusanModel) {
        // TODO Auto-generated method stub
        this.listUrusanModel = listUrusanModel;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listUrusanModel.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listUrusanModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return listUrusanModel.indexOf(getItem(position));
    }

    public void addList(List<UrusanModel> listUrusanModel) {
        this.listUrusanModel.addAll(listUrusanModel);
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

        UrusanModel urusan = (UrusanModel)getItem(position);
        holder.titleTextView.setText(urusan.getName());

        return convertView;
    }
}

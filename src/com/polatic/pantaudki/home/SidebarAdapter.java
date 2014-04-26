/*
 * Copyright (C) PT. Polatic Informatika Indonesia
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package com.polatic.pantaudki.home;

import com.polatic.pantaudki.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SidebarAdapter extends BaseAdapter {
    Context context;
    List<SidebarItem> sidebarItem;

    public SidebarAdapter(Context context, List<SidebarItem> sidebarItem) {
        this.context = context;
        this.sidebarItem = sidebarItem;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView textCart;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return sidebarItem.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return sidebarItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return sidebarItem.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
            holder = new ViewHolder();
            holder.titleTextView = (TextView) convertView.findViewById(R.id.sidebar_title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.sidebar_image);
            holder.textCart = (TextView)convertView.findViewById(R.id.sidebar_counter);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SidebarItem sidebar = (SidebarItem) getItem(position);
        holder.titleTextView.setText(sidebar.getTitle());
        holder.imageView.setImageResource(sidebar.getImageId());
        if (sidebar.isCounterVisible()==true) {
            holder.textCart.setText(sidebar.getTextCart());
        } else {
            holder.textCart.setVisibility(View.GONE);
        }
        return convertView;
    }

}

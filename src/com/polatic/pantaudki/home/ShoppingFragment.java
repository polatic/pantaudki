/*
 * 	   Created by Daniel Nadeau
 * 	   daniel.nadeau01@gmail.com
 * 	   danielnadeau.blogspot.com
 * 
 * 	   Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.polatic.pantaudki.home;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.echo.holographlibrary.Line;
import com.echo.holographlibrary.LineGraph;
import com.echo.holographlibrary.BarGraph.OnBarClickedListener;
import com.echo.holographlibrary.LineGraph.OnPointClickedListener;
import com.echo.holographlibrary.LinePoint;
import com.polatic.pantaudki.R;

public class ShoppingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.home_fragment_shopping, container, false);
        final Resources resources = getResources();
        ArrayList<Bar> aBars = new ArrayList<Bar>();

        Bar bar = new Bar();
        bar.setColor(resources.getColor(R.color.green_light));
        bar.setSelectedColor(resources.getColor(R.color.transparent_orange));
        bar.setName("PEGAWAI");
        bar.setValue(11919);
        bar.setValueString("11,919  Milyar");
        aBars.add(bar);

        bar = new Bar();
        bar.setColor(resources.getColor(R.color.purple));
        bar.setName("BUNGA");
        bar.setValue(4);
        bar.setValueString("4 Milyar");
        aBars.add(bar);

        bar = new Bar();
        bar.setColor(resources.getColor(R.color.red));
        bar.setName("HIBAH");
        bar.setValue(2617);
        bar.setValueString("2,617 Milyar");
        aBars.add(bar);

        bar = new Bar();
        bar.setColor(resources.getColor(R.color.main_blue_light));
        bar.setName("SOSIAL");
        bar.setValue(1220);
        bar.setValueString("1,220 Milyar");
        aBars.add(bar);
        
        BarGraph barGraph = (BarGraph) v.findViewById(R.id.first_bargraph);
        barGraph.setBars(aBars);

        barGraph.setOnBarClickedListener(new OnBarClickedListener() {

            @Override
            public void onClick(int index) {
                Toast.makeText(getActivity(),
                        "Bar " + index + " clicked",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
        
        aBars = new ArrayList<Bar>();

        bar = new Bar();
        bar.setColor(resources.getColor(R.color.green_light));
        bar.setSelectedColor(resources.getColor(R.color.transparent_orange));
        bar.setName("BANTUAN KEUANGAN");
        bar.setValue(36);
        bar.setValueString("36 Milyar");
        aBars.add(bar);

        bar = new Bar();
        bar.setColor(resources.getColor(R.color.purple));
        bar.setName("TIDAK TERDUGA");
        bar.setValue(78);
        bar.setValueString("78 Milyar");
        aBars.add(bar);

        barGraph = (BarGraph) v.findViewById(R.id.second_bargraph);
        barGraph.setBars(aBars);
        
        aBars = new ArrayList<Bar>();

        bar = new Bar();
        bar.setColor(resources.getColor(R.color.green_light));
        bar.setSelectedColor(resources.getColor(R.color.transparent_orange));
        bar.setName("PEGAWAI");
        bar.setValue(2865);
        bar.setValueString("2,865 Milyar");
        aBars.add(bar);

        bar = new Bar();
        bar.setColor(resources.getColor(R.color.purple));
        bar.setName("BARANG & JASA");
        bar.setValue(17104);
        bar.setValueString("17,104 Milyar");
        aBars.add(bar);
        

        bar = new Bar();
        bar.setColor(resources.getColor(R.color.purple));
        bar.setName("MODAL");
        bar.setValue(2386);
        bar.setValueString("2,386 Milyar");
        aBars.add(bar);

        barGraph = (BarGraph) v.findViewById(R.id.third_bargraph);
        barGraph.setBars(aBars);
        
        return v;
    }
}

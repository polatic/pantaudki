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

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.echo.holographlibrary.BarGraph.OnBarClickedListener;
import com.polatic.pantaudki.R;

import java.util.ArrayList;

public class IncomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.home_fragment_income, container, false);
        final Resources resources = getResources();
        ArrayList<Bar> aBars = new ArrayList<Bar>();

        Bar bar = new Bar();
        bar.setColor(resources.getColor(R.color.green_light));
        bar.setSelectedColor(resources.getColor(R.color.transparent_orange));
        bar.setName("Pajak Daerah");
        bar.setValue(32500000);
        bar.setValueString("32,500 Milyar");
        aBars.add(bar);

        bar = new Bar();
        bar.setColor(resources.getColor(R.color.purple));
        bar.setName("Retribusi");
        bar.setValue(1746418);
        bar.setValueString("1,746 Milyar");
        aBars.add(bar);

        bar = new Bar();
        bar.setColor(resources.getColor(R.color.red));
        bar.setName("Pengelolaan");
        bar.setValue(447550);
        bar.setValueString("447 Milyar");
        aBars.add(bar);

        bar = new Bar();
        bar.setColor(resources.getColor(R.color.main_red));
        bar.setName("Lain-Lain");
        bar.setValue(4850246);
        bar.setValueString("4,850 Milyar");
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
        bar.setColor(resources.getColor(R.color.main_blue));
        bar.setSelectedColor(resources.getColor(R.color.transparent_orange));
        bar.setName("Dana Hasil");
        bar.setValue(17684);
        bar.setValueString("17,684 Milyar");
        aBars.add(bar);

        bar = new Bar();
        bar.setColor(resources.getColor(R.color.main_red));
        bar.setName("Dana Alokasi Umum");
        bar.setValue(86);
        bar.setValueString("86 Milyar");
        aBars.add(bar);

        barGraph = (BarGraph) v.findViewById(R.id.second_bargraph);
        barGraph.setBars(aBars);
        
        aBars = new ArrayList<Bar>();

        bar = new Bar();
        bar.setColor(resources.getColor(R.color.main_blue_light));
        bar.setSelectedColor(resources.getColor(R.color.transparent_orange));
        bar.setName("Pendapatan Hibah");
        bar.setValue(5000);
        bar.setValueString("5,000 Milyar");
        aBars.add(bar);

        bar = new Bar();
        bar.setColor(resources.getColor(R.color.red_light));
        bar.setName("Penyesuaian dan Otonomi");
        bar.setValue(2386);
        bar.setValueString("2,386 Milyar");
        aBars.add(bar);

        barGraph = (BarGraph) v.findViewById(R.id.third_bargraph);
        barGraph.setBars(aBars);
        
        return v;
    }
}

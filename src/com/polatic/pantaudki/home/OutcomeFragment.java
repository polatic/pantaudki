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
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;

import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.BarGraph.OnBarClickedListener;
import com.echo.holographlibrary.PieGraph.OnSliceClickedListener;
import com.echo.holographlibrary.PieSlice;
import com.polatic.pantaudki.R;

public class OutcomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.home_fragment_outcome, container, false);
        final Resources resources = getResources();
        ArrayList<Bar> aBars = new ArrayList<Bar>();

        Bar bar = new Bar();
        bar.setColor(resources.getColor(R.color.green_light));
        bar.setSelectedColor(resources.getColor(R.color.transparent_orange));
        bar.setName("Sisa Anggaran Sebelumnya");
        bar.setValue(7015);
        bar.setValueString("7,015 Milyar");
        aBars.add(bar);

        bar = new Bar();
        bar.setColor(resources.getColor(R.color.purple));
        bar.setName("Penerimaan Pinjaman Daerah");
        bar.setValue(269);
        bar.setValueString("269 Milyar");
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
        bar.setColor(resources.getColor(R.color.main_blue_light));
        bar.setSelectedColor(resources.getColor(R.color.transparent_orange));
        bar.setName("Penyertaan Modal");
        bar.setValue(7108);
        bar.setValueString("7,108 Milyar");
        aBars.add(bar);

        bar = new Bar();
        bar.setColor(resources.getColor(R.color.red_light));
        bar.setName("Pembayaran Pokok Utang");
        bar.setValue(9);
        bar.setValueString("9 Milyar");
        aBars.add(bar);

        barGraph = (BarGraph) v.findViewById(R.id.second_bargraph);
        barGraph.setBars(aBars);
        
        return v;
    }
}

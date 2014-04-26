/*
 * Copyright (C) PT. Polatic Informatika Indonesia
 *
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */

package com.polatic.pantaudki.guideline;

import com.polatic.pantaudki.R;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class GuidelineOrderFragment extends Fragment {

    private TextView guidelineTitle;
    private TextView guidelineDesc;
    private ImageView guidelineImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.guideline_layout, container, false);
        guidelineTitle = (TextView) view.findViewById(R.id.guideline_title);
        guidelineDesc = (TextView) view.findViewById(R.id.guideline_desc);
        guidelineImage = (ImageView) view.findViewById(R.id.guideline_image);

        guidelineTitle.setText(getString(R.string.guideline_order_title));
        guidelineDesc.setText(Html.fromHtml(getString(R.string.guideline_order_description)));
        Picasso.with(getActivity()).load(R.drawable.guide_order)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error).into(guidelineImage);
        // guidelineImage.setImageResource(R.drawable.guide_basket);
        return view;
    }

    public static GuidelineOrderFragment newInstance() {
        GuidelineOrderFragment guidelineFragment4 = new GuidelineOrderFragment();
        return guidelineFragment4;
    }
}

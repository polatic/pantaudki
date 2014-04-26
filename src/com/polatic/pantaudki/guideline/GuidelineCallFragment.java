
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

public class GuidelineCallFragment extends Fragment {
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

        guidelineTitle.setText(getString(R.string.guideline_call));
        guidelineDesc.setText(Html.fromHtml(getString(R.string.guideline_call_description)));
        
        Picasso.with(getActivity()).load(R.drawable.guide_call)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error).into(guidelineImage);
        // guidelineImage.setImageResource(R.drawable.guide_call);
        return view;
    }

    public static GuidelineCallFragment newInstance() {
        GuidelineCallFragment guidelineFragment1 = new GuidelineCallFragment();
        return guidelineFragment1;
    }
}

package com.mark.sleevecoach.Helps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mark.sleevecoach.R;

/**
 * Created by user1 on 4/6/2017.
 */
public class FiveDayResetFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fivedayreset, container, false);
        TextView _txtContent = (TextView)rootView.findViewById(R.id.fiveday_textcont);
        _txtContent.setText(Html.fromHtml(getResources().getString(R.string.str_fivedayreset)));
        _txtContent.setMovementMethod(LinkMovementMethod.getInstance());
        return rootView;
    }
}

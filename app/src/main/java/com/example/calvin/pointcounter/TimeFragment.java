package com.example.calvin.pointcounter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by calvin on 6/6/15.
 */

public class TimeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View abc = inflater.inflate(R.layout.fragment_time, container, false);
        TextView text = (TextView)abc.findViewById(R.id.time_time);
        text.setVisibility(View.GONE);
        return abc;
    }

}
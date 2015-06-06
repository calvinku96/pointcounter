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

public class PointFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View abc =  inflater.inflate(R.layout.fragment_point, container, false);
        TextView pointScoreText = (TextView) abc.findViewById(R.id.point_score);
        pointScoreText.setVisibility(View.GONE);
        return abc;
    }

}
package com.ctrctr.pointcounter;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by calvin on 6/6/15.
 */

public class ChessFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View abc = inflater.inflate(R.layout.fragment_chess, container, false);
        LinearLayout toplayout = (LinearLayout) abc.findViewById(R.id.chess_top_time_layout);
        toplayout.setVisibility(View.GONE);
        LinearLayout bottomlayout = (LinearLayout) abc.findViewById(R.id.chess_bottom_time_layout);
        bottomlayout.setVisibility(View.GONE);
        return abc;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

}

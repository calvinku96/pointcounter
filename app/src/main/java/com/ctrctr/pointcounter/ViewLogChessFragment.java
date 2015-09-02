package com.ctrctr.pointcounter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by calvin on 9/2/15.
 */
public class ViewLogChessFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View abc = inflater.inflate(R.layout.fragment_view_log_chess, container, false);
        Bundle args = getArguments();
        TextView view_log_chess = (TextView) abc.findViewById(R.id.view_log_chess);
        view_log_chess.setText(args.getString("chess"));
        return abc;
    }
}

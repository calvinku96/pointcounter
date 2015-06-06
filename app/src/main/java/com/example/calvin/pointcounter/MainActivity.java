package com.example.calvin.pointcounter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //prevent keyboard automatically popup
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_view_log) {
            ViewLog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void ViewLog() {
        //View Log
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return new PointFragment();
                case 1:
                    return new TimeFragment();
                case 2:
                    return new ChessFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_point).toUpperCase(l);
                case 1:
                    return getString(R.string.title_time).toUpperCase(l);
                case 2:
                    return getString(R.string.title_chess).toUpperCase(l);
            }
            return null;
        }
    }


    /**
     * *****************
     * *Methods for Point**
     * *****************
     */
    public void pointStart(View view) {
        TextView pointScoreText = (TextView) findViewById(R.id.point_score);
        LinearLayout pointSetMaxLayout = (LinearLayout) findViewById(R.id.point_setmax_layout);
        pointSetMaxLayout.setVisibility(View.GONE);
        pointScoreText.setVisibility(View.VISIBLE);

        TextView pointTimeView = (TextView) findViewById(R.id.point_time);
        pointTimeView.setText("00:00.0");
        //Start
    }

    public void pointReset(View view) {
        TextView pointScoreText = (TextView) findViewById(R.id.point_score);
        LinearLayout pointSetMaxLayout = (LinearLayout) findViewById(R.id.point_setmax_layout);
        pointSetMaxLayout.setVisibility(View.VISIBLE);
        pointScoreText.setVisibility(View.GONE);
        //Reset
    }

    public void pointLeftScore(View view) {
        //Left Player gets the Point
    }

    public void pointRightScore(View view) {
        //Right Player gets the Point
    }


    /*******************
     **Methods for Time**
     ******************/
    public void timeLeftScore(View view){
        //Left Player gets the Point
    }
    public void timeRightScore(View view){
        //Right Player gets the Point
    }
    public void timeStart(View view) {
        EditText minstring = (EditText) findViewById(R.id.time_init_min);
        EditText secstring = (EditText) findViewById(R.id.time_init_sec);
        if(!(minstring.getText().toString().equals("")||secstring.getText().toString().equals(""))) {
            TextView timetext = (TextView) findViewById(R.id.time_time);
            timetext.setVisibility(View.VISIBLE);
            LinearLayout settimelayout = (LinearLayout) findViewById(R.id.time_set_time_layout);
            settimelayout.setVisibility(View.GONE);
            //Start
        }else{
            Toast.makeText(this,getString(R.string.time_start_not_filled),Toast.LENGTH_SHORT).show();
        }
    }
    public void timeReset(View view){
        TextView timetext = (TextView)findViewById(R.id.time_time);
        timetext.setVisibility(View.GONE);
        LinearLayout settimelayout = (LinearLayout)findViewById(R.id.time_set_time_layout);
        settimelayout.setVisibility(View.VISIBLE);
        //Reset
    }

    /**
     * *****************
     * *Methods for Chess**
     * *****************
     */
    public void chessStart(View vew) {
        EditText topminstring = (EditText) findViewById(R.id.chess_top_init_min);
        EditText topsecstring = (EditText) findViewById(R.id.chess_top_init_sec);
        EditText bottomminstring = (EditText) findViewById(R.id.chess_bottom_init_min);
        EditText bottomsecstring = (EditText) findViewById(R.id.chess_bottom_init_sec);
        if (!(topminstring.getText().toString().equals("") || topsecstring.getText().toString().equals("") || bottomminstring.getText().toString().equals("") || bottomsecstring.getText().toString().equals(""))) {
            LinearLayout topinitlayout = (LinearLayout) findViewById(R.id.chess_top_init_layout);
            LinearLayout bottominitlayout = (LinearLayout) findViewById(R.id.chess_bottom_init_layout);
            LinearLayout toptimelayout = (LinearLayout) findViewById(R.id.chess_top_time_layout);
            LinearLayout bottomtimelayout = (LinearLayout) findViewById(R.id.chess_bottom_time_layout);
            topinitlayout.setVisibility(View.GONE);
            bottominitlayout.setVisibility(View.GONE);
            toptimelayout.setVisibility(View.VISIBLE);
            bottomtimelayout.setVisibility(View.VISIBLE);

            //Start
        }
        else{
            Toast.makeText(this,getString(R.string.chess_start_not_filled),Toast.LENGTH_SHORT).show();
        }
    }
    public void chessReset(View view){
        LinearLayout topinitlayout = (LinearLayout) findViewById(R.id.chess_top_init_layout);
        LinearLayout bottominitlayout = (LinearLayout) findViewById(R.id.chess_bottom_init_layout);
        LinearLayout toptimelayout = (LinearLayout) findViewById(R.id.chess_top_time_layout);
        LinearLayout bottomtimelayout = (LinearLayout) findViewById(R.id.chess_bottom_time_layout);
        topinitlayout.setVisibility(View.VISIBLE);
        bottominitlayout.setVisibility(View.VISIBLE);
        toptimelayout.setVisibility(View.GONE);
        bottomtimelayout.setVisibility(View.GONE);

        //Reset
    }
    public void chessTopPress(View view){
        Toast.makeText(this,"Top Pressed",Toast.LENGTH_SHORT).show();
    }
    public void chessBottomPress(View view){
        Toast.makeText(this,"Bottom Pressed",Toast.LENGTH_SHORT).show();
    }

}

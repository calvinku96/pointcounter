package com.example.calvin.pointcounter;

import java.util.Locale;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


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

    //Methods for PointFragment
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


}

package com.example.calvin.pointcounter;

import java.util.Locale;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
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


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        setGone();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        Space pointScoreSpace = (Space) findViewById(R.id.point_score_space);
        pointScoreSpace.setVisibility(View.GONE);
        pointSetMaxLayout.setVisibility(View.GONE);
        pointScoreText.setVisibility(View.VISIBLE);
        //Start
    }

    public void pointReset(View view) {
        TextView pointScoreText = (TextView) findViewById(R.id.point_score);
        LinearLayout pointSetMaxLayout = (LinearLayout) findViewById(R.id.point_setmax_layout);
        Space pointScoreSpace = (Space) findViewById(R.id.point_score_space);
        pointScoreSpace.setVisibility(View.VISIBLE);
        pointSetMaxLayout.setVisibility(View.VISIBLE);
        pointScoreText.setVisibility(View.GONE);
        //Reset
    }

    public void setGone() {
        TextView pointScoreText = (TextView) findViewById(R.id.point_score);
        pointScoreText.setVisibility(View.GONE);
    }


}

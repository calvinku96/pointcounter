package com.example.calvin.pointcounter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
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

    Player pointp1;
    Player pointp2;
    Timer pointtimer;
    boolean pointgamestate;

    public void pointStart(View view) {
        TextView pointScoreText = (TextView) findViewById(R.id.point_score);
        LinearLayout pointSetMaxLayout = (LinearLayout) findViewById(R.id.point_setmax_layout);
        pointSetMaxLayout.setVisibility(View.GONE);
        pointScoreText.setVisibility(View.VISIBLE);
        CheckBox checkBox = (CheckBox) findViewById(R.id.point_deuce_checkBox);

        EditText point_p1name = (EditText) findViewById(R.id.point_p1name);
        EditText point_p1max = (EditText) findViewById(R.id.point_p1max);
        EditText point_p2name = (EditText) findViewById(R.id.point_p2name);
        EditText point_p2max = (EditText) findViewById(R.id.point_p2max);

        TextView pointTimeView = (TextView) findViewById(R.id.point_time);
        pointTimeView.setText("00:00.0");
        pointgamestate = true;
        //Start
        //String name, Initial Score, Max Score, Deuce Checkbox
        pointp1 = new Player(point_p1name.getText().toString(), 0,
                Integer.parseInt(point_p1max.getText().toString()), checkBox.isChecked());
        pointp2 = new Player(point_p2name.getText().toString(), 0,
                Integer.parseInt(point_p2max.getText().toString()), checkBox.isChecked());
        pointtimer = new Timer(pointTimeView, 0l);
        pointtimer.resumeTimer();
    }

    public void pointReset(View view) {
        TextView pointScoreText = (TextView) findViewById(R.id.point_score);
        LinearLayout pointSetMaxLayout = (LinearLayout) findViewById(R.id.point_setmax_layout);
        pointSetMaxLayout.setVisibility(View.VISIBLE);
        pointScoreText.setVisibility(View.GONE);
        //Reset
        if (pointp1 != null) {
            pointp1.resetScore();
            pointp2.resetScore();
        }
    }

    public void pointLeftScore(View view) {
        //Left Player gets the Point
        if (pointgamestate) {
            TextView pointScoreText = (TextView) findViewById(R.id.point_score);

            pointp1.addScore(1);
            pointScoreText.setText(Integer.toString(pointp1.getScore()) + ":" + Integer.toString(pointp2.getScore()));
            checkGameEnd(pointp1, pointp2);
        } else {
            Toast.makeText(this, getString(R.string.point_game_not_start), Toast.LENGTH_SHORT).show();
        }
    }

    public void pointRightScore(View view) {
        //Right Player gets the Point
        if (pointgamestate) {
            TextView pointScoreText = (TextView) findViewById(R.id.point_score);

            pointp2.addScore(1);
            pointScoreText.setText(Integer.toString(pointp1.getScore()) + ":" + Integer.toString(pointp2.getScore()));
            checkGameEnd(pointp2, pointp1);
        } else {
            Toast.makeText(this, getString(R.string.point_game_not_start), Toast.LENGTH_SHORT).show();
        }
    }

    public void checkGameEnd(Player win, Player lose) {
        //End game
        if (win.isScoreMax()) {
            pointtimer.pauseTimer();
            pointgamestate = false;

            //output winner
            Toast.makeText(this, win.getName() + " " + getString(R.string.winner), Toast.LENGTH_SHORT).show();
        }

        //Deuce
        if (win.matchPoint() && lose.matchPoint()) {
            win.deuce(1);
            lose.deuce(1);
            Toast.makeText(this, getString(R.string.deuce), Toast.LENGTH_SHORT).show();
        }

        //Match point
        else if (win.matchPoint()) {
            Toast.makeText(this, getString(R.string.matchpoint), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * ****************
     * *Methods for Time**
     * ****************
     */
    public void timeUp() {
        //Finishes game
    }

    public void timeLeftScore(View view) {
        //Left Player gets the Point
    }

    public void timeRightScore(View view) {
        //Right Player gets the Point
    }

    public void timeStart(View view) {
        EditText minstring = (EditText) findViewById(R.id.time_init_min);
        EditText secstring = (EditText) findViewById(R.id.time_init_sec);
        if (!(minstring.getText().toString().equals("") || secstring.getText().toString().equals(""))) {
            TextView timetext = (TextView) findViewById(R.id.time_time);
            timetext.setVisibility(View.VISIBLE);
            LinearLayout settimelayout = (LinearLayout) findViewById(R.id.time_set_time_layout);
            settimelayout.setVisibility(View.GONE);
            //Start
        } else {
            Toast.makeText(this, getString(R.string.time_start_not_filled), Toast.LENGTH_SHORT).show();
        }
    }

    public void timeReset(View view) {
        TextView timetext = (TextView) findViewById(R.id.time_time);
        timetext.setVisibility(View.GONE);
        LinearLayout settimelayout = (LinearLayout) findViewById(R.id.time_set_time_layout);
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
        } else {
            Toast.makeText(this, getString(R.string.chess_start_not_filled), Toast.LENGTH_SHORT).show();
        }
    }

    public void chessReset(View view) {
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

    public void chessTopPress(View view) {
        Toast.makeText(this, "Top Pressed", Toast.LENGTH_SHORT).show();
    }

    public void chessBottomPress(View view) {
        Toast.makeText(this, "Bottom Pressed", Toast.LENGTH_SHORT).show();
    }

    /**
     * Timer Class
     */
    public class Timer {

        long starttime;
        long elapsedtime;
        long thresholdtime;
        String curtime;
        private final int REFRESH_RATE = 100;
        private Handler mHandler = new Handler();
        TextView currenttime;

        public Timer(TextView currenttime, long thresholdtime) {
            this.starttime = System.currentTimeMillis();
            this.thresholdtime = thresholdtime;
            this.currenttime = currenttime;
            mHandler.removeCallbacks(this.startTimer);
        }

        public void resetTimer() {
            mHandler.removeCallbacks(startTimer);
            elapsedtime = 0;
            currenttime.setText("00:00:0");
        }

        public void pauseTimer() {
            mHandler.removeCallbacks(startTimer);
        }

        public void resumeTimer() {
            starttime = System.currentTimeMillis() - elapsedtime;
            mHandler.removeCallbacks(startTimer);
            mHandler.postDelayed(startTimer, 0);
        }

        public boolean timeLimit() {
            if ((elapsedtime >= thresholdtime) && (thresholdtime != 0l)) {
                return true;
            } else {
                return false;
            }
        }

        private Runnable startTimer = new Runnable() {
            public void run() {
                elapsedtime = System.currentTimeMillis() - starttime;
                curtime = updateTimer(elapsedtime);

                if (timeLimit()) {
                    pauseTimer();
                    //call to main activity
                    timeUp();
                }
                currenttime.setText(curtime);
                mHandler.postDelayed(this, REFRESH_RATE);
            }
        };

        private String updateTimer(float time) {
            long secs, mins;
            String seconds, minutes, milliseconds;

            //For countdown
            if (thresholdtime > 0) {
                time = thresholdtime - time;
            }

            //variable time is in millisecond
            secs = (long) (time / 1000);
            mins = (long) ((time / 1000) / 60);

		/* Convert the seconds to String
         * and format to ensure it has
		 * a leading zero when required
		 */
            secs = secs % 60;
            seconds = String.valueOf(secs);
            if (secs == 0) {
                seconds = "00";
            }
            if (secs < 10 && secs > 0) {
                seconds = "0" + seconds;
            }

		/* Convert the minutes to String and format the String */

            minutes = String.valueOf(mins);
            if (mins == 0) {
                minutes = "00";
            }
            if (mins < 10 && mins > 0) {
                minutes = "0" + minutes;
            }

            milliseconds = String.valueOf((long) time);
            if (milliseconds.length() == 2) {
                milliseconds = "0" + milliseconds;
            }
            if (milliseconds.length() <= 1) {
                milliseconds = "00";
            }
            milliseconds = milliseconds.substring(milliseconds.length() - 3, milliseconds.length() - 2);

		/* Setting the timer text to the elapsed time */
            return minutes + ":" + seconds + "." + milliseconds;
        }
    }
}


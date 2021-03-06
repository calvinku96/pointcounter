package com.ctrctr.pointcounter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
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
    CustomViewPager mViewPager;
    public static String path;
    public boolean paused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (CustomViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //prevent keyboard automatically popup
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        path = getFilesDir().getAbsolutePath();
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
            ViewLogs();
            return true;
        } else if (id == R.id.action_pause) {
            pauseGame();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void ViewLogs() {
        //View Logs
        if (pointgamestate || timegamestate || chessgamestate) {
            Toast.makeText(this, getString(R.string.view_log_string), Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, ViewLog.class);
            startActivity(intent);
        }
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

    public static Drawable getDrawable(Context context, int resource) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(resource);
        } else {
            return context.getResources().getDrawable(resource, null);
        }
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            view.clearFocus();
            InputMethodManager imm;
            imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
    public boolean pointgamestate = false;
    Logs pointlog;

    public void pointStart(View view) throws IOException {
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
        pointScoreText.setText("0:0");
        pointgamestate = true;
        if (pointtimer != null) {
            pointtimer.resetTimer();
            pointtimer = null;
        }
        //Start
        //String name, Initial Score, Max Score, Deuce Checkbox
        pointp1 = new Player(point_p1name.getText().toString(), 0,
                Integer.parseInt(point_p1max.getText().toString()), checkBox.isChecked());
        pointp2 = new Player(point_p2name.getText().toString(), 0,
                Integer.parseInt(point_p2max.getText().toString()), checkBox.isChecked());
        TextView[] pointTimeViewList = {pointTimeView};
        pointtimer = new Timer(pointTimeViewList, Timer.CONST_POINT, 0l);
        pointtimer.resumeTimer();
        pointMakeStatusText(getString(R.string.point_game_started));
        //Logs
        pointlog = new Logs(path + "/point.log");
        pointlog.addCode(Logs.CONST_TWENTY_EQUALS);
        pointlog.addCode(Logs.CONST_LINE_BREAK);
        pointlog.addText(getString(R.string.point_game_started));
        pointlog.addCode(Logs.CONST_LINE_BREAK);
        pointlog.addText(getString(R.string.player1semicolon) + " " + pointp1.getName());
        pointlog.addCode(Logs.CONST_LINE_BREAK);
        pointlog.addText(getString(R.string.player2semicolon) + " " + pointp2.getName());
        pointlog.addCode(Logs.CONST_LINE_BREAK);
        pointlog.addText(getString(R.string.point_use_deuce)
                + " " + String.valueOf(checkBox.isChecked()));
        pointlog.addCode(Logs.CONST_LINE_BREAK);
        pointlog.addCode(Logs.CONST_TWENTY_EQUALS);
        pointlog.addCode(Logs.CONST_LINE_BREAK);
        pointlog.saveLog();
        //Disable Deuce CheckBox
        checkBox.setEnabled(false);

        mViewPager.setPagingEnabled(false);
        paused = false;
        changepausebutton(paused);

        hideKeyboard();
        point_p1name.setEnabled(false);
        point_p2name.setEnabled(false);
    }

    public void pointReset(View view) throws IOException {
        TextView pointScoreText = (TextView) findViewById(R.id.point_score);
        LinearLayout pointSetMaxLayout = (LinearLayout) findViewById(R.id.point_setmax_layout);
        CheckBox checkBox = (CheckBox) findViewById(R.id.point_deuce_checkBox);
        TextView point_p1name = (TextView) findViewById(R.id.point_p1name);
        TextView point_p2name = (TextView) findViewById(R.id.point_p2name);
        pointSetMaxLayout.setVisibility(View.VISIBLE);
        pointScoreText.setVisibility(View.GONE);
        //Reset
        if (pointp1 != null) {
            pointgamestate = false;
            pointScoreText.setText("0:0");
            pointp1.resetScore();
            pointp2.resetScore();
            pointtimer.resetTimer();
            //Logs
            pointlog.addText(getString(R.string.reset));
            pointlog.addCode(Logs.CONST_LINE_BREAK);
            pointlog.addCode(Logs.CONST_TWENTY_EQUALS);
            for (int i = 0; i < 3; i++) {
                pointlog.addCode(Logs.CONST_LINE_BREAK);
            }
            pointlog.saveLog();
            pointp1 = null;
            pointp2 = null;
        }
        checkBox.setEnabled(true);
        mViewPager.setPagingEnabled(true);

        paused = false;
        changepausebutton(paused);

        point_p1name.setEnabled(true);
        point_p2name.setEnabled(true);
    }

    public void pointLeftScore(View view) throws IOException {
        //Left Player gets the Point
        if (pointgamestate && (!paused)) {
            TextView pointTimeView = (TextView) findViewById(R.id.point_time);
            TextView pointScoreText = (TextView) findViewById(R.id.point_score);
            pointp1.addScore(1);
            pointScoreText.setText(Integer.toString(pointp1.getScore())
                    + ":" + Integer.toString(pointp2.getScore()));
            pointMakeStatusText(pointp1.getName() + " " + getString(R.string.scores));
            pointlog.addText(pointp1.getName() + " " + getString(R.string.scored_at_time)
                    + " " + pointTimeView.getText().toString());
            pointlog.addCodeAndSave(Logs.CONST_LINE_BREAK);
            pointChangeLeftRight();
            checkGameEnd(pointp1, pointp2);
        } else {
            Toast.makeText(this, getString(R.string.game_not_start),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void pointRightScore(View view) throws IOException {
        //Right Player gets the Point
        if (pointgamestate && (!paused)) {
            TextView pointTimeView = (TextView) findViewById(R.id.point_time);
            TextView pointScoreText = (TextView) findViewById(R.id.point_score);
            pointp2.addScore(1);
            pointScoreText.setText(Integer.toString(pointp1.getScore())
                    + ":" + Integer.toString(pointp2.getScore()));
            pointMakeStatusText(pointp2.getName() + " " + getString(R.string.scores));
            pointlog.addText(pointp2.getName() + " " + getString(R.string.scored_at_time)
                    + " " + pointTimeView.getText().toString());
            pointlog.addCodeAndSave(Logs.CONST_LINE_BREAK);
            pointChangeLeftRight();
            checkGameEnd(pointp2, pointp1);
        } else {
            Toast.makeText(this, getString(R.string.game_not_start),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void pointChangeLeftRight() throws IOException {
        RadioButton leftradio = (RadioButton) findViewById(R.id.point_radio_left);
        RadioButton rightradio = (RadioButton) findViewById(R.id.point_radio_right);

        if (leftradio.isChecked()) {
            //If left radio button on, set left off and right on
            leftradio.setChecked(false);
            rightradio.setChecked(true);
            pointlog.addText(getString(R.string.point_radio_position)
                    + " " + getString(R.string.point_right));
            pointlog.addCodeAndSave(Logs.CONST_LINE_BREAK);
        } else {
            //If right radio button on, set left on and right off
            leftradio.setChecked(true);
            rightradio.setChecked(false);
            pointlog.addText(getString(R.string.point_radio_position)
                    + " " + getString(R.string.point_left));
            pointlog.addCodeAndSave(Logs.CONST_LINE_BREAK);
        }
    }

    public void checkGameEnd(Player win, Player lose) throws IOException {
        //End game
        if (win.isScoreMax()) {
            pointtimer.pauseTimer();
            pointgamestate = false;

            //output winner
            pointMakeStatusText(win.getName() + " " + getString(R.string.winner));
            pointlog.addText(win.getName() + " " + getString(R.string.winner));
            pointlog.addCode(Logs.CONST_LINE_BREAK);
            pointlog.addCode(Logs.CONST_TWENTY_EQUALS);
            for (int i = 0; i < 3; i++) {
                pointlog.addCode(Logs.CONST_LINE_BREAK);
            }
            pointlog.saveLog();
            mViewPager.setPagingEnabled(true);


            TextView point_p1name = (TextView) findViewById(R.id.point_p1name);
            TextView point_p2name = (TextView) findViewById(R.id.point_p2name);

            point_p1name.setEnabled(true);
            point_p2name.setEnabled(true);
        }

        //Deuce
        if (win.matchPoint() && lose.matchPoint() && win.usedeuce) {
            win.deuce(1);
            lose.deuce(1);
            pointMakeStatusText(getString(R.string.deuce));
            pointlog.addText(getString(R.string.deuce));
            pointlog.addCodeAndSave(Logs.CONST_LINE_BREAK);
        }

        //Match point
        else if (win.matchPoint()) {
            pointMakeStatusText(getString(R.string.matchpoint));
            pointlog.addText(getString(R.string.matchpoint));
            pointlog.addCodeAndSave(Logs.CONST_LINE_BREAK);
        }
    }

    public void pointMakeStatusText(String string) {
        //Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        TextView pointstatustext = (TextView) findViewById(R.id.point_status_text);
        pointstatustext.setVisibility(View.VISIBLE);
        pointstatustext.setText(string);
    }

    /**
     * ****************
     * *Methods for Time**
     * ****************
     */

    public boolean timegamestate = false;
    Player timep1;
    Player timep2;
    Timer timetimer;
    Logs timelog;


    public void timeStart(View view) throws IOException {
        EditText time_init_min = (EditText) findViewById(R.id.time_init_min);
        EditText time_init_sec = (EditText) findViewById(R.id.time_init_sec);

        if (!(time_init_min.getText().toString().equals("")
                || time_init_sec.getText().toString().equals(""))) {
            TextView timetext = (TextView) findViewById(R.id.time_time);
            timetext.setVisibility(View.VISIBLE);
            LinearLayout settimelayout = (LinearLayout) findViewById(R.id.time_set_time_layout);
            settimelayout.setVisibility(View.GONE);

            EditText time_p1name = (EditText) findViewById(R.id.time_p1name);
            EditText time_p2name = (EditText) findViewById(R.id.time_p2name);
            TextView time_time = (TextView) findViewById(R.id.time_time);

            TextView time_score = (TextView) findViewById(R.id.time_score);
            time_time.setText("00:00.0");
            time_score.setText("0:0");

            if (timetimer != null) {
                timetimer.resetTimer();
                timetimer = null;
            }

            timegamestate = true;
            //Start
            //Player Init
            timep1 = new Player(time_p1name.getText().toString(), 0, 0, false);
            timep2 = new Player(time_p2name.getText().toString(), 0, 0, false);
            //calc threshold time
            long timethrestime = Long.parseLong(time_init_min.getText().toString()) * 60000
                    + Long.parseLong(time_init_sec.getText().toString()) * 1000;
            //Init Timer
            TextView[] time_time_list = {time_time};
            timetimer = new Timer(time_time_list, Timer.CONST_TIME, timethrestime);
            timetimer.resumeTimer();
            //Make statustext
            timeMakeStatusText(getString(R.string.time_game_started));
            //Logs
            timelog = new Logs(path + "/time.log");
            timelog.addCode(Logs.CONST_TWENTY_EQUALS);
            timelog.addCode(Logs.CONST_LINE_BREAK);
            timelog.addText(getString(R.string.time_game_started));
            timelog.addCode(Logs.CONST_LINE_BREAK);
            timelog.addText(getString(R.string.player1semicolon) + " " + timep1.getName());
            timelog.addCode(Logs.CONST_LINE_BREAK);
            timelog.addText(getString(R.string.player2semicolon) + " " + timep2.getName());
            timelog.addCode(Logs.CONST_LINE_BREAK);
            timelog.addText(getString(R.string.time_time_limit_string)
                    + " "
                    + time_init_min.getText().toString()
                    + getString(R.string.colon)
                    + time_init_sec.getText().toString());
            timelog.addCode(Logs.CONST_LINE_BREAK);
            timelog.addCode(Logs.CONST_TWENTY_EQUALS);
            timelog.addCode(Logs.CONST_LINE_BREAK);
            timelog.saveLog();


            mViewPager.setPagingEnabled(false);

            paused = false;
            changepausebutton(paused);

            hideKeyboard();

            time_p1name.setEnabled(false);
            time_p2name.setEnabled(false);
        } else {
            Toast.makeText(this, getString(R.string.time_start_not_filled),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void timeReset(View view) throws IOException {
        TextView timetext = (TextView) findViewById(R.id.time_time);
        LinearLayout settimelayout = (LinearLayout) findViewById(R.id.time_set_time_layout);
        TextView timescoretext = (TextView) findViewById(R.id.time_score);
        timetext.setVisibility(View.GONE);
        settimelayout.setVisibility(View.VISIBLE);
        //Reset
        if (timep1 != null) {
            timegamestate = false;
            timescoretext.setText("0:0");
            timep1.resetScore();
            timep2.resetScore();
            timetimer.resetTimer();
            //Logs
            timelog.addText(getString(R.string.reset));
            timelog.addCode(Logs.CONST_LINE_BREAK);
            timelog.addCode(Logs.CONST_TWENTY_EQUALS);
            for (int i = 0; i < 3; i++) {
                timelog.addCode(Logs.CONST_LINE_BREAK);
            }
            timelog.saveLog();
            timep1 = null;
            timep2 = null;
        }

        mViewPager.setPagingEnabled(true);

        paused = false;
        changepausebutton(paused);

        EditText time_p1name = (EditText) findViewById(R.id.time_p1name);
        EditText time_p2name = (EditText) findViewById(R.id.time_p2name);

        time_p1name.setEnabled(true);
        time_p2name.setEnabled(true);
    }

    public void timeTimeUp() throws IOException {
        //Finishes Game
        timetimer.pauseTimer();
        timegamestate = false;
        timetimer = null;
        Player winner;
        boolean draw;
        //Output Winner and Logs
        if (timep1.getScore() > timep2.getScore()) {
            winner = timep1;
            draw = false;
        } else if (timep1.getScore() < timep2.getScore()) {
            winner = timep2;
            draw = false;
        } else {
            winner = timep1;
            draw = true;
        }
        timelog.addText(getString(R.string.time_up));
        timelog.addCode(Logs.CONST_LINE_BREAK);
        if (draw) {
            timeMakeStatusText(getString(R.string.time_draw));
            timelog.addText(getString(R.string.time_draw));
        } else {
            timeMakeStatusText(winner.getName() + " " + getString(R.string.winner));
            timelog.addText(winner.getName() + " " + getString(R.string.winner));
        }
        timelog.addCode(Logs.CONST_LINE_BREAK);
        timelog.addCode(Logs.CONST_TWENTY_EQUALS);
        for (int i = 0; i < 3; i++) {
            timelog.addCode(Logs.CONST_LINE_BREAK);
        }
        timelog.saveLog();

        //Show the EditText init min and sec
        //TextView timetime = (TextView) findViewById(R.id.time_time);
        //LinearLayout time_set_time_layout = (LinearLayout) findViewById(R.id.time_set_time_layout);
        //timetime.setVisibility(View.GONE);
        //time_set_time_layout.setVisibility(View.VISIBLE);
        mViewPager.setPagingEnabled(true);

        paused = false;
        changepausebutton(paused);

        EditText time_p1name = (EditText) findViewById(R.id.time_p1name);
        EditText time_p2name = (EditText) findViewById(R.id.time_p2name);

        time_p1name.setEnabled(true);
        time_p2name.setEnabled(true);
    }

    public void timeLeftScore(View view) throws IOException {
        //Left Player gets the Point
        if (timegamestate && (!paused)) {
            TextView timeTimeView = (TextView) findViewById(R.id.time_time);
            TextView timeScoreText = (TextView) findViewById(R.id.time_score);
            timep1.addScore(1);
            timeScoreText.setText(Integer.toString(timep1.getScore())
                    + ":" + Integer.toString(timep2.getScore()));
            timeMakeStatusText(timep1.getName() + " " + getString(R.string.scores));
            timelog.addText(timep1.getName() + " " + getString(R.string.scored_at_time)
                    + " " + timeTimeView.getText().toString());
            timelog.addCodeAndSave(Logs.CONST_LINE_BREAK);
        } else {
            Toast.makeText(this, getString(R.string.game_not_start),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void timeRightScore(View view) throws IOException {
        //Right Player gets the Point
        if (timegamestate && (!paused)) {
            TextView timeTimeView = (TextView) findViewById(R.id.time_time);
            TextView timeScoreText = (TextView) findViewById(R.id.time_score);
            timep2.addScore(1);
            timeScoreText.setText(Integer.toString(timep1.getScore())
                    + ":" + Integer.toString(timep2.getScore()));
            timeMakeStatusText(timep2.getName() + " " + getString(R.string.scores));
            timelog.addText(timep2.getName() + " " + getString(R.string.scored_at_time)
                    + " " + timeTimeView.getText().toString());
            timelog.addCodeAndSave(Logs.CONST_LINE_BREAK);
        } else {
            Toast.makeText(this, getString(R.string.game_not_start),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void timeMakeStatusText(String string) {
        //Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        TextView timestatustext = (TextView) findViewById(R.id.time_status_text);
        timestatustext.setVisibility(View.VISIBLE);
        timestatustext.setText(string);
    }


    /**
     * *****************
     * *Methods for Chess**
     * *****************
     */
    public boolean chessgamestate = false;
    Timer chessuptime;
    Timer chessbottomtime;
    Logs chesslog;

    public void chessStart(View vew) throws IOException {
        EditText chess_top_init_min = (EditText) findViewById(R.id.chess_top_init_min);
        EditText chess_top_init_sec = (EditText) findViewById(R.id.chess_top_init_sec);
        EditText chess_bottom_init_min = (EditText) findViewById(R.id.chess_bottom_init_min);
        EditText chess_bottom_init_sec = (EditText) findViewById(R.id.chess_bottom_init_sec);

        if (!(chess_top_init_min.getText().toString().equals("")
                || chess_top_init_sec.getText().toString().equals("")
                || chess_bottom_init_min.getText().toString().equals("")
                || chess_bottom_init_sec.getText().toString().equals(""))) {
            hideKeyboard();
            LinearLayout topinitlayout = (LinearLayout) findViewById(R.id.chess_top_init_layout);
            LinearLayout bottominitlayout;
            bottominitlayout = (LinearLayout) findViewById(R.id.chess_bottom_init_layout);
            LinearLayout toptimelayout = (LinearLayout) findViewById(R.id.chess_top_time_layout);
            LinearLayout bottomtimelayout;
            bottomtimelayout = (LinearLayout) findViewById(R.id.chess_bottom_time_layout);
            topinitlayout.setVisibility(View.GONE);
            bottominitlayout.setVisibility(View.GONE);
            toptimelayout.setVisibility(View.VISIBLE);
            bottomtimelayout.setVisibility(View.VISIBLE);

            if (chessuptime != null) {
                chessuptime.resetTimer();
                chessuptime = null;
            }
            if (chessbottomtime != null) {
                chessbottomtime.resetTimer();
                chessbottomtime = null;
            }
            //Start
            //Init Timer
            chessgamestate = true;
            TextView[] chessuptimertextviewlist = {(TextView) findViewById(R.id.chess_top_time),
                    (TextView) findViewById(R.id.chess_top_time_inv)};
            long topthreshold = Long.parseLong(chess_top_init_min.getText().toString()) * 60000
                    + Long.parseLong(chess_top_init_sec.getText().toString()) * 1000;
            chessuptime = new Timer(chessuptimertextviewlist, Timer.CONST_CHESS, topthreshold);

            TextView[] chessbottimertextviewlist = {(TextView) findViewById(R.id.chess_bottom_time),
                    (TextView) findViewById(R.id.chess_bottom_time_inv)};
            long botthreshold = Long.parseLong(chess_bottom_init_min.getText().toString()) * 60000
                    + Long.parseLong(chess_bottom_init_sec.getText().toString()) * 1000;
            chessbottomtime = new Timer(chessbottimertextviewlist, Timer.CONST_CHESS, botthreshold);

            //Start Timer
            chessbottomtime.resumeTimer();

            //Logs
            chesslog = new Logs(path + "/chess.log");
            chesslog.addCode(Logs.CONST_TWENTY_EQUALS);
            chesslog.addCode(Logs.CONST_LINE_BREAK);
            chesslog.addText(getString(R.string.time_game_started));
            chesslog.addCode(Logs.CONST_LINE_BREAK);
            chesslog.addText(getString(R.string.chess_top_timer)
                    + " "
                    + chess_top_init_min.getText().toString()
                    + getString(R.string.colon)
                    + chess_top_init_sec.getText().toString());
            chesslog.addCode(Logs.CONST_LINE_BREAK);
            chesslog.addText(getString(R.string.chess_bottom_timer)
                    + " "
                    + chess_bottom_init_min.getText().toString()
                    + getString(R.string.colon)
                    + chess_bottom_init_sec.getText().toString());
            chesslog.addCode(Logs.CONST_LINE_BREAK);
            chesslog.addCode(Logs.CONST_TWENTY_EQUALS);
            chesslog.addCode(Logs.CONST_LINE_BREAK);
            chesslog.saveLog();

            mViewPager.setPagingEnabled(false);

            paused = false;
            changepausebutton(paused);

        } else {
            Toast.makeText(this, getString(R.string.chess_start_not_filled), Toast.LENGTH_SHORT).show();
        }
    }

    public void chessReset(View view) throws IOException {
        LinearLayout topinitlayout = (LinearLayout) findViewById(R.id.chess_top_init_layout);
        LinearLayout bottominitlayout = (LinearLayout) findViewById(R.id.chess_bottom_init_layout);
        LinearLayout toptimelayout = (LinearLayout) findViewById(R.id.chess_top_time_layout);
        LinearLayout bottomtimelayout = (LinearLayout) findViewById(R.id.chess_bottom_time_layout);
        topinitlayout.setVisibility(View.VISIBLE);
        bottominitlayout.setVisibility(View.VISIBLE);
        toptimelayout.setVisibility(View.GONE);
        bottomtimelayout.setVisibility(View.GONE);

        TextView chess_top_time = (TextView) findViewById(R.id.chess_top_time);
        TextView chess_top_time_inv = (TextView) findViewById(R.id.chess_top_time_inv);
        TextView chess_bottom_time = (TextView) findViewById(R.id.chess_bottom_time);
        TextView chess_bottom_time_inv = (TextView) findViewById(R.id.chess_bottom_time_inv);
        TextView[] chess_time_list = {
                chess_top_time,
                chess_top_time_inv,
                chess_bottom_time,
                chess_bottom_time_inv
        };

        //Reset
        if (chessgamestate) {
            chessgamestate = false;
            for (TextView chess_time : chess_time_list) {
                chess_time.setText("00:00.0");
            }
            if (chessuptime != null) {
                chessuptime.pauseTimer();
            }
            if (chessbottomtime != null) {
                chessbottomtime.pauseTimer();
            }
            //Logs
            chesslog.addText(getString(R.string.reset));
            chesslog.addCode(Logs.CONST_LINE_BREAK);
            chesslog.addCode(Logs.CONST_TWENTY_EQUALS);
            for (int i = 0; i < 3; i++) {
                chesslog.addCode(Logs.CONST_LINE_BREAK);
            }
            chesslog.saveLog();
            chessuptime = null;
            chessbottomtime = null;
        }

        mViewPager.setPagingEnabled(true);

        paused = false;
        changepausebutton(paused);
    }

    public void chessTimeUp() throws IOException {
        //Finishes Game
        chessuptime.pauseTimer();
        chessbottomtime.pauseTimer();
        chessgamestate = false;

        TextView chess_top_time = (TextView) findViewById(R.id.chess_top_time);
        TextView chess_bottom_time = (TextView) findViewById(R.id.chess_bottom_time);

        //Logs
        chesslog.addText(getString(R.string.time_up));
        chesslog.addCode(Logs.CONST_LINE_BREAK);
        chesslog.addText(getString(R.string.chess_top_timer)
                + " " + chess_top_time.getText().toString());
        chesslog.addCode(Logs.CONST_LINE_BREAK);
        chesslog.addText(getString(R.string.chess_bottom_timer)
                + " " + chess_bottom_time.getText().toString());
        chesslog.addCode(Logs.CONST_LINE_BREAK);
        chesslog.addCode(Logs.CONST_TWENTY_EQUALS);
        for (int i = 0; i < 3; i++) {
            chesslog.addCode(Logs.CONST_LINE_BREAK);
        }
        chesslog.saveLog();

        mViewPager.setPagingEnabled(true);

        paused = false;
        changepausebutton(paused);
    }

    public void chessTopPress(View view) throws IOException {
        //Toast.makeText(this, "Top Pressed", Toast.LENGTH_SHORT).show();

        if (chessgamestate && (!paused)) {
            TextView chessTopTimeView = (TextView) findViewById(R.id.chess_top_time);
            TextView chessBottomTimeView = (TextView) findViewById(R.id.chess_bottom_time);
            if (!chessuptime.getIsPaused()) {
                chessuptime.pauseTimer();
                chessbottomtime.resumeTimer();

                //Logs
                chesslog.addText(getString(R.string.chess_top_player)
                        + " " + getString(R.string.chess_presses_button));
                chesslog.addCode(Logs.CONST_LINE_BREAK);
                chesslog.addText(getString(R.string.chess_top_time)
                        + " " + chessTopTimeView.getText().toString());
                chesslog.addCode(Logs.CONST_LINE_BREAK);
                chesslog.addText(getString(R.string.chess_bottom_time)
                        + " " + chessBottomTimeView.getText().toString());
                chesslog.addCodeAndSave(Logs.CONST_LINE_BREAK);
            }
        } else {
            Toast.makeText(this, getString(R.string.game_not_start),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void chessBottomPress(View view) throws IOException {
        //Toast.makeText(this, "Bottom Pressed", Toast.LENGTH_SHORT).show();

        if (chessgamestate && (!paused)) {
            TextView chessTopTimeView = (TextView) findViewById(R.id.chess_top_time);
            TextView chessBottomTimeView = (TextView) findViewById(R.id.chess_bottom_time);
            if (!chessbottomtime.getIsPaused()) {
                chessuptime.resumeTimer();
                chessbottomtime.pauseTimer();

                //Logs
                chesslog.addText(getString(R.string.chess_bottom_player)
                        + " " + getString(R.string.chess_presses_button));
                chesslog.addCode(Logs.CONST_LINE_BREAK);
                chesslog.addText(getString(R.string.chess_top_time)
                        + " " + chessTopTimeView.getText().toString());
                chesslog.addCode(Logs.CONST_LINE_BREAK);
                chesslog.addText(getString(R.string.chess_bottom_time)
                        + " " + chessBottomTimeView.getText().toString());
                chesslog.addCodeAndSave(Logs.CONST_LINE_BREAK);
            }
        } else {
            Toast.makeText(this, getString(R.string.game_not_start),
                    Toast.LENGTH_SHORT).show();
        }
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
        TextView[] currenttimelist;
        private int type;
        public static final int CONST_POINT = 0;
        public static final int CONST_TIME = 1;
        public static final int CONST_CHESS = 2;
        private boolean isPaused;

        public Timer(TextView[] currenttimelist, int type, long thresholdtime) {
            this.starttime = System.currentTimeMillis();
            this.thresholdtime = thresholdtime;
            this.currenttimelist = currenttimelist;
            mHandler.removeCallbacks(this.startTimer);
            this.type = type;
            isPaused = false;
        }

        public void resetTimer() {
            mHandler.removeCallbacks(startTimer);
            elapsedtime = 0;
            for (TextView currenttime : currenttimelist) {
                currenttime.setText("00:00:0");
            }
            isPaused = false;
        }

        public void pauseTimer() {
            mHandler.removeCallbacks(startTimer);
            isPaused = true;
        }

        public void resumeTimer() {
            starttime = System.currentTimeMillis() - elapsedtime;
            mHandler.removeCallbacks(startTimer);
            mHandler.postDelayed(startTimer, 0);
            isPaused = false;
        }

        public boolean timeLimit() {
            return (type == CONST_TIME || type == CONST_CHESS)
                    && (elapsedtime >= thresholdtime)
                    && (thresholdtime != 0l);
        }

        public void stoptimer() throws IOException {
            //call MainActivity
            switch (type) {
                case CONST_TIME:
                    timeTimeUp();
                    isPaused = false;
                    break;
                case CONST_CHESS:
                    chessTimeUp();
                    isPaused = false;
                    break;
                default:
                    break;
            }
        }

        private Runnable startTimer = new Runnable() {
            public void run() {
                elapsedtime = System.currentTimeMillis() - starttime;
                curtime = updateTimer(elapsedtime);

                if (timeLimit()) {
                    pauseTimer();
                    try {
                        stoptimer();
                    } catch (IOException e) {
                        Log.e("Exception", "File Write Failed: " + e.toString());
                    }
                    return;
                }
                for (TextView currenttime : currenttimelist) {
                    currenttime.setText(curtime);
                }
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
            milliseconds = milliseconds.substring(milliseconds.length() - 3,
                    milliseconds.length() - 2);

		/* Setting the timer text to the elapsed time */
            return minutes + ":" + seconds + "." + milliseconds;
        }

        public boolean getIsPaused() {
            return isPaused;
        }
    }


    /**
     * Handles game pauses
     */
    boolean upispaused;

    public void pauseGame() {
        if (paused) {
            //Play
            paused = false;
            if (pointgamestate) {
                //play point
                pointtimer.resumeTimer();
                changepausebutton(paused);
            } else if (timegamestate) {
                //play time
                timetimer.resumeTimer();
                changepausebutton(paused);
            } else if (chessgamestate) {
                //play chess
                if (upispaused) {
                    chessbottomtime.resumeTimer();
                } else {
                    chessuptime.resumeTimer();
                }
                changepausebutton(paused);
            } else {
                Toast.makeText(this, getString(R.string.game_not_start), Toast.LENGTH_SHORT).show();
            }

        } else {
            paused = true;
            if (pointgamestate) {
                //pause point
                pointtimer.pauseTimer();
                changepausebutton(paused);
            } else if (timegamestate) {
                //pause time
                timetimer.pauseTimer();
                changepausebutton(paused);
            } else if (chessgamestate) {
                //pause chess
                if (chessuptime.getIsPaused()) {
                    chessbottomtime.pauseTimer();
                    upispaused = true;
                } else if (chessbottomtime.getIsPaused()) {
                    chessuptime.pauseTimer();
                    upispaused = false;
                }
                changepausebutton(paused);
            } else {
                Toast.makeText(this, getString(R.string.game_not_start_pause_button),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void changepausebutton(boolean condition) {
        ActionMenuItemView pausebutton = (ActionMenuItemView) findViewById(R.id.action_pause);
        if (condition) {
            //pause to play
            pausebutton.setIcon(getDrawable(this, R.drawable.ic_play_arrow_white_24dp));
            pausebutton.setTitle(getString(R.string.menu_play));
        } else {
            //play to pause
            pausebutton.setIcon(getDrawable(this, R.drawable.ic_pause_white_24dp));
            pausebutton.setTitle(getString(R.string.menu_pause));
        }
    }

}
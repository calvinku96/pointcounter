package com.example.calvin.pointcounter;

import android.os.Handler;

/**
 * Created by roycesanto on 6/7/2015.
 */
public class Timer {
    //start time
    //get current time
    //reset
    //pause
    //resume
    //threshold time
    //boolean for end time
    long starttime;
    long elapsedtime;
    long thresholdtime;
    String curtime;
    private final int REFRESH_RATE = 100;
    private Handler mHandler = new Handler();


    public Timer() {
        this.starttime = System.currentTimeMillis();
        //this.thresholdtime =
        mHandler.removeCallbacks(this.startTimer);

    }

    public void resetTimer() {

    }

    public void pauseTimer() {

    }

    public void resumeTimer() {

    }

    public boolean timeLimit() {

    }

    private Runnable startTimer = new Runnable() {
        public void run() {
            elapsedtime = System.currentTimeMillis() - starttime;
            curtime = updateTimer(elapsedtime);
            currenttime.setText(curtime);
            mHandler.postDelayed(this, REFRESH_RATE);
        }
    };

    private String updateTimer(float time) {
        long secs,mins;
        String seconds,minutes,milliseconds;

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
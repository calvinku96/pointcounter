package com.ctrctr.pointcounter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class ViewLog extends AppCompatActivity implements ActionBar.TabListener {

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
    public String[] log_string_array = {" ", " ", " "};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_log);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }


        Intent intent = getIntent();
        try {
            log_string_array = getlogfile();
        } catch (IOException e) {
            Log.e("Exception", "File Write Failed: " + e.toString());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_view_log_delete) {
            try {
                deleteLogDialog();
            } catch (IOException e) {
                Log.e("Exception", "File Write Failed: " + e.toString());
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
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
            //return PlaceholderFragment.newInstance(position + 1);
            Bundle args = new Bundle();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            switch (position) {
                case 0:
                    args.putString("point", log_string_array[0]);
                    ViewLogPointFragment a = new ViewLogPointFragment();

                    a.setArguments(args);
                    ft.addToBackStack(null);
                    ft.commit();
                    return a;
                case 1:
                    args.putString("time", log_string_array[1]);
                    ViewLogTimeFragment b = new ViewLogTimeFragment();

                    b.setArguments(args);
                    ft.addToBackStack(null);
                    ft.commit();
                    return b;
                case 2:
                    args.putString("chess", log_string_array[2]);
                    ViewLogChessFragment c = new ViewLogChessFragment();

                    c.setArguments(args);
                    ft.addToBackStack(null);
                    ft.commit();
                    return c;
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
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_view_log, container, false);
            return rootView;
        }
    }

    public String[] getlogfile() throws IOException {
        String path = getFilesDir().getAbsolutePath();
        String[] endingPathArray = {"/point.log", "/time.log", "/chess.log"};
        String[] contentsarray = new String[endingPathArray.length];
        for (int i = 0; i < endingPathArray.length; i++) {
            File file = new File(path + endingPathArray[i]);
            int length = (int) file.length();
            byte[] bytes = new byte[length];
            FileInputStream in = new FileInputStream(file);
            in.read(bytes);
            in.close();
            String contents = new String(bytes);
            contentsarray[i] = contents;
        }
        return contentsarray;
    }

    public void resetLog(String string) throws IOException {
        String path = getFilesDir().getAbsolutePath();
        File file = new File(path + string);
        FileOutputStream stream = new FileOutputStream(file, false);
        try {
            stream.write("".getBytes());
        } catch (IOException e) {
            Log.e("Exception", "File Write Failed: " + e.toString());
        } finally {
            stream.close();
        }
        Toast.makeText(this, "Log file cleared", Toast.LENGTH_SHORT).show();
    }

    public void deleteLogDialog() throws IOException {
        AlertDialog dialog;
        final CharSequence[] items = {
                getString(R.string.view_log_point),
                getString(R.string.view_log_time),
                getString(R.string.view_log_chess)};
        //arraylist to keep the selected items
        final ArrayList selectedItems = new ArrayList();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.view_log_delete_dialog_title));
        builder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                if (isChecked) {
                    //If the user checked the item, add it to the selected items
                    selectedItems.add(indexSelected);
                } else if (selectedItems.contains(indexSelected)) {
                    selectedItems.remove(Integer.valueOf(indexSelected));
                }
            }
        }).setPositiveButton(getString(R.string.view_log_OK), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Code when the user click OK
                try {
                    deleteLog(selectedItems);
                } catch (IOException e) {
                    Log.e("Exception", "File Write Failed: " + e.toString());
                }

            }
        }).setNegativeButton(getString(R.string.view_log_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Code when user clicked on Cancel
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    public void deleteLog(ArrayList<Integer> selectedItems) throws IOException {
        for (int items : selectedItems) {
            switch (items) {
                case 0:
                    resetLog("/point.log");
                    log_string_array[0] = "";
                    break;
                case 1:
                    resetLog("/time.log");
                    log_string_array[1] = "";
                    break;
                case 2:
                    resetLog("/chess.log");
                    log_string_array[2] = "";
                    break;
                default:
                    break;
            }
        }
        Toast.makeText(this, getString(R.string.view_log_deleted), Toast.LENGTH_SHORT).show();
    }

}
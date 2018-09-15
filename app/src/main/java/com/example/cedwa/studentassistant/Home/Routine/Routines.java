package com.example.cedwa.studentassistant.Home.Routine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cedwa.studentassistant.DatabaseFiles.Config;
import com.example.cedwa.studentassistant.Home.Routine.days.Friday;
import com.example.cedwa.studentassistant.Home.Routine.days.Monday;
import com.example.cedwa.studentassistant.Home.Routine.days.Saturday;
import com.example.cedwa.studentassistant.Home.Routine.days.Sunday;
import com.example.cedwa.studentassistant.Home.Routine.days.Thursday;
import com.example.cedwa.studentassistant.Home.Routine.days.Tuesday;
import com.example.cedwa.studentassistant.Home.Routine.days.Wednesday;
import com.example.cedwa.studentassistant.R;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class Routines extends Fragment {

    Map< String,Fragment> daysMap;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //create the map
        daysMap = new HashMap< String,Fragment>();
        daysMap.put(Config.DAY_SAT, new Saturday());
        daysMap.put(Config.DAY_SUN, new Sunday());
        daysMap.put(Config.DAY_MON, new Monday());
        daysMap.put(Config.DAY_TUE, new Tuesday());
        daysMap.put(Config.DAY_WED, new Wednesday());
        daysMap.put(Config.DAY_THU, new Thursday());
        daysMap.put(Config.DAY_FRI, new Friday());

        Logger.addLogAdapter(new AndroidLogAdapter());

        View result = inflater.inflate(R.layout.fragment_routines, container, false);
        SectionPagerAdapter sectionPagerAdapter = new SectionPagerAdapter(getChildFragmentManager());
        viewPager = result.findViewById(R.id.pager);
        /*viewPager.addOnPageChangeListener(new CircularViewPagerHandler(viewPager));*/
        viewPager.setAdapter(sectionPagerAdapter);
        viewPager.setCurrentItem(setCurrentDay());

        return result;
    }

    private class SectionPagerAdapter extends FragmentPagerAdapter{

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {

                case 0:
                    return new Saturday();
                case 1 :
                    return new Sunday();
                case 2:
                    return new Monday();
                case 3:
                    return new Tuesday();
                case 4:
                    return new Wednesday();
                case 5:
                    return new Thursday();
                case 6:
                    return new Friday();

            }

            return null;
        }

        @Override
        public int getCount() {
            return 7;
        }
    }

    public int setCurrentDay()
    {
        int day;
        //set current day
        Calendar sCalendar = Calendar.getInstance();
        String dayLongName = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

        Logger.d(dayLongName+" "+Config.DAY_SAT);

        switch(dayLongName.toLowerCase())
        {
            case Config.DAY_SAT:
                return 0;

            case Config.DAY_SUN:
                return 1;

            case Config.DAY_MON:
               return 2;

            case Config.DAY_TUE:
               return 3;

            case Config.DAY_WED:
                return 4;

            case Config.DAY_THU:
                return 5;

            default:
                return 6;
        }

    }

}

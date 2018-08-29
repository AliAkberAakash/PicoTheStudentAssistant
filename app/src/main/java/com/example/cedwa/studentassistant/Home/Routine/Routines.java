package com.example.cedwa.studentassistant.Home.Routine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cedwa.studentassistant.Home.Routine.days.Friday;
import com.example.cedwa.studentassistant.Home.Routine.days.Monday;
import com.example.cedwa.studentassistant.Home.Routine.days.Saturday;
import com.example.cedwa.studentassistant.Home.Routine.days.Sunday;
import com.example.cedwa.studentassistant.Home.Routine.days.Thursday;
import com.example.cedwa.studentassistant.Home.Routine.days.Tuesday;
import com.example.cedwa.studentassistant.Home.Routine.days.Wednesday;
import com.example.cedwa.studentassistant.R;


public class Routines extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.fragment_routines, container, false);
        SectionPagerAdapter sectionPagerAdapter = new SectionPagerAdapter(getChildFragmentManager());
        ViewPager viewPager = result.findViewById(R.id.pager);
        viewPager.setAdapter(sectionPagerAdapter);

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

}

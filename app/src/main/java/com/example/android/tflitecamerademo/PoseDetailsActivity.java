package com.example.android.tflitecamerademo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PoseDetailsActivity extends AppCompatActivity {

    public final static String newActivityMessage = "PoseDetail";

    private String poseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pose_details);

        poseName = getIntent().getStringExtra(newActivityMessage);

        //Attach the SectionsPagerAdapter to the ViewPager
        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    }


    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle args = new Bundle();
            Fragment fragment;
            switch (position) {
                case 0:
                    args.putString(PoseInfoFragment.newFragmentMessage, poseName);
                    fragment = new PoseInfoFragment();
                    fragment.setArguments(args);
                    return fragment;
                case 1:
                    args.putString(PoseExampleFragment.newFragmentMessage, poseName);
                    fragment = new PoseExampleFragment();
                    fragment.setArguments(args);
                    return fragment;
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.home_tab);
                case 1:
                    return getResources().getText(R.string.example_tab);
            }
            return null;
        }
    }
}

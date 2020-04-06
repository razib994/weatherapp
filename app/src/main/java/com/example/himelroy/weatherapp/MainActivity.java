package com.example.himelroy.weatherapp;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabPagerAdapter tabPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            viewPager =  findViewById(R.id.viewPager);
            tabLayout = findViewById(R.id.tabLayout);
            tabLayout.addTab(tabLayout.newTab().setText("CURRENT"));
            tabLayout.addTab(tabLayout.newTab().setText("7 DAYS FORECAST"));
            tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
            viewPager.setAdapter(tabPagerAdapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
        public class TabPagerAdapter extends FragmentPagerAdapter{
        int tabCount;

            public TabPagerAdapter(FragmentManager fm, int tabCount) {
                super(fm);
                this.tabCount =tabCount;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return new Currentragment();
                    case 1:
                        return new ForecastFragment();
                }
                return null;
            }

            @Override
            public int getCount() {
                return tabCount;
            }
        }




}

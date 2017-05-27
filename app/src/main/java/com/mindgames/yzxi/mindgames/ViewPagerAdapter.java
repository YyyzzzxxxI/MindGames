package com.mindgames.yzxi.mindgames;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;





public class ViewPagerAdapter extends FragmentStatePagerAdapter {





    CharSequence Titles[];
    int NumbOfTabs;



    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            Records_tab_1 tab1 = new Records_tab_1();

            return tab1;
        }
        if(position == 1)
        {
            Records_tab_2 tab2 = new Records_tab_2();

            return tab2;
        }
        else
        {
            Records_tab_3 tab3 = new Records_tab_3();

            return tab3;
        }


    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }




}
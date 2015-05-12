package com.climbingtraining.constantine.climbingtraining.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.climbingtraining.constantine.climbingtraining.fragments.CategoryFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.EquipmentsFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.TypesExercisesFragment;

/**
 * Created by KonstantinSysoev on 09.05.15.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    private int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return CategoryFragment.newInstance();
        } else if (position == 1){
            return EquipmentsFragment.newInstance();
        } else {
            return TypesExercisesFragment.newInstance();
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
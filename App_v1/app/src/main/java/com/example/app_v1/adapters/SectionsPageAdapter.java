package com.example.app_v1.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class SectionsPageAdapter extends FragmentPagerAdapter
{
    private final List<Fragment> fragmentList = new ArrayList<>();

    public SectionsPageAdapter(FragmentManager fm)
    {
        super(fm);
    }

    public void addFragment(Fragment fragment)
    {
        fragmentList.add(fragment);
    }

    @Override
    public int getCount()
    {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int i)
    {
        return fragmentList.get(i);
    }
}

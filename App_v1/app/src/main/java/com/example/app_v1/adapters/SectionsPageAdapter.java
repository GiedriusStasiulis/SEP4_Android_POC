package com.example.app_v1.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
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

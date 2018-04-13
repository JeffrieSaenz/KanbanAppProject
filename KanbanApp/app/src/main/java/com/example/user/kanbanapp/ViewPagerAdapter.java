package com.example.user.kanbanapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public void addFragments(Main_Content f, String t) {
        // (fragments.isEmpty()) ? f.setPosicion(0): f.setPosicion(fragments.size()-1);
        //f.setPosicion(fragments.size() - 1);
        // System.out.printf(" Tamano = %s ; ", String.valueOf(fragments.size()));
        fragments.add(f);
        titles.add(t);
        Integer pos = fragments.size() - 1;
        f.setPosicion(pos);
    }
}

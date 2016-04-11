package com.emapper.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class FrgtPagerAdapter extends FragmentPagerAdapter {
	private ArrayList<Fragment> fragments;

	public FrgtPagerAdapter(FragmentManager frgtManager,
			ArrayList<Fragment> fragments) {
		super(frgtManager);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}
}

package com.example.torch.viewPgerAdapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.torch.fragment.reservation.DoneFragment;
import com.example.torch.fragment.reservation.Pending;

public class ViewPagerAdapterReservation extends FragmentPagerAdapter {
    Fragment fragment[]={new Pending(),new DoneFragment()};
    String Title [] ={"Pending","Done"};
    public ViewPagerAdapterReservation(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragment[position];
    }

    @Override
    public int getCount() {
        return fragment.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return Title[position];
    }
}
package com.example.torch.viewPgerAdapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.torch.fragment.Products;
import com.example.torch.fragment.Services;
import com.example.torch.fragment.ServiesFragmentNestedRecycler;

public class viewPagerAdapter extends FragmentPagerAdapter {
    Fragment fragment[]={new Services(),new Products()};
    String Title [] ={"Services","Products"};
    public viewPagerAdapter(FragmentManager fm) {
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

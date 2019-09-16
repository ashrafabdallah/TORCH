package com.example.torch.viewPgerAdapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.torch.fragment.FavoritProduct;
import com.example.torch.fragment.FavoritProviders;

public class ViewPagerAtapterFavorit extends FragmentPagerAdapter {
    private Fragment[] fragment = {new FavoritProviders(), new FavoritProduct()};
    private String titel[] = {"Providers", "Products"};

    public ViewPagerAtapterFavorit(FragmentManager fm) {
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
        return titel[position];
    }
}

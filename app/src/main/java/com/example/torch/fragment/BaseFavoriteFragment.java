package com.example.torch.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.torch.R;
import com.example.torch.viewPgerAdapter.ViewPagerAtapterFavorit;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFavoriteFragment extends Fragment {


    public BaseFavoriteFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_base_favorite, container, false);
        ViewPagerAtapterFavorit adappter = new ViewPagerAtapterFavorit(getActivity().getSupportFragmentManager());
       int limit = (adappter.getCount() > 1 ? adappter.getCount() - 1 : 1);
        TabLayout tabLayout = v.findViewById(R.id.tabLayout);
        ViewPager viewPager = v.findViewById(R.id.Viewpager);
        viewPager.setAdapter(adappter);
        viewPager.setOffscreenPageLimit(limit);
        tabLayout.setupWithViewPager(viewPager);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search_product,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}

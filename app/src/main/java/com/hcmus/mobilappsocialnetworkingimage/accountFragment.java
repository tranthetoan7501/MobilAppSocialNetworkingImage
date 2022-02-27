package com.hcmus.mobilappsocialnetworkingimage;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.tabs.TabLayout;

public class accountFragment extends Fragment {
    Button button;
    TextView textView;
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    video videoFragment;
    tag tagFragment;
    all_pictures allPicturesFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        toolbar = view.findViewById(R.id.toolbar);
        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        videoFragment = new video();
        tagFragment = new tag();
        allPicturesFragment = new all_pictures();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(((AppCompatActivity)getActivity()).getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.addFragment(allPicturesFragment);
        viewPagerAdapter.addFragment(videoFragment);
        viewPagerAdapter.addFragment(tagFragment);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_grid);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_video);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_tag);

    }
}
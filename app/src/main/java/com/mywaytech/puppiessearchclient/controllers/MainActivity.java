package com.mywaytech.puppiessearchclient.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.fragments.AdoptionFragment;
import com.mywaytech.puppiessearchclient.controllers.fragments.WallFragment;

/**
 * Created by m.maigua on 4/13/2016.
 */
public class MainActivity extends BaseActivity {

    public static final int FRAG0_POS = 0;
    public static final int FRAG1_POS = 1;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        tabLayout.getTabAt(0).setText("Muro");
        tabLayout.getTabAt(1).setText("Adopción");

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void selectTab ( int position){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment;


        switch (position) {
            case FRAG0_POS:
                fragment = WallFragment.newInstance(position);
                ft.replace(R.id.container, fragment).commit();
                break;
            case FRAG1_POS:
                fragment = AdoptionFragment.newInstance(position);
                ft.replace(R.id.container, fragment).commit();
                break;
            default:
                fragment = WallFragment.newInstance(position);
                ft.replace(R.id.container, fragment).commit();
                break;

        }

    }

    @Override
    public int getToolbarTitle() {
        return R.string.main_activity_title;
    }

    @Override
    public int getContentResource() {
        return R.layout.activity_main_layout;
    }


}

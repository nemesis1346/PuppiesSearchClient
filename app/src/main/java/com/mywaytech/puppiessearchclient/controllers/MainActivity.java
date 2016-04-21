package com.mywaytech.puppiessearchclient.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.fragments.AdoptionFragment;
import com.mywaytech.puppiessearchclient.controllers.fragments.WallFragment;
import com.mywaytech.puppiessearchclient.models.NewUserObject;
import com.mywaytech.puppiessearchclient.models.UserPetObject;
import com.mywaytech.puppiessearchclient.services.UserDatabase;

/**
 * Created by m.maigua on 4/13/2016.
 */
public class MainActivity extends BaseActivity implements SearchDialog.PassDataFragment {

    public static final String EXTRA_USERDATA = "com.mywaytech.puppiessearchclient.extras.extra_userdata";
    public static final String EXTRA_EMAIL_FORAUTH = "com.mywaytech.puppiessearchclient.extras.extra_email_forauth";
    public static final String EXTRA_NEWPET_DATA = "com.mywaytech.puppiessearchclient.extras.extra_newpet_data";

    public static final String EXTRA_FRAGMENT_VAL = "com.mywaytech.puppiessearchclient.extras.extra_fragment_val";

    public static final int FRAG0_POS = 0;
    public static final int FRAG1_POS = 1;
    private TabLayout tabLayout;
    int value = -1;

    private String emailForAuth;
    private NewUserObject newUserObject;
    private UserPetObject userPetObject;

    private String[] email_second_method;

    private UserDatabase myDB;

    private int frag_val;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userPetObject = (UserPetObject) getIntent().getSerializableExtra(EXTRA_NEWPET_DATA);
        emailForAuth = getIntent().getStringExtra(EXTRA_EMAIL_FORAUTH);
        frag_val = getIntent().getIntExtra(EXTRA_FRAGMENT_VAL, 0);

        myDB = new UserDatabase(this);
        //   email_second_method=new String[]{myDB.getLastRow()[0],myDB.getLastRow()[1],myDB.getLastRow()[2]};

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("Muro"));
        tabLayout.addTab(tabLayout.newTab().setText("Adopción"));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (value == -1) {
                    value = 0;
                }
                selectTab(tab.getPosition(), value);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        switch(frag_val){
            case 1:
                selectTab(FRAG0_POS, 0);
                break;
            case 2:
                selectTab(FRAG0_POS, 1);
                break;
            default:
                selectTab(FRAG0_POS, 0);
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_search:
                showDialog();
                return true;
            case R.id.action_account:
                intent = new Intent(MainActivity.this, AccountActivity.class);
                intent.putExtra(AccountActivity.EXTRA_EMAIL, emailForAuth);
                //  intent.putExtra(AccountActivity.EXTRA_EMAIL, email_second_method[1]);
                startActivity(intent);
                return true;
            case R.id.action_map:
                intent = new Intent(MainActivity.this, MapActivity.class);
//                intent.putExtra(FormActivity.EXTRA_NAME, "Marco");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showDialog() {
        FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
        SearchDialog searchDialog = SearchDialog.newInstance();

//        searchDialog.setTargetFragment(,0);

        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft3.remove(prev);
        }
        ft3.addToBackStack(null);

        searchDialog.show(ft3, "dialog");
    }


    private void selectTab(int position, int value) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment;
        switch (position) {
            case FRAG0_POS:
                fragment = WallFragment.newInstance(position, value);
                ft.replace(R.id.container, fragment).commit();
                break;
            case FRAG1_POS:
                fragment = AdoptionFragment.newInstance(position, value);
                ft.replace(R.id.container, fragment).commit();
                break;
            default:
                fragment = WallFragment.newInstance(position, value);
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


    @Override
    public void backData(int value) {
        Log.d("backdata", "" + value);
        this.value = value;
        selectTab(tabLayout.getSelectedTabPosition(), value);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}

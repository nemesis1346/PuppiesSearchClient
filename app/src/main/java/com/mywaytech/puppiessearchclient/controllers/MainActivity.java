package com.mywaytech.puppiessearchclient.controllers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.fragments.ReportFragment;
import com.mywaytech.puppiessearchclient.controllers.fragments.WallAdoptionFragment;
import com.mywaytech.puppiessearchclient.controllers.fragments.WallLostFragment;
import com.mywaytech.puppiessearchclient.utils.AlertDialogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by m.maigua on 4/13/2016.
 */
public class MainActivity extends BaseActivity {

    public static final String EXTRA_EMAIL_FORAUTH = "com.mywaytech.puppiessearchclient.extras.extra_email_forauth";

    public static final String EXTRA_FRAGMENT_VAL = "com.mywaytech.puppiessearchclient.extras.extra_fragment_val";

    private static final int PET_REQUEST = 0;

    public static final int FRAG0_POS = 0;
    public static final int FRAG1_POS = 1;
    private TabLayout tabLayout;
    int value = -1;

    private FloatingActionButton btn_add_dog;

    private String emailForAuth;

    private int frag_val;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.main_activity_title);

        btn_add_dog = (FloatingActionButton) findViewById(R.id.fab_add_dog_wall);
        btn_add_dog.setOnClickListener(addListener);

        emailForAuth = getIntent().getStringExtra(EXTRA_EMAIL_FORAUTH);
        frag_val = getIntent().getIntExtra(EXTRA_FRAGMENT_VAL, 0);

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


        switch (frag_val) {
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

    public View.OnClickListener addListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = ReportActivity.newIntent(MainActivity.this);
            startActivityForResult(intent, PET_REQUEST);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_filter:
                showDialog();
                return true;
            case R.id.action_contact_us:
                intent = ContactUsActivity.newIntent(MainActivity.this);
                startActivity(intent);
                return true;
            case R.id.action_map:
                intent = MapActivity.newIntent(MainActivity.this);
                startActivity(intent);
                return true;
            case R.id.menuoptions_settings:
                intent = new Intent(MainActivity.this, PreferencesActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuoptions_logout:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void selectTab(int position, int value) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment;
        switch (position) {
            case FRAG0_POS:
                fragment = WallLostFragment.newInstance(position, value);
                ft.replace(R.id.container, fragment).commit();
                break;
            case FRAG1_POS:
                fragment = WallAdoptionFragment.newInstance(position, value);
                ft.replace(R.id.container, fragment).commit();
                break;
            default:
                fragment = WallLostFragment.newInstance(position, value);
                ft.replace(R.id.container, fragment).commit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        logout();
    }

    public void logout() {
        new AlertDialogUtils.Builder(this)
                .setResourceMessage(R.string.close_session_message)
                .setTitle(R.string.close_session_title)
                .setPositiveText(R.string.btn_yes)
                .setPositiveButtonListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent mainIntent = new Intent().setClass(
                                MainActivity.this, LoginActivity.class);
                        startActivity(mainIntent);
                    }
                })
                .setNegativeText(R.string.btn_no)
                .show();

    }

    public void showDialog() {

        final ListView mChoiceListView = new ListView(this);
        List<String> optionsFilter = new ArrayList<String>();
        optionsFilter.add(ReportFragment.TYPE_PET_ADOPTION);
        optionsFilter.add(ReportFragment.TYPE_PET_LOST);
        optionsFilter.add(ReportFragment.TYPE_PET_NEW);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, optionsFilter);
        mChoiceListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mChoiceListView.setAdapter(arrayAdapter);


        new AlertDialogUtils.Builder(this)
                .setTitle(R.string.txt_title_choice)
                .setPositiveText(R.string.btn_ok)
                .setCustomView(mChoiceListView)
                .setPositiveButtonListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIsCancelable(false)
                .setNegativeText(R.string.btn_cancel)
                .setNegativeButtonListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    }

}

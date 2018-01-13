package com.bytepair.psmf;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("test");

        // Set up Drawer Variables
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.left_drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        // Attach button to drawer layout
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        // Tells home button to go up one level instead of back to home page
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up buttons in the navigation view
        setupDrawerContent(mNavigationView);

        // Set Introduction as starting checked item
        selectDrawerItem(mNavigationView.getMenu().findItem(R.id.introduction));
    }


    // Makes navigation drawer open when clicking button on toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // Replaces the fragment in content_main with whatever was selected from the navigation drawer
    public void selectDrawerItem(MenuItem menuItem) {
        Fragment myFragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.introduction:
                fragmentClass = Introduction.class;
                break;
            case R.id.questions:
                fragmentClass = Questions.class;
                break;
            case R.id.supplements:
                fragmentClass = Supplements.class;
                break;
            case R.id.calculator:
                fragmentClass = Calculator.class;
                break;
            case R.id.shop:
                fragmentClass = Shop.class;
                break;
            default:
                fragmentClass = Introduction.class;
        }
        try {
            myFragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // use fragment manager to replace current fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, myFragment).commit();

        // remove highlight from existing items
        for (int i = 0; i < mNavigationView.getMenu().size(); i++) {
            mNavigationView.getMenu().getItem(i).setChecked(false);
        }

        // highlight the selected item
        menuItem.setChecked(true);

        // set action bar title
        setTitle(menuItem.getTitle());

        // close the navigation drawer
        mDrawerLayout.closeDrawers();
    }


    // Calls selectDrawerItem whenever a navigation menu item is selected
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });
    }

}

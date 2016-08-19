package com.ucsbapp.phillip.affix;

import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class HomeScreen extends AppCompatActivity {

    //These are all the necessary variables to make the tabs

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        //Assign view variables to respective view in xml

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);


        //Create Adapter and set that adapter to the viewPager
        //setSupportActionBar method: sets toolbar.xml as the default action bar

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        setSupportActionBar(toolbar);


        //TabLayout.newTab() method: create a tab view,its the tab itself, not the fragment.

        final TabLayout.Tab SmartSell = tabLayout.newTab();
        final TabLayout.Tab VTours = tabLayout.newTab();
        final TabLayout.Tab GauchoLink = tabLayout.newTab();


        //Set Title text for tabs

        SmartSell.setText("SmartSell");
        VTours.setText("VTours");
        GauchoLink.setText("GauchoLink");

        // Position the tabs in this order
        tabLayout.addTab(SmartSell, 0);
        tabLayout.addTab(VTours, 1);
        tabLayout.addTab(GauchoLink, 2);


        //TabTextColor: sets the color for the title of the tabs,
        //ColorStateList here makes tab change colors when selected, active, inactive etc
        // TabIndicatorColor sets the color for the indiactor below the tabs

        tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.drawable.tab_selector));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorAccent));


        // Adding onPageChangeListener to the viewPager
        // add the PageChangeListener and pass a TabLayoutPageChangeListener so that Tabs Selection
        //changes when a viewpager page changes.

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}



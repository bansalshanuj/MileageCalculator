package com.development.shanujbansal.mileagecalc;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StartActivity extends ActionBarActivity implements ActionBar.TabListener {

    // Within which the entire activity is enclosed
    DrawerLayout mDrawerLayout;
    // ListView represents Navigation Drawer
    ListView mDrawerList;
    // ActionBarDrawerToggle indicates the presence of Navigation Drawer in the action bar
    ActionBarDrawerToggle mDrawerToggle;
    // Title of the action bar
    String mTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Here we need to check if there is atleast one vehicle
        // already registered, then open the add entry page or else
        // open the register vehicle page.
//        ArrayList<String> vehiclesList = DatabaseHelper.getInstance(this).getVehiclesList();
//        if (vehiclesList == null || vehiclesList.size() == 0) {
//            Intent addVehicleIntent = new Intent(this, AddVehicle.class);
//            //addVehicleIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            addVehicleIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(addVehicleIntent);
//        } else {
//            Intent addNewEntryIntent = new Intent(this, AddNewEntry.class);
//            // addNewEntryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            addNewEntryIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(addNewEntryIntent);
//        }


        // For creating the side navigation.
        mTitle = (String) getTitle();
        // Getting reference to the DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.nav_drawer_list);

        // Getting reference to the ActionBarDrawerToggle
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                R.drawable.background,
                R.string.drawer_open,
                R.string.drawer_close) {

            /** Called when drawer is closed */
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            /** Called when a drawer is opened */
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle("Select a river");
                invalidateOptionsMenu();
            }
        };

        // Setting DrawerToggle on DrawerLayout
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // Creating an ArrayAdapter to add items to the listview mDrawerList
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getBaseContext(),
                R.layout.drawer_list_item,
                getResources().getStringArray(R.array.Actions)
        );

        // Setting the adapter on mDrawerList
        mDrawerList.setAdapter(adapter);

        if (getActionBar() != null) {
            // Enabling Home button
            getActionBar().setHomeButtonEnabled(true);
            // Enabling Up navigation
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Setting item click listener for the listview mDrawerList
        mDrawerList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id) {

                /*
                // Getting an array of rivers
                String[] rivers = getResources().getStringArray(R.array.Actions);

                //Currently selected river
                mTitle = rivers[position];
                // Creating a fragment object
                RiverFragment rFragment = new RiverFragment();
                // Creating a Bundle object
                Bundle data = new Bundle();

                // Setting the index of the currently selected item of mDrawerList
                data.putInt("position", position);
                // Setting the position to the fragment
                rFragment.setArguments(data);
                // Getting reference to the FragmentManager
                FragmentManager fragmentManager = getFragmentManager();

                // Creating a fragment transaction
                FragmentTransaction ft = fragmentManager.beginTransaction();
                // Adding a fragment to the fragment transaction
                ft.replace(R.id.content_frame, rFragment);

                // Committing the transaction
                ft.commit();
                // Closing the drawer
                mDrawerLayout.closeDrawer(mDrawerList);
                */
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
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
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }
}

package lifemonitor.application;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

import lifemonitor.application.controller.gmap.GoogleMapFragment;
import lifemonitor.application.controller.medicalRecord.AddTreatmentActivity;
import lifemonitor.application.controller.medicalRecord.ShowMedicalRecordActivity;
import lifemonitor.application.controller.medicalRecord.TodayIntakesActivity;
import lifemonitor.application.controller.service.AddMedicalAppointment;
import lifemonitor.application.controller.userconfig.UserConfigActivity;
import lifemonitor.application.database.LocalDataBase;
import lifemonitor.application.model.menu.NavDrawerItem;
import lifemonitor.application.model.menu.NavDrawerListAdapter;

/**
 * Home Page
 */
public class MyActivity extends FragmentActivity {
    public static final String FRAGMENT_ID_BUNDLE = "fragmentId";
    public static final int MEDICAL_RECORD_FRAGMENT_ID = 0,
            TODAY_TREATMENTS_FRAGMENT_ID = 1,
            APPOINTMENT_FRAGMENT_ID = 2,
            ADD_TREATMENT_FRAGMENT_ID = 3,
            PHARMACY_FRAGMENT_ID = 4,
            CONFIGURATION_FRAGMENT_ID = 5,
            EMERGENCY_CALL_FRAGMENT_ID = 6;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private int position;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);

        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Medical record
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[MEDICAL_RECORD_FRAGMENT_ID], navMenuIcons.getResourceId(MEDICAL_RECORD_FRAGMENT_ID, -1)));
        // Medicines to take today
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[TODAY_TREATMENTS_FRAGMENT_ID], navMenuIcons.getResourceId(TODAY_TREATMENTS_FRAGMENT_ID, -1)));
        // Add doctor appointment
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[APPOINTMENT_FRAGMENT_ID], navMenuIcons.getResourceId(APPOINTMENT_FRAGMENT_ID, - 1)));
        // Add treatment
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[ADD_TREATMENT_FRAGMENT_ID], navMenuIcons.getResourceId(ADD_TREATMENT_FRAGMENT_ID, -1)));
        // Find closest pharmacy
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[PHARMACY_FRAGMENT_ID], navMenuIcons.getResourceId(PHARMACY_FRAGMENT_ID, -1)));
        // Configuration
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[CONFIGURATION_FRAGMENT_ID], navMenuIcons.getResourceId(CONFIGURATION_FRAGMENT_ID, -1)));
        // Emergency call
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[EMERGENCY_CALL_FRAGMENT_ID], navMenuIcons.getResourceId(EMERGENCY_CALL_FRAGMENT_ID, -1)));

        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // Display the current fragment, depending on if a specific fragment is requested
        Intent intent = getIntent();
        int fragmentId = intent.getIntExtra(FRAGMENT_ID_BUNDLE, -1);
        if(fragmentId != -1) {
            displayView(fragmentId);
        } else {
            if (savedInstanceState != null) {
                position = savedInstanceState.getInt(FRAGMENT_ID_BUNDLE, 0);
                displayView(position);
            } else {
                displayView(0);
            }
        }

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
    }


    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* *
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    public void displayView(int position) {
        this.position = position;
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case MEDICAL_RECORD_FRAGMENT_ID:
                fragment = new ShowMedicalRecordActivity();
                break;
            case APPOINTMENT_FRAGMENT_ID:
                fragment = new AddMedicalAppointment();
                break;
            case ADD_TREATMENT_FRAGMENT_ID:
                fragment = new AddTreatmentActivity();
                break;
            case TODAY_TREATMENTS_FRAGMENT_ID:
                fragment = new TodayIntakesActivity();
                break;
            case PHARMACY_FRAGMENT_ID:
                fragment = GoogleMapFragment.newInstance();
                break;
            case CONFIGURATION_FRAGMENT_ID:
                fragment = new UserConfigActivity();
                break;
            case EMERGENCY_CALL_FRAGMENT_ID:
                emergency_call();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).addToBackStack("").commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MyActivity", "Error in creating fragment");
        }
    }

    private void emergency_call() {
        LocalDataBase dbHandler;
        dbHandler = new LocalDataBase(this);
        Intent callIntent = new Intent(Intent.ACTION_CALL);

        try {
            String call = dbHandler.getUser(dbHandler.getFirstUserId()).getEmergencyNumber();

            if (!call.equals("")) {
                callIntent.setData(Uri.parse("tel:" + call));
                startActivity(callIntent);
            } else {
                Toast.makeText(MyActivity.this, R.string.emergency_number_empty, Toast.LENGTH_LONG).show();
            }
        }
        catch (SQLException sqle) { }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(FRAGMENT_ID_BUNDLE, position);
        super.onSaveInstanceState(outState);
    }
}

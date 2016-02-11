package com.egov.egov;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.egov.egov.AsyncTaskGeneric.ServiceCall;
import com.egov.egov.Fragments.ComplaintFragment;
import com.egov.egov.Fragments.ComplaintManagement;
import com.egov.egov.Fragments.MainFragment;
import com.egov.egov.Fragments.PhoneDirectoryFragment;
import com.egov.egov.Fragments.TravelFragment;
import com.egov.egov.Utils.TypefaceSpan;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Bundle bundle;
SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView namess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Fresco.initialize(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, new MainFragment()).commit();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        namess = (TextView) header.findViewById(R.id.textView);
        TextView cvid = (TextView) header.findViewById(R.id.cvid);
        sharedPreferences = getSharedPreferences("Name", MODE_APPEND);
editor = sharedPreferences.edit();
        bundle = getIntent().getExtras();
        new getNameTask().execute();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        SpannableString s = new SpannableString("Home");
        s.setSpan(new TypefaceSpan(this, "Lato-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setTitle(s);









        SpannableString cvidSpan = new SpannableString(bundle.getString("cvid", "Cvid"));
        cvidSpan.setSpan(new TypefaceSpan(this, "Lato-Regular.ttf"), 0, cvidSpan.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cvid.setText(cvidSpan);


    }



    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        ComplaintFragment complaintFragment;
        if (id == R.id.main) {
            MainFragment mainFragment = new MainFragment();
            fragmentManager.beginTransaction().replace(R.id.flContent, mainFragment).commit();
        } else if (id == R.id.add_complaint) {
            complaintFragment = new ComplaintFragment();
            complaintFragment.newInstance(1, fragmentManager);
            fragmentManager.beginTransaction().replace(R.id.flContent, complaintFragment).commit();
        } else if (id == R.id.phone_book) {
            PhoneDirectoryFragment phoneDirectoryFragment = new PhoneDirectoryFragment();
            phoneDirectoryFragment.newInstance(1);
            fragmentManager.beginTransaction().replace(R.id.flContent, phoneDirectoryFragment).commit();

        } else if (id == R.id.complaint_management) {
            ComplaintManagement complaintManagement = new ComplaintManagement();
            fragmentManager.beginTransaction().replace(R.id.flContent, complaintManagement).commit();
        } else if (id == R.id.travel_notify) {
            TravelFragment travelFragment = new TravelFragment();
            fragmentManager.beginTransaction().replace(R.id.flContent, travelFragment).commit();
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment

        // Highlight the selected item, update the title, and close the drawer
        item.setChecked(true);
        SpannableString s = new SpannableString(item.getTitle());
        s.setSpan(new TypefaceSpan(this, "Lato-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setTitle(s);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    class getNameTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            ServiceCall serviceCall = new ServiceCall();
            return serviceCall.getJson("api_user/user_name/"+bundle.getString("cvid"));

        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);


            if (null == result || result.length() == 0) {

//                Toast.makeText(getApplicationContext(),
//                        "No data found from Server!!!", Toast.LENGTH_LONG)
//                        .show();

            } else {
                try {
                    JSONArray jsonArray = new JSONArray(result);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String name = jsonObject.optString("name").toString();
                        SpannableString nameSpan = new SpannableString(name);
                        nameSpan.setSpan(new TypefaceSpan(getApplicationContext(), "Lato-Regular.ttf"), 0, nameSpan.length(),
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        namess.setText(nameSpan);
                    }
                } catch (JSONException e) {
                    Log.d("mother", "ddddddddddddddd");
                }


            }
        }
    }
}

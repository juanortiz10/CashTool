package com.rhynyx.cashtool;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.rhynyx.cashtool.database.DataBaseHelper;
import com.rhynyx.cashtool.fragments.Accounts;
import com.rhynyx.cashtool.fragments.AccountsResume;
import com.rhynyx.cashtool.fragments.Expenses;
import com.rhynyx.cashtool.fragments.Index;
import com.rhynyx.cashtool.fragments.Revenue;
import com.rhynyx.cashtool.fragments.Settings;
import com.rhynyx.cashtool.services.NotificationService;
import com.rhynyx.cashtool.services.Receiver;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment myHome = new Index();
        android.support.v4.app.FragmentTransaction fragmentTransaction1
                = getSupportFragmentManager().beginTransaction();
        fragmentTransaction1.replace(R.id.content_frame, myHome);
        fragmentTransaction1.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        try{
            Intent alarmIntent = new Intent(this, Receiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,alarmIntent,0);
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            int interval = 1000/*28800000*/;
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),interval,pendingIntent);
            Toast.makeText(getApplicationContext(),"Set at",Toast.LENGTH_SHORT).show();
        }catch (Exception ex){}

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

        if (id == R.id.nav_settings) {
            fragment = new Settings();
            setTitle(R.string.settings);
        } else if (id == R.id.nav_home) {
            fragment = new Index();
            setTitle(R.string.home);
        } else if (id == R.id.nav_revenue) {
            fragment = new Revenue();
            setTitle(R.string.revenue);
        } else if (id == R.id.nav_expenses) {
            fragment = new Expenses();
            setTitle(R.string.expenses);
        } else if (id == R.id.nav_accounts) {
            fragment = new Accounts();
            setTitle(R.string.accounts);
        } else if (id == R.id.nav_account_resume) {
            fragment = new AccountsResume();
            setTitle(R.string.account_resume);
        }

        android.support.v4.app.FragmentTransaction fragmentTransaction1
                = getSupportFragmentManager().beginTransaction();
        fragmentTransaction1.replace(R.id.content_frame, fragment);
        fragmentTransaction1.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

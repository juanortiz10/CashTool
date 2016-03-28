package com.rhynyx.cashtool.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.rhynyx.cashtool.R;
import com.rhynyx.cashtool.services.Receiver;

/**
 * Created by juan on 5/03/16.
 */
public class Settings extends Fragment {
    Switch notiSwitch;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_settings, container, false);
        notiSwitch = (Switch)v.findViewById(R.id.switchSet);
            final Intent alarmIntent = new Intent(getActivity(), Receiver.class);
            final PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),0,alarmIntent,0);
            final AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
            final int interval = 28800000;
        if (getActivity().getSharedPreferences(getString(R.string.preference), Context.MODE_PRIVATE).getString("Status",null).equals("ON")) {
            notiSwitch.setChecked(true);

        }else {
            notiSwitch.setChecked(false);
        }
        notiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Context context = getActivity();
                SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                if (sharedPref.getString("Status", null).equals("OFF")) {
                    editor.putString("Status", "ON");
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);

                } else if (sharedPref.getString("Status", null).equals("ON")) {
                    editor.putString("Status", "OFF");
                    alarmManager.cancel(pendingIntent);
                }
                editor.commit();
                Toast toast = Toast.makeText(context, sharedPref.getString("Status", null), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        return v;
    }
}

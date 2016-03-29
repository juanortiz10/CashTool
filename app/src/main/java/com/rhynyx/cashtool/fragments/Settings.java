package com.rhynyx.cashtool.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.rhynyx.cashtool.MainActivity;
import com.rhynyx.cashtool.R;
import com.rhynyx.cashtool.services.Receiver;

import java.util.Locale;

/**
 * Created by juan on 5/03/16.
 */
public class Settings extends Fragment {
    Switch notiSwitch;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_settings, container, false);
        /**
         *
         *
         * ---------------ENCENDIDO Y APAGADO DE NOTIFICACIONES------->
         *
         *
         *
         */
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
        /**
         *
         *
         * ---------------Cambio de IDIOMA------------------------>
         *
         *
         *
         */

        RadioButton es = (RadioButton)v.findViewById(R.id.RBespañol);
        RadioButton en = (RadioButton)v.findViewById(R.id.RBingles);


        es.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {
                Context context = getActivity();
                SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("Language","MX");
                editor.commit();
                changeConf();
                Toast toast = Toast.makeText(context, sharedPref.getString("Language", null), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getActivity();
                SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("Language","US");
                editor.commit();
                changeConf();
                Toast toast = Toast.makeText(context, sharedPref.getString("Language", null), Toast.LENGTH_SHORT);
                toast.show();

            }
        });


        if (getActivity().getSharedPreferences(getString(R.string.preference), Context.MODE_PRIVATE).getString("Language",null).equals("MX")) {
            es.setChecked(true);

        }else if(getActivity().getSharedPreferences(getString(R.string.preference), Context.MODE_PRIVATE).getString("Language",null).equals("US")){
            en.setChecked(true);
        }else {
            es.setChecked(true);
        }


        return v;
    }

    private void changeConf(){
        Configuration conf = new Configuration();
        Locale locale;
        try{
            if(getActivity().getSharedPreferences(getString(R.string.preference), Context.MODE_PRIVATE).getString("Language",null).equals("MX")){
                locale = new Locale("es", getActivity().getSharedPreferences(getString(R.string.preference), Context.MODE_PRIVATE).getString("Language", null));

            }else{
                locale = new Locale("en", getActivity().getSharedPreferences(getString(R.string.preference), Context.MODE_PRIVATE).getString("Language", null));
            }
            conf.locale = locale;
            getResources().updateConfiguration(conf, getResources().getDisplayMetrics());
            Intent main = new Intent(getActivity().getApplicationContext(), MainActivity.class);
            getActivity().startActivity(main);
            getActivity().finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

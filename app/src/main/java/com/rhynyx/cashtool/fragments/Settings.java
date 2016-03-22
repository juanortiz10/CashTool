package com.rhynyx.cashtool.fragments;

import android.app.SharedElementCallback;
import android.content.Context;
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


        notiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Context context = getActivity();
                SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                boolean notStatus = notiSwitch.isChecked();
                if (notStatus) {

                    editor.putString("Status", "On");
                    Toast toast = Toast.makeText(context, R.string.notiOn, Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    editor.putString("Status", "Off");
                    Toast toast = Toast.makeText(context, R.string.notiOff, Toast.LENGTH_SHORT);
                    toast.show();
                }
                editor.commit();
            }
        });

        return v;
    }
}

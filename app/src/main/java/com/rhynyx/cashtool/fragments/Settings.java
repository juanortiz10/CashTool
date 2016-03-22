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
import android.widget.Switch;

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
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        boolean notStatus = notiSwitch.isChecked();
        if (notStatus){
            editor.putString("Status","On");
        }else {
            editor.putString("Status", "Off");
        }
        return v;
    }
}

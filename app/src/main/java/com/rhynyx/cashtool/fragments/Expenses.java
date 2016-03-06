package com.rhynyx.cashtool.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.rhynyx.cashtool.R;

/**
 * Created by juan on 5/03/16.
 */
public class Expenses extends Fragment {
    Spinner frecuency_options;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_expenses, container, false);
        frecuency_options = (Spinner) v.findViewById(R.id.frecuency_options);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.times, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frecuency_options.setAdapter(adapter);
        return v;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.nav_home).setChecked(false);
        menu.findItem(R.id.nav_expenses).setChecked(true);
    }

}
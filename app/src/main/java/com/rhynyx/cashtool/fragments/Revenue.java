package com.rhynyx.cashtool.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rhynyx.cashtool.R;
import com.rhynyx.cashtool.database.DataBaseHelper;

/**
 * Created by juan on 5/03/16.
 */
public class Revenue extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    Spinner frecuency_options2;
    EditText quantity_expenses_box,category_box;
    CheckBox check_repeat;
    Button btn_save_revenue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_revenue, container, false);

        frecuency_options2 = (Spinner) v.findViewById(R.id.frecuency_options2);
        frecuency_options2.setEnabled(false);

        quantity_expenses_box = (EditText)v.findViewById(R.id.quantity_expenses_box);
        category_box = (EditText) v.findViewById(R.id.category_box);

        check_repeat = (CheckBox) v.findViewById(R.id.check_repeat);
        check_repeat.setOnCheckedChangeListener(this);

        btn_save_revenue = (Button) v.findViewById(R.id.btn_save_revenue);
        btn_save_revenue.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.times, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frecuency_options2.setAdapter(adapter);
        return v;
    }

    @Override
    public void onClick(View v) {
        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity().getApplicationContext());
            Double quantity = Double.parseDouble(quantity_expenses_box.getText().toString().trim());
            String category = category_box.toString().trim();
            boolean isRepeatitive = check_repeat.isChecked();
            int whenToRepeat = frecuency_options2.getSelectedItemPosition();

            if(!isRepeatitive)
                whenToRepeat = -1;

            if(dataBaseHelper.insertNewRevenue(dataBaseHelper, category, quantity, isRepeatitive, whenToRepeat) > 0){
                category_box.setText("");
                check_repeat.setChecked(false);
                quantity_expenses_box.setText("");
                Toast.makeText(getActivity().getApplicationContext(), R.string.revenue_added,Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity().getApplicationContext(), R.string.something_is_wrong,Toast.LENGTH_SHORT).show();
            }
        }catch (Exception ex){
            Toast.makeText(getActivity().getApplicationContext(), R.string.something_is_wrong,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            frecuency_options2.setEnabled(true);
        }else{
            frecuency_options2.setEnabled(false);
        }
    }
}

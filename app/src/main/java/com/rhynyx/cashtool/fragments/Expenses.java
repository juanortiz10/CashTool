package com.rhynyx.cashtool.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
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
public class Expenses extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    Spinner frecuency_options;
    EditText quantity_expenses_box,category_box;
    CheckBox check_repeat;
    Button btn_save_expenses;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_expenses, container, false);

        frecuency_options = (Spinner) v.findViewById(R.id.frecuency_options);
        frecuency_options.setEnabled(false);
        quantity_expenses_box = (EditText) v.findViewById(R.id.quantity_expenses_box);
        category_box = (EditText) v.findViewById(R.id.category_box);

        check_repeat = (CheckBox) v.findViewById(R.id.check_repeat);
        check_repeat.setOnCheckedChangeListener(this);

        btn_save_expenses = (Button) v.findViewById(R.id.btn_save_expenses);
        btn_save_expenses.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.times, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frecuency_options.setAdapter(adapter);
        return v;
    }

    @Override
    public void onClick(View v) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity().getApplicationContext());
        Double quantity = Double.parseDouble(quantity_expenses_box.getText().toString().trim());
        String category = category_box.toString().trim();
        boolean isRepeat = check_repeat.isChecked();
        int whenDays = frecuency_options.getSelectedItemPosition();

        if (!isRepeat)
            whenDays = -1;
        try {
            if (dataBaseHelper.insertNewExpense(dataBaseHelper, category, quantity, isRepeat, whenDays) > 0) {
                category_box.setText("");
                check_repeat.setChecked(false);
                quantity_expenses_box.setText("");
                Toast.makeText(getActivity().getApplicationContext(), R.string.expense_added, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity().getApplicationContext(), R.string.something_is_wrong, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(getActivity().getApplicationContext(), R.string.something_is_wrong, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            frecuency_options.setEnabled(true);
        }else{
            frecuency_options.setEnabled(false);
        }
    }
}
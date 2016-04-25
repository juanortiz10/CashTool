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

import java.util.LinkedList;

/**
 * Created by juan on 5/03/16.
 */
public class Expenses extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    Spinner frecuency_options,how_many_optionse, category_box;
    EditText quantity_expenses_box;
    CheckBox check_repeat;
    Button btn_save_expenses;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_expenses, container, false);

        frecuency_options = (Spinner) v.findViewById(R.id.frecuency_options);
        frecuency_options.setEnabled(false);
        how_many_optionse = (Spinner) v.findViewById(R.id.how_many_optionse);
        how_many_optionse.setEnabled(false);

        quantity_expenses_box = (EditText) v.findViewById(R.id.quantity_expenses_box);
        category_box = (Spinner) v.findViewById(R.id.category_box);

        check_repeat = (CheckBox) v.findViewById(R.id.check_repeat);
        check_repeat.setOnCheckedChangeListener(this);

        btn_save_expenses = (Button) v.findViewById(R.id.btn_save_expenses);
        btn_save_expenses.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.times, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapterHow = ArrayAdapter.createFromResource(getActivity(),
                R.array.how_long, android.R.layout.simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterHow.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        DataBaseHelper db = new DataBaseHelper(getActivity().getApplicationContext());
        LinkedList list = db.getCategories("EG");
        ArrayAdapter adapterCat = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_box.setAdapter(adapterCat);

        frecuency_options.setAdapter(adapter);
        how_many_optionse.setAdapter(adapterHow);

        return v;
    }

    @Override
    public void onClick(View v) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity().getApplicationContext());
        Double quantity = Double.parseDouble(quantity_expenses_box.getText().toString().trim());
        String category = category_box.getSelectedItem().toString();
        boolean isRepeat = check_repeat.isChecked();
        int whenDays = frecuency_options.getSelectedItemPosition();
        int how_many = how_many_optionse.getSelectedItemPosition();

        if (!isRepeat) {
            whenDays = -1;
            how_many = -1;
        }
        try {
            if(whenDays !=0 && how_many!= 0) {
                if (dataBaseHelper.insertNewExpense(dataBaseHelper, category, quantity, isRepeat, whenDays, how_many) > 0) {

                    check_repeat.setChecked(false);
                    quantity_expenses_box.setText("");
                    Toast.makeText(getActivity().getApplicationContext(), R.string.expense_added, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.something_is_wrong, Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getActivity().getApplicationContext(), R.string.wrong_information,Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(getActivity().getApplicationContext(), R.string.something_is_wrong, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            frecuency_options.setEnabled(true);
            how_many_optionse.setEnabled(true);
        }else{
            frecuency_options.setEnabled(false);
            how_many_optionse.setEnabled(false);
        }
    }
}
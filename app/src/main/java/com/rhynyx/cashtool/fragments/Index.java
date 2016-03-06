package com.rhynyx.cashtool.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.rhynyx.cashtool.R;

/**
 * Created by juan on 5/03/16.
 */
public class Index extends Fragment implements View.OnClickListener{
    EditText monthly_payment_box,acum_money_box,total_acum_box,level_rich_box;
    Button btn_revenue, btn_expenses;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_index,container,false);

        monthly_payment_box = (EditText)v.findViewById(R.id.monthly_payment_box);
        acum_money_box = (EditText)v.findViewById(R.id.acum_money_box);
        total_acum_box = (EditText)v.findViewById(R.id.total_acum_box);
        level_rich_box = (EditText)v.findViewById(R.id.level_rich_box);
        btn_revenue = (Button)v.findViewById(R.id.btn_revenue);
        btn_expenses = (Button)v.findViewById(R.id.btn_expenses);

        btn_expenses.setOnClickListener(this);
        btn_revenue.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        if(v.getId() == R.id.btn_expenses){
              fragment = new Expenses();
              getActivity().setTitle(R.string.expenses);
        }else if(v.getId() == R.id.btn_revenue){
              fragment = new Revenue();
              getActivity().setTitle(R.string.revenue);
        }

        android.support.v4.app.FragmentTransaction fragmentTransaction1
                = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction1.replace(R.id.content_frame, fragment);
        fragmentTransaction1.commit();
    }
}

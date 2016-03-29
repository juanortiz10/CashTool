package com.rhynyx.cashtool.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rhynyx.cashtool.R;
import com.rhynyx.cashtool.database.DataBaseHelper;

/**
 * Created by juan on 5/03/16.
 */
public class Index extends Fragment implements View.OnClickListener{
    EditText monthly_payment_box,acum_money_year_box,total_acum_box,level_rich_box;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_index,container,false);

        monthly_payment_box = (EditText)v.findViewById(R.id.monthly_payment_box);
        acum_money_year_box = (EditText)v.findViewById(R.id.acum_money_box);
        total_acum_box = (EditText)v.findViewById(R.id.total_acum_box);
        level_rich_box = (EditText)v.findViewById(R.id.level_rich_box);


        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        new LoadInformation().execute();
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        android.support.v4.app.FragmentTransaction fragmentTransaction1
                = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction1.replace(R.id.content_frame, fragment);
        fragmentTransaction1.commit();
    }

     class LoadInformation extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity().getApplicationContext());
                    double total_acum = (dataBaseHelper.getAllRev() - dataBaseHelper.getAllExp());
                    double monthly_am = dataBaseHelper.getMonthlyAmount();
                    double acum_money_year = dataBaseHelper.getAnnualAmount();
                    double level_rich = dataBaseHelper.getRichLevel();

                  //  dataBaseHelper.checkExpensesUpdate();
                  //  dataBaseHelper.checkRevenueUpdate();

                    total_acum_box.setText("$".concat(String.valueOf(total_acum)));
                    monthly_payment_box.setText("$".concat(String.valueOf(monthly_am)));
                    acum_money_year_box.setText("$".concat(String.valueOf(acum_money_year)));
                    level_rich_box.setText("$".concat(String.valueOf(level_rich)));
                }
            });

            return null;
        }
    }
}

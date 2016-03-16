package com.rhynyx.cashtool.fragments;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.rhynyx.cashtool.R;

import static com.rhynyx.cashtool.R.color.green;

/**
 * Created by juan on 5/03/16.
 */
public class AccountsResume extends Fragment {
    TableLayout tabARIng,tabAREgr;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_accounts_resume, container, false);
        tabARIng = (TableLayout)v.findViewById(R.id.table_ARIng);
        tabAREgr = (TableLayout)v.findViewById(R.id.table_AREgr);

        TableRow cabeceraIng = getCabecera(v);
        cabeceraIng.setBackgroundColor(Color.GREEN);
        TableRow cabeceraEgr = getCabecera(v);
        cabeceraEgr.setBackgroundColor(Color.RED);
            tabAREgr.addView(cabeceraEgr);
            tabARIng.addView(cabeceraIng);
        llenarTabla(tabARIng,v);
        return v;
    }


    public TableRow getCabecera(View v){
        Resources res = getResources();
        TableRow cabecera = new TableRow(v.getContext());
        cabecera.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        cabecera.setPadding(5,5,5,5);
        for (String s : res.getStringArray(R.array.table_AccountsResume)) {
            TextView columna2 = new TextView(v.getContext());
            columna2.setText(s);
            columna2.setPadding(10,10,10,10);
            cabecera.addView(columna2);
        }
        return cabecera;
    }
    public void llenarTabla(TableLayout table, View v){
        Resources res = getResources();
       for (int i =0;i<10;i++) {
           TableRow tr = new TableRow(this.getActivity());
           for (int y=0;y<3;y++) {
               TextView data = new TextView(v.getContext());
               data.setText(y + "");
               tr.addView(data);
           }
           table.addView(tr);
       }
    }
}

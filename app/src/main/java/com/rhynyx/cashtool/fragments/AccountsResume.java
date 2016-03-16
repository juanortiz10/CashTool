package com.rhynyx.cashtool.fragments;

import android.app.ActionBar;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.rhynyx.cashtool.R;
import com.rhynyx.cashtool.database.DataBaseHelper;

import java.util.ArrayList;
import java.util.Iterator;

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
        cabeceraIng.setBackgroundResource(R.color.green);
        TableRow cabeceraEgr = getCabecera(v);
        cabeceraEgr.setBackgroundResource(R.color.red);
            tabAREgr.addView(cabeceraEgr);
            tabARIng.addView(cabeceraIng);
        llenarTabla(tabAREgr,v,"Egreso");
        llenarTabla(tabARIng,v,"Ingreso");
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
            columna2.setPadding(10, 10, 10, 10);
            columna2.setGravity(Gravity.CENTER | Gravity.BOTTOM);
            columna2.setWidth(TableRow.LayoutParams.MATCH_PARENT);
            cabecera.addView(columna2);
        }
        return cabecera;
    }

    public void llenarTabla(TableLayout table, View v, String nm){

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity().getApplicationContext());
        if (nm.equals("Ingreso")){
            ArrayList res = dataBaseHelper.getRev();
            Iterator it = res.iterator();
            while (it.hasNext()) {
                TableRow tr = new TableRow(this.getActivity());
                tr.setPadding(5,5,5,5);
                String[] data = (String[]) it.next();
                for (int y = 0; y < 3; y++) {
                    TextView tv = new TextView(v.getContext());
                    tv.setText(data[y]);
                    tv.setPadding(10, 10, 10, 10);
                    tv.setGravity(Gravity.CENTER | Gravity.BOTTOM);
                    tr.addView(tv);
                }
                table.addView(tr);
            }
            table.setStretchAllColumns(true);
        }else if(nm.equals("Egreso")){
            ArrayList res = dataBaseHelper.getExp();
            Iterator it = res.iterator();
            while (it.hasNext()){
                TableRow tr = new TableRow(this.getActivity());
                tr.setPadding(5,5,5,5);
                String[] data =(String[]) it.next();
                for (int y=0;y<3;y++) {
                    TextView tv = new TextView(v.getContext());
                    tv.setText(data[y]);
                    tv.setPadding(10, 10, 10, 10);
                    tv.setGravity(Gravity.CENTER | Gravity.BOTTOM);
                    tr.addView(tv);
                }
                table.addView(tr);
            }
            table.setStretchAllColumns(true);
        }else{}
    }
}

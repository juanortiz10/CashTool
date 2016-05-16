package com.rhynyx.cashtool.fragments;


import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.rhynyx.cashtool.R;
import com.rhynyx.cashtool.database.DataBaseHelper;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by poncho on 15/05/16.
 */
public class Detail extends Fragment{
    TableLayout tableDetail;
    TextView titleDetail;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.content_detail,container,false);
        final Spinner detail = (Spinner)v.findViewById(R.id.spinDetail);
         tableDetail= (TableLayout)v.findViewById(R.id.table_detail);
        titleDetail = (TextView) v.findViewById(R.id.title_detail);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.Ingreso_Egreso,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        detail.setAdapter(adapter);

        detail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (detail.getSelectedItem().toString().equals(getResources().getString(R.string.revenue))){
                    tableDetail.removeAllViewsInLayout();
                    titleDetail.setText(getResources().getString(R.string.revenue));
                    TableRow cabeceraIng = getCabecera(v);
                    cabeceraIng.setBackgroundResource(R.color.green);
                    tableDetail.addView(cabeceraIng);
                    llenarTabla(tableDetail,v,1);
                }else if (detail.getSelectedItem().toString().equals(getResources().getString(R.string.expenses))){
                    tableDetail.removeAllViewsInLayout();
                    titleDetail.setText(getResources().getString(R.string.expenses));
                    TableRow cabeceraEg = getCabecera(v);
                    cabeceraEg.setBackgroundResource(R.color.red);
                    tableDetail.addView(cabeceraEg);
                    llenarTabla(tableDetail,v,2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return v;
    }
    public TableRow getCabecera(View v){
        Resources res = getResources();
        TableRow cabecera = new TableRow(v.getContext());
        cabecera.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        cabecera.setPadding(5,5,5,5);
        for (String s : res.getStringArray(R.array.table_DetailHeader)) {
            TextView columna2 = new TextView(v.getContext());
            columna2.setText(s);
            columna2.setPadding(10, 10, 10, 10);
            columna2.setGravity(Gravity.CENTER | Gravity.BOTTOM);
            columna2.setWidth(TableRow.LayoutParams.MATCH_PARENT);
            cabecera.addView(columna2);
        }
        return cabecera;
    }
    public void llenarTabla(TableLayout table, View v, int option){

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity().getApplicationContext());
            ArrayList res = dataBaseHelper.getRevExp(option);
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
    }
}

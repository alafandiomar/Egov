package com.egov.egov.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.egov.egov.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;


public class TravelFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private FragmentActivity myContext;
    View view;
    boolean firstone = false,secondone = false;
    EditText arrivingDay,DepartingDay;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_travel, container, false);
        arrivingDay = (EditText)view.findViewById(R.id.arriving_date);
        DepartingDay = (EditText)view.findViewById(R.id.departing_date);
        arrivingDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        TravelFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(),"ssss");
                secondone = true;
                firstone = false;
            }
        });

        DepartingDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        TravelFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(),"ssss");
                firstone = true;
                secondone = false;
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        myContext =(FragmentActivity) context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        if(firstone)
            DepartingDay.setText(date);
        else
            arrivingDay.setText(date);
    }
}

package com.egov.egov.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.egov.egov.AsyncTaskGeneric.ServiceCall;
import com.egov.egov.Fonts;
import com.egov.egov.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import fr.ganfra.materialspinner.MaterialSpinner;


public class TravelFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    public HashMap<String, String> idPair;
    View view;
    boolean firstone = false, secondone = false;
    TextView arrivingDay, DepartingDay, departingLabel, arrivingLabel;
    ImageButton btn;
    SharedPreferences sharedPreferences;
    ArrayList<String> PoliceStationList = new ArrayList<>();
    MaterialSpinner to;
    private FragmentActivity myContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        idPair = new HashMap<String, String>();
        view = inflater.inflate(R.layout.fragment_travel, container, false);
        to = (MaterialSpinner) view.findViewById(R.id.spinner2);
        to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new getPolice_id().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        arrivingDay = (TextView) view.findViewById(R.id.arriving);
        DepartingDay = (TextView) view.findViewById(R.id.departing);
        departingLabel = (TextView) view.findViewById(R.id.departing_label);
        arrivingLabel = (TextView) view.findViewById(R.id.arriving_label);
        btn = (ImageButton) view.findViewById(R.id.sendbtn);
        sharedPreferences = this.getActivity().getSharedPreferences("Name", Context.MODE_PRIVATE);
        new getUser_id().execute();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new sendTravel().execute();
            }
        });
        Calendar now = Calendar.getInstance();
        arrivingDay.setText(now.get(Calendar.DAY_OF_MONTH) + "/" + now.get(Calendar.MONTH) + 1 + "/" + now.get(Calendar.YEAR));
        DepartingDay.setText(now.get(Calendar.DAY_OF_MONTH) + "/" + now.get(Calendar.MONTH) + 1 + "/" + now.get(Calendar.YEAR));
        DepartingDay.setTypeface(Fonts.getTypeFace(getContext(), "light"));
        arrivingDay.setTypeface(Fonts.getTypeFace(getContext(), "light"));
        departingLabel.setTypeface(Fonts.getTypeFace(getContext(), "reg"));
        arrivingLabel.setTypeface(Fonts.getTypeFace(getContext(), "reg"));

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
                dpd.show(getActivity().getFragmentManager(), "ssss");
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
                dpd.show(getActivity().getFragmentManager(), "ssss");
                firstone = true;
                secondone = false;
            }
        });
        new NamePolice().execute();
        return view;
    }

    String getArrivingdate() {
        return arrivingDay.getText().toString();
    }

    String getDepartingdate() {
        return DepartingDay.getText().toString();
    }

    String getPoliceStationName() {
        Log.d(to.getSelectedItem().toString(), to.getSelectedItem().toString());
        return to.getSelectedItem().toString();
    }

    @Override
    public void onAttach(Context context) {
        myContext = (FragmentActivity) context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        if (firstone)
            DepartingDay.setText(date);
        else
            arrivingDay.setText(date);
    }

    class getPolice_id extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            ServiceCall serviceCall = new ServiceCall();
            return serviceCall.getJson("/api_ps/ps_no/" + getPoliceStationName());

        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);


            if (null == result || result.length() == 0) {


            } else {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.optString("id").toString();
                        idPair.put("ps_id", id);
                        Log.d("finish1", "finish1");
                    }
                } catch (JSONException e) {
                    Log.d("mother", "ddddddddddddddd");
                }

            }

        }
    }

    class getUser_id extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            ServiceCall serviceCall = new ServiceCall();
            return serviceCall.getJson("/api_user/user_no/" + sharedPreferences.getString("cvid", "NULL"));

        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);


            if (null == result || result.length() == 0) {


            } else {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.optString("id").toString();
                        idPair.put("id", id);
                    }
                } catch (JSONException e) {
                    Log.d("mother", "ddddddddddddddd");
                }

            }

        }
    }

    class sendTravel extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("finish2", "finish2");
        }

        @Override
        protected String doInBackground(String... params) {

            Log.d("finish3", "finish3");
            ServiceCall serviceCall = new ServiceCall();
            return serviceCall.PutComplaintTravel("Travel Notice", "Travel Date is : " + getDepartingdate() + " Arrival Date is " + getArrivingdate(), "traviling", idPair.get("ps_id").toString(), idPair.get("id").toString());

        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            Toast.makeText(getContext(), "Your Travel Notification Has Been Sent !", Toast.LENGTH_SHORT).show();
        }
    }

    class NamePolice extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            ServiceCall serviceCall = new ServiceCall();
            return serviceCall.getJson("api_ps/all_ps");

        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);


            if (null == result || result.length() == 0) {

//                Toast.makeText(getApplicationContext(),
//                        "No data found from Server!!!", Toast.LENGTH_LONG)
//                        .show();

            } else {
                try {
                    JSONArray jsonArray = new JSONArray(result);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String name = jsonObject.optString("name").toString();
                        PoliceStationList.add(name);

                    }
                } catch (JSONException e) {
                    Log.d("mother", "ddddddddddddddd");
                }

                String[] stockArr = new String[PoliceStationList.size()];
                stockArr = PoliceStationList.toArray(stockArr);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, stockArr);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                to.setAdapter(adapter);

            }
        }
    }
}

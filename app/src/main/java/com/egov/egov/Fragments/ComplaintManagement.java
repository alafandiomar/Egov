package com.egov.egov.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.egov.egov.AsyncTaskGeneric.ServiceCall;
import com.egov.egov.Fonts;
import com.egov.egov.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ComplaintManagement extends Fragment {

    SearchView searchView;
    TextView status;
    TextView name;
    TextView complaints;

    HashMap<String, String> idPair;
SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public ComplaintManagement() {
        idPair = new HashMap<>();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPreferences = getActivity().getSharedPreferences("Name", Context.MODE_APPEND);
        editor = sharedPreferences.edit();
        View view = inflater.inflate(R.layout.fragment_complaint_management, container, false);
        searchView = (SearchView) view.findViewById(R.id.searchView);
        status = (TextView) view.findViewById(R.id.status);
        name = (TextView) view.findViewById(R.id.name);
        complaints = (TextView) view.findViewById(R.id.complaints);
        new getComplaints().execute();
        searchView.setQueryHint("Complaint ID");
        status.setTypeface(Fonts.getTypeFace(getContext(), "reg"));
        name.setTypeface(Fonts.getTypeFace(getContext(), "light"));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                new getCompStage().execute();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    String getQuery() {
        return searchView.getQuery().toString();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    class getCompStage extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            ServiceCall serviceCall = new ServiceCall();
            return serviceCall.getJson("/api_comp/stage/" + getQuery());

        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);


            if (null == result || result.length() == 0) {
                name.setText("Not Found !! ");
            } else {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.optString("name").toString();
                        idPair.put("name", name);
                    }
                    name.setText(idPair.get("name"));
                } catch (JSONException e) {
                    Log.d("mother", "ddddddddddddddd");

                }

            }

        }
    }

    class getComplaints extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            ServiceCall serviceCall = new ServiceCall();
            return serviceCall.getJson("/api_comp/all_comp/"+sharedPreferences.getString("cvid","null"));

        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);


            if (null == result || result.length() == 0) {
            } else {
                try {
                    String Full = "Your Complaints have the following IDs :  ";
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.optString("complaint_id").toString();
                        Full += name+"   ";
                    }
                    complaints.setText( Full);
                } catch (JSONException e) {
                    Log.d("mother", "ddddddddddddddd");

                }

            }

        }
    }

}

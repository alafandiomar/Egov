package com.egov.egov.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.egov.egov.AsyncTaskGeneric.ServiceCall;
import com.egov.egov.Fonts;
import com.egov.egov.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;


public class ComplaintDialogFragment extends DialogFragment {


    TextInputLayout material_Subject;
    TextInputLayout material_info;
    MaterialSpinner to;
    EditText subject;
    EditText info;
    TextView title;
    ArrayList<String> PoliceStationList = new ArrayList<>();

    public ComplaintDialogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complaint_dialog, container, false);


        to = (MaterialSpinner) view.findViewById(R.id.spinner);
        new NamePolice().execute();
        material_Subject = (TextInputLayout) view.findViewById(R.id.material_subject);
        material_info = (TextInputLayout) view.findViewById(R.id.material_info);
        subject = (EditText) view.findViewById(R.id.Subject_textbox);
        info = (EditText) view.findViewById(R.id.info_textbox);
        title = (TextView) view.findViewById(R.id.title);
        material_info.setTypeface(Fonts.getTypeFace(getActivity(), "reg"));
        material_Subject.setTypeface(Fonts.getTypeFace(getActivity(), "reg"));
        subject.setTypeface(Fonts.getTypeFace(getActivity(), "light"));
        info.setTypeface(Fonts.getTypeFace(getActivity(), "light"));
        Bundle args = getArguments();
        title.setText(args.getString("title"));
        title.setTextColor(Color.WHITE);
        title.setTypeface(Fonts.getTypeFace(getActivity(), "reg"));
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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

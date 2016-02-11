package com.egov.egov.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egov.egov.AsyncTaskGeneric.ServiceCall;
import com.egov.egov.DummyContent.DummyContentPhone.DummyItem;
import com.egov.egov.ListAdapters.MyPhoneDirectoryRecyclerViewAdapter;
import com.egov.egov.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PhoneDirectoryFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    ArrayList<HashMap<String, String>> PoliceStationList = new ArrayList<>();
    RecyclerView recyclerView;
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PhoneDirectoryFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PhoneDirectoryFragment newInstance(int columnCount) {
        PhoneDirectoryFragment fragment = new PhoneDirectoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phonedirectory_list, container, false);
        // Set the adapter
        recyclerView = (RecyclerView) view.findViewById(R.id.list);

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        new PhonePolice().execute();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }

    class PhonePolice extends AsyncTask<String, Void, String> {

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
                        HashMap<String, String> PoliceStation = new HashMap<String, String>();

                        String id = jsonObject.optString("id").toString();
                        String name = jsonObject.optString("name").toString();
                        String location = jsonObject.optString("location").toString();
                        String phone_number = jsonObject.optString("phone_number").toString();
                        PoliceStation.put("id", id);
                        PoliceStation.put("name", name);
                        PoliceStation.put("location", location);
                        PoliceStation.put("phone_number", phone_number);
                        Log.d("output", id + "   " + name + "  " + location + "  " + phone_number);
                        PoliceStationList.add(PoliceStation);
                    }
                } catch (JSONException e) {
                    Log.d("mother", "ddddddddddddddd");
                }
                recyclerView.setAdapter(new MyPhoneDirectoryRecyclerViewAdapter(PoliceStationList, getContext()));

            }

        }

    }
}

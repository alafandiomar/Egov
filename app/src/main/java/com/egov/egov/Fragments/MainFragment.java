package com.egov.egov.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.egov.egov.AsyncTaskGeneric.ServiceCall;
import com.egov.egov.Fonts;
import com.egov.egov.ListAdapters.NewsRecyclerViewAdapter;
import com.egov.egov.R;
import com.egov.egov.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    ArrayList<HashMap<String, String>> NewsList = new ArrayList<>();
    ArrayList<HashMap<String, String>> AllNewsList = new ArrayList<>();
    TextView txt;
    ListView recyclerView;
    private SliderLayout mDemoSlider;
    String getURL = "http://192.168.1.109:81/Internal_Affairs_API/index.php/api_news/all_news";

    com.android.volley.RequestQueue queue;
    public MainFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void getMethod(String url) {
        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        ArrayList<HashMap<String, String>> NewsList = new ArrayList<>();

                        Log.d("Response", response.toString());
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                HashMap<String, String> NewsItem = new HashMap<String, String>();

                                String id = jsonObject.optString("id").toString();
                                String text = jsonObject.optString("text").toString();
                                String attacment_id = jsonObject.optString("ref").toString();
                                NewsItem.put("id", id);
                                NewsItem.put("text", text);
                                NewsItem.put("ref", ServiceCall.imageUrl + "/" + attacment_id);
                                Log.d("output", id + "  " + text + "  " + ServiceCall.imageUrl + attacment_id);
                                NewsList.add(NewsItem);
                            }
                        } catch (JSONException e) {
                            Log.d("mother", "ddddddddddddddd");
                        }

                        recyclerView.setAdapter(new NewsRecyclerViewAdapter(NewsList,getActivity().getApplicationContext()));
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
    }
        @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState){
            View view = inflater.inflate(R.layout.fragment_main, container, false);
            mDemoSlider = (SliderLayout) view.findViewById(R.id.slider);
            recyclerView = (ListView) view.findViewById(R.id.list);
            queue = VolleySingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue();
            getMethod(getURL);
            txt = (TextView) view.findViewById(R.id.newsbar);
            new SliderNewsTask().execute();


            return view;
        }

        // TODO: Rename method, update argument and hook method into UI event

        @Override
        public void onAttach (Context context){
            super.onAttach(context);
        }

        @Override
        public void onDetach () {
            super.onDetach();
        }


        @Override
        public void onPageScrolled ( int position, float positionOffset, int positionOffsetPixels){

        }

        @Override
        public void onPageSelected ( int position){

        }

        @Override
        public void onPageScrollStateChanged ( int state){

        }

        @Override
        public void onSliderClick (BaseSliderView slider){

        }

        class SliderNewsTask extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {

                ServiceCall serviceCall = new ServiceCall();
                return serviceCall.getJson("api_news/all_news");

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
                            HashMap<String, String> NewsItem = new HashMap<String, String>();

                            String id = jsonObject.optString("id").toString();
                            String text = jsonObject.optString("text").toString();
                            String attacment_id = jsonObject.optString("ref").toString();
                            NewsItem.put("id", id);
                            NewsItem.put("text", text);
                            NewsItem.put("ref", ServiceCall.imageUrl + "/" + attacment_id);
                            Log.d("output", id + "  " + text + "  " + ServiceCall.imageUrl + attacment_id);
                            NewsList.add(NewsItem);
                        }
                    } catch (JSONException e) {
                        Log.d("mother", "ddddddddddddddd");
                    }
                    String s = "";
                    for (int i = 0; i < 2; i++) {
                        s += NewsList.get(i).get("text") + "   ****   ";
                    }
                    recyclerView.setAdapter(new NewsRecyclerViewAdapter(NewsList,getActivity() ));
                    txt.setText(s);
                    txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                    txt.setPadding(10, 10, 0, 0);
                    txt.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    txt.setSingleLine();
                    txt.setMarqueeRepeatLimit(10);
                    txt.setFocusable(true);
                    txt.setHorizontallyScrolling(true);
                    txt.setFocusableInTouchMode(true);
                    txt.requestFocus();
                    txt.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                    txt.setTypeface(Fonts.getTypeFace(getContext(), "reg"), Typeface.BOLD);

                    for (int j = 0; j < 2; j++) {
                        TextSliderView textSliderView = new TextSliderView(getContext());
                        // initialize a SliderLayout

                        textSliderView
                                .description(NewsList.get(j).get("text"))
                                .image(NewsList.get(j).get("ref"))
                                .setScaleType(BaseSliderView.ScaleType.Fit)
                        ;


                        mDemoSlider.addSlider(textSliderView);
                    }
                    mDemoSlider.setPresetTransformer(SliderLayout.Transformer.ZoomOut);
                    mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Top);
                    mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                    mDemoSlider.setDuration(4000);


                }

            }

        }

        class AllNewsTask extends AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected ArrayList<HashMap<String, String>> doInBackground(String... params) {

                ServiceCall serviceCall = new ServiceCall();
                String res = serviceCall.getJson("api_news/all_news");
                try {
                    JSONArray jsonArray = new JSONArray(res);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HashMap<String, String> NewsItem = new HashMap<String, String>();

                        String id = jsonObject.optString("id").toString();
                        String text = jsonObject.optString("text").toString();
                        String attacment_id = jsonObject.optString("ref").toString();
                        NewsItem.put("id", id);
                        NewsItem.put("text", text);
                        NewsItem.put("ref", ServiceCall.imageUrl + "/" + attacment_id);
                        Log.d("output", id + "  " + text + "  " + ServiceCall.imageUrl + attacment_id);
                        AllNewsList.add(NewsItem);
                    }
                    return AllNewsList;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<HashMap<String, String>> list) {
                // TODO Auto-generated method stub
                super.onPostExecute(list);
                for (int i = 0; i < list.size(); i++) {
                    AllNewsList.add(list.get(i));
                }
                Log.d("list size in ", list.toString());

            }


        }
    }


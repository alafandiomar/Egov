package com.egov.egov;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.egov.egov.AsyncTaskGeneric.ServiceCall;
import com.egov.egov.Utils.CustomRequest;
import com.egov.egov.Utils.TypefaceSpan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Complaint extends AppCompatActivity {
@Bind(R.id.imageView1)
    ImageView imageView;
    @Bind(R.id.imageView2)
    ImageView imageView2;
    @Bind(R.id.sendbtn)
    ImageButton sendbtn;
    TextInputLayout material_Subject;
    TextInputLayout material_info;
    Spinner to;
    EditText subject;
    EditText info;
    TextView label;
    ArrayList<String> PoliceStationList = new ArrayList<>();
    boolean first = false;
    boolean second = false;
    SharedPreferences sharedPreferences,sharedPreferences2;
    SharedPreferences.Editor editor,editor2;
    private ProgressDialog dialog = null;
    private int serverResponseCode = 0;
    RequestQueue queue;
    public HashMap<String, String> idPair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        ButterKnife.bind(this);
        idPair = new HashMap<>();

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        sharedPreferences = getSharedPreferences("file",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        sharedPreferences2 = getSharedPreferences("Name",MODE_PRIVATE);
        editor2 = sharedPreferences2.edit();
        new getUser_id().execute();

        Bundle bundle = this.getIntent().getExtras();
        SpannableString title = new SpannableString(bundle.getString("title").toString());
        title.setSpan(new TypefaceSpan(this, "Lato-Regular.ttf"), 0, title.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        setTitle(title);

        Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.userid)).getBitmap();
        imageView.setImageBitmap(bitmap);
        Bitmap bitmap2 = ((BitmapDrawable) getResources().getDrawable(R.drawable.docscan)).getBitmap();
        imageView2.setImageBitmap(bitmap2);
        to = (Spinner) findViewById(R.id.spinner);
        new NamePolice().execute();
        material_Subject = (TextInputLayout) findViewById(R.id.material_subject);
        material_info = (TextInputLayout) findViewById(R.id.material_info);
        subject = (EditText) findViewById(R.id.Subject_textbox);
        info = (EditText) findViewById(R.id.info_textbox);
        label = (TextView) findViewById(R.id.label);
        label.setTypeface(Fonts.getTypeFace(this,"reg"));
        material_info.setTypeface(Fonts.getTypeFace(this, "reg"));
        material_Subject.setTypeface(Fonts.getTypeFace(this, "reg"));
        subject.setTypeface(Fonts.getTypeFace(this, "light"));
        info.setTypeface(Fonts.getTypeFace(this, "light"));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                first = true;
                second = false;
                Intent intent = new Intent(getApplicationContext(), ImageChooserActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                first = false;
                second = true;
                Intent intent = new Intent(getApplicationContext(), ImageChooserActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(Complaint.this, "", "Uploading file...", true);
                final String name = sharedPreferences.getString("name","NULLLLL");
                final String name2 = sharedPreferences.getString("name2","NULLLLL");

                System.out.println(name);
                System.out.println(name2);
                new Thread(new Runnable() {
                    public void run() {

                        uploadFile(name);
                        uploadFile(name2);
                        getIDphoto(1);
                        getIDphoto(2);

                    }
                }).start();
                new Thread(new Runnable() {
                    public void run() {

                        try {
                            Thread.sleep(3000);
                            new SendComplaint().execute();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();


            }
        });
        to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new getPolice_id().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    public int uploadFile(String sourceFileUri) {


        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(ServiceCall.upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){

                    runOnUiThread(new Runnable() {
                        public void run() {
                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                                    +" F:/wamp/wamp/www/uploads";

                            Toast.makeText(getApplicationContext(), "File Upload Complete.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                dialog.dismiss();
                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "MalformedURLException", Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload file to server Exception", "Exception : "  + e.getMessage(), e);
            }
            dialog.dismiss();
            return serverResponseCode;

        } // End else block

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && first) {
            String filePath = data.getStringExtra("RESULT_FILE_PATH");
            editor.putString("name",filePath);
            editor.commit();
            Bitmap bitmap= BitmapFactory.decodeFile(filePath);
            imageView.setImageBitmap(bitmap);
        }
        if (requestCode == 1 && resultCode == RESULT_OK && second) {
            String filePath = data.getStringExtra("RESULT_FILE_PATH");
            editor.putString("name2",filePath);
            editor.commit();
            Bitmap bitmap= BitmapFactory.decodeFile(filePath);
            imageView2.setImageBitmap(bitmap);
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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, stockArr);
                adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                to.setAdapter(adapter);

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
            return serviceCall.getJson("/api_user/user_no/" + sharedPreferences2.getString("cvid", "NULL"));

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
    class SendComplaint extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            ServiceCall serviceCall = new ServiceCall();
            Log.d("here",idPair.get("id")+"  "+idPair.get("ps_id")+"   "+getSub()+"  "+getContent()+"   "+sharedPreferences.getString("id"+1,null)+"   "+sharedPreferences.getString("id"+2,null));
            return serviceCall.putComplaint(idPair.get("id"),idPair.get("ps_id"),getSub(),getContent(),sharedPreferences.getString("id"+1,null),sharedPreferences.getString("id"+2,null),"haha worked");

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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, stockArr);
                adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                to.setAdapter(adapter);

            }
        }
    }
    public Map<String, String> getParams(int i){
        Map<String, String> params = new HashMap<String, String>();
        switch (i)
        {
            case 1:
                params.put("name", sharedPreferences.getString("name","NULLLLL"));
                break;
            case 2:
                params.put("name",sharedPreferences.getString("name2","NULLLLL"));
                break;
        }
        Log.d("hohooooooooooooo",sharedPreferences.getString("name","NULLLLL") + sharedPreferences.getString("name2","NULLLLL"));
        return params;
    }

    String getSub()
    {
        return subject.getText().toString();
    }
    String getContent()
    {
        return info.getText().toString();
    }
    String getPoliceStationName() {
        Log.d(to.getSelectedItem().toString(), to.getSelectedItem().toString());
        return to.getSelectedItem().toString();
    }

    public void getIDphoto(final int i) {
        // prepare the Request
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, ServiceCall.getPhotoIDUri, getParams(i), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("doc_id");
                    editor.putString("id"+i,jsonObject1.getString("doc_id").toString());
                    editor.commit();
                    System.out.println("i am here" + i +"  "+sharedPreferences.getString("id"+i,"Nul"+i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    ,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        queue.add(jsObjRequest);
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
}

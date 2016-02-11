package com.egov.egov;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.egov.egov.AsyncTaskGeneric.ServiceCall;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    public SharedPreferences sharedPreferences;
    @Bind(R.id.civil_number)
    EditText civilNumber;
    @Bind(R.id.input_password)
    EditText password;
    @Bind(R.id.btn_login)
    Button login_btn;
    @Bind(R.id.link_signup)
    TextView Sign_up;
    @Bind(R.id.forgotBtn)
    Button forgotbtn;
    @Bind(R.id.civil_number_material)
    TextInputLayout civilnumbermat;
    @Bind(R.id.input_password_material)
    TextInputLayout passwordmaterial;
    Fonts fonts;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("Name", MODE_APPEND);
        editor = sharedPreferences.edit();
        this.login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginTask().execute();
            }
        });

        this.Sign_up.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                        startActivity(intent);
                    }
                }
        );

        this.forgotbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Forgot.class);
                startActivity(intent);
            }
        });


        login_btn.setTypeface(Fonts.getTypeFace(this, "reg"));
        Sign_up.setTypeface(Fonts.getTypeFace(this, "light"));
        password.setTypeface(Fonts.getTypeFace(this, "reg"));
        civilNumber.setTypeface(Fonts.getTypeFace(this, "reg"));
        forgotbtn.setTypeface(Fonts.getTypeFace(this, "reg"));
        civilnumbermat.setTypeface(Fonts.getTypeFace(this, "reg"));
        passwordmaterial.setTypeface(Fonts.getTypeFace(this, "reg"));

    }

    String getPassword() {
        return password.getText().toString();
    }

    String getCvid() {
        return civilNumber.getText().toString();
    }

    class LoginTask extends AsyncTask<String, Void, String> {
        String mun;
        private int status;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            ServiceCall serviceCall = new ServiceCall();
            return serviceCall.LoginPostJson(getCvid(), getPassword());

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
                System.out.println(result);
                if (result.equals("1")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("cvid", getCvid());
                    editor.putString("cvid", getCvid());
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                Log.d("haaaaaaaaaaaa", "haaaaaaaaaa");

            }

        }

    }
}


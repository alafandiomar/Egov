package com.egov.egov;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.egov.egov.AsyncTaskGeneric.ServiceCall;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {

    @Bind(R.id.civil_number_material)
    TextInputLayout civil_number_material;
    @Bind(R.id.civil_number)
    EditText civil_number;
    @Bind(R.id.full_name_material)
    TextInputLayout full_name_material;
    @Bind(R.id.full_name)
    EditText full_name;
    @Bind(R.id.input_email_material)
    TextInputLayout input_email_material;
    @Bind(R.id.input_email)
    EditText input_email;
    @Bind(R.id.input_password_material)
    TextInputLayout input_password_material;
    @Bind(R.id.input_password)
    EditText input_password;
    @Bind(R.id.re_input_password_material)
    TextInputLayout re_input_password_material;
    @Bind(R.id.re_input_password)
    EditText re_input_password;
    @Bind(R.id.btn_signup)
    Button btn_signup;
    @Bind(R.id.link_login)
    TextView Link_login;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("Name", MODE_APPEND);
        editor = sharedPreferences.edit();
        civil_number.setTypeface(Fonts.getTypeFace(this, "light"));
        civil_number_material.setTypeface(Fonts.getTypeFace(this, "reg"));
        full_name_material.setTypeface(Fonts.getTypeFace(this, "reg"));
        full_name.setTypeface(Fonts.getTypeFace(this, "light"));
        input_email.setTypeface(Fonts.getTypeFace(this, "light"));
        input_email_material.setTypeface(Fonts.getTypeFace(this, "reg"));
        input_password.setTypeface(Fonts.getTypeFace(this, "light"));
        input_password_material.setTypeface(Fonts.getTypeFace(this, "reg"));
        re_input_password.setTypeface(Fonts.getTypeFace(this, "light"));
        re_input_password_material.setTypeface(Fonts.getTypeFace(this, "reg"));
        btn_signup.setTypeface(Fonts.getTypeFace(this, "reg"));
        Link_login.setTypeface(Fonts.getTypeFace(this, "light"));
        Link_login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                }
        );

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SignUpTask().execute();
            }
        });
    }

    String getCvid() {
        return civil_number.getText().toString();
    }

    String getPassword() {
        return input_password.getText().toString();
    }

    String getEmail() {
        return input_email.getText().toString();
    }

    String getName() {
        return full_name.getText().toString();
    }

    class SignUpTask extends AsyncTask<String, Void, String> {
        String mun;
        private int status;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            ServiceCall serviceCall = new ServiceCall();
            return serviceCall.putSignUP(getCvid(), getName(), getPassword(), getEmail());

        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            Bundle bundle = new Bundle();
            bundle.putString("cvid", getCvid());
            bundle.putString("name", getName());
            editor.putString("cvid", getCvid());
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtras(bundle);
            Toast.makeText(getApplicationContext(), "Your Account Has Been Activated", Toast.LENGTH_SHORT).show();
            startActivity(intent);

        }

    }

}



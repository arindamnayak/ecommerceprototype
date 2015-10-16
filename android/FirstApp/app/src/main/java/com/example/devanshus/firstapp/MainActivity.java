package com.example.devanshus.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.*;

public class MainActivity extends AppCompatActivity {
    EditText username,password;
    Button login,signup;
     ParseUser parseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            Parse.initialize(this, "qG2zJIcWHHBXSHoy8XJV0TLRLpTkdrbg1rHtOUFX", "VG5mKd6Kd4EC9ZqXtSPg2Ts0I7Dty2mrHixpjNVv");
            if(ParseUser.getCurrentUser()!=null){
                Intent intent = new Intent(MainActivity.this, CategoryListActivity.class);
                startActivity(intent);}

        }catch(Exception e){

        }
        username = (EditText)findViewById(R.id.fld_username);
        password = (EditText)findViewById(R.id.fld_pwd);
        login = (Button)findViewById(R.id.btn_login);
        signup = (Button)findViewById(R.id.btn_signup);

        parseUser = new ParseUser();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = username.getText().toString();
                String pwd = password.getText().toString();

                parseUser.setUsername(uname);
                parseUser.setPassword(pwd);

                parseUser.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(MainActivity.this, "SignUp successful", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "SignUp Failure", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = username.getText().toString();
                String pwd = password.getText().toString();

                parseUser.logInInBackground(uname, pwd, new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            // Toast.makeText(MainActivity.this,"Login successful",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, CategoryListActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Login Failure", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });


    }

    @Override
    public void onBackPressed() {
       // finish();
    }
}

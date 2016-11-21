package com.dismas.imaya.touradvisor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class Login_RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    Button login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_registration);

        login = (Button) findViewById(R.id.btn_login_option);
        register = (Button) findViewById(R.id.btn_register);

        login.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.btn_login_option:

                // on login button click send to login activity

                Intent login = new Intent(getApplicationContext(), LoginActivity.class);

                startActivity(login);

                break;

            // on register button click send to register activity

            default:
                Intent registeration = new Intent(getApplicationContext(),
                        SignupActivity.class);

                startActivity(registeration);
                break;
        }

    }

    // on back key press exit the application.

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Login_RegistrationActivity.this,
                    SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

}
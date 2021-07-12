package com.rhs.psw;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText keyET = findViewById(R.id.keyET);
        PasswordStrengthView passwordSV= findViewById(R.id.passwordSV);
        PasswordStrengthView passwordSV0= findViewById(R.id.passwordSV0);
        PasswordStrengthView passwordSV1= findViewById(R.id.passwordSV1);
        PasswordStrengthView passwordSV2= findViewById(R.id.passwordSV2);
        PasswordStrengthView passwordSV3= findViewById(R.id.passwordSV3);
        PasswordStrengthView passwordSV4= findViewById(R.id.passwordSV4);

        passwordSV.attachEditText(keyET);
        passwordSV0.attachEditText(keyET);
        passwordSV1.attachEditText(keyET);
        passwordSV2.attachEditText(keyET);
        passwordSV3.attachEditText(keyET);
        passwordSV4.attachEditText(keyET);
    }
}
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

        passwordSV.attachEditText(keyET);
    }
}
package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String USERNAME = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        EditText userInput = (EditText) findViewById(R.id.userInput);
        EditText passwordInput = (EditText) findViewById(R.id.passwordInput);

        String username = userInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (username.equals("userdsl") && password.equals("123456")) {
            Intent intent = new Intent(this, DisplayDashboard.class);

            intent.putExtra(USERNAME, username);

            startActivity(intent);
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Credenciales incorrectas";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}

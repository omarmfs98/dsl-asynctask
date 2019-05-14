package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayDashboard extends AppCompatActivity {
    public int index = 0;
    private ProgressDialog dialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_dashboard);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.USERNAME);

        TextView textView = findViewById(R.id.welcomeUser);
        textView.setText("Bienvenido " + message);
    }


    public void calcOperation(View view) {
        RadioGroup group = (RadioGroup) findViewById(R.id.operations);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                index = radioGroup.indexOfChild(radioButton);
            }
        });

        EditText num1 = (EditText) findViewById(R.id.num1);
        EditText num2 = (EditText) findViewById(R.id.num2);

        String val1 = num1.getText().toString();
        String val2 = num2.getText().toString();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Calculando...");
        switch (index) {
            case 0:
                dialog.setTitle("Suma entre números");
                break;
            case 1:
                dialog.setTitle("Resta entre números");
                break;
            case 2:
                dialog.setTitle("Multiplicación entre números");
                break;
            case 3:
                dialog.setTitle("División entre números");
                break;
        }

        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(false);
        new LongOperation().execute(val1, val2);

    }

    private class LongOperation extends AsyncTask<String, Float, String> {
        @Override
        protected void onPreExecute() {
            dialog.setProgress(0);
            dialog.setMax(100);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            for (int i = 0; i < 20; i++) {
                try {Thread.sleep(1); }
                catch (InterruptedException e) {}

                publishProgress(i/20f);
            }

            String val = "";

            switch (index) {
                case 0:
                    val = "El resultado es: " + Integer.toString(Integer.parseInt(params[0]) + Integer.parseInt(params[1]));
                    break;
                case 1:
                    val = "El resultado es: " + Integer.toString(Integer.parseInt(params[0]) - Integer.parseInt(params[1]));
                    break;
                case 2:
                    val = "El resultado es: " + Integer.toString(Integer.parseInt(params[0]) * Integer.parseInt(params[1]));
                    break;
                case 3:
                    if (Integer.parseInt(params[1]) != 0) {
                        val = "El resultado es: " + Float.toString(Float.parseFloat(params[0]) / Float.parseFloat(params[1]));
                    } else {
                        val = "No se puede dividir entre 0";
                    }
                    break;
            }
            return val;
        }

        protected void onProgressUpdate (Float... valores) {
            int p = Math.round(100*valores[0]);
            dialog.setProgress(p);
        }


        protected void onPostExecute(String value) {
            dialog.dismiss();


            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, value, duration);
            toast.show();
        }
    }
}

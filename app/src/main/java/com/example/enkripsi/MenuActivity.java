package com.example.enkripsi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


    public class MenuActivity extends Activity implements OnClickListener {

        Button enkrips, howto, about,exit;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_menu);
            enkrips = (Button) findViewById(R.id.btnenkrips);
            howto = (Button) findViewById(R.id.btnhowto);
            about = (Button) findViewById(R.id.btnabout);
            exit = (Button) findViewById(R.id.btnexit);

            enkrips.setOnClickListener(this);
            howto.setOnClickListener(this);
            about.setOnClickListener(this);
            exit.setOnClickListener(this);

        }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnenkrips:
                Intent enkripsi = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(enkripsi);
                break;

            case R.id.btnhowto:
                Intent howto = new Intent(MenuActivity.this, HowtoActivity.class);
                startActivity(howto);
                break;

            case R.id.btnabout:
                Intent about = new Intent(MenuActivity.this, AboutActivity.class);
                startActivity(about);
                break;
            case R.id.btnexit:
                onBackPressed();
                break;
        }
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Do you want Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MenuActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present. getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
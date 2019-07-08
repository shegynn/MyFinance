package com.example.shegy.myfinance;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class About extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        String versionName = BuildConfig.VERSION_NAME;

        TextView tvVerProgram = findViewById(R.id.tvVerProgram);
        tvVerProgram.setText("Версия:" + versionName);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }
}

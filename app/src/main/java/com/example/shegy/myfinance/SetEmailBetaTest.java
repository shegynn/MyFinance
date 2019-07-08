package com.example.shegy.myfinance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SetEmailBetaTest extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_email_beta_test);
    }

    public void onClickContinue(View v){
        Intent intent = new Intent(this, FinalPageSetFirstParamActivity.class);
        startActivity(intent);
    }
}

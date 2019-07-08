package com.example.shegy.myfinance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TwoPageSetFirstParamActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two_page_set_first_param);
    }

    public void onClickContinue(View v){
        Intent intent = new Intent(this, ThreePageSetFirstParamActivity.class);
        startActivity(intent);
    }
}

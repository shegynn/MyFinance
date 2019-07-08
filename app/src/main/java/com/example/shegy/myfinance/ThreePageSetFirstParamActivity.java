package com.example.shegy.myfinance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ThreePageSetFirstParamActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.three_page_set_first_param);

        boolean firstCard = getIntent().getBooleanExtra("firstCard", true);
        TextView tvHeadQuestionThreePageSetFirstParam = findViewById(R.id.tvHeadQuestionThreePageSetFirstParam);

        if (firstCard) {
            tvHeadQuestionThreePageSetFirstParam.setText(R.string.headline_question_three_page_set_first_param);
        }else{
            tvHeadQuestionThreePageSetFirstParam.setText(R.string.headline_question_more_three_page_set_first_param);
        }

    }

    public void onClickAnswerYes(View v){
        Intent intent = new Intent(this, AddBankCardActivity.class);
        intent.putExtra("set_first_param", true);
        startActivity(intent);
    }

    public void onClickAnswerNo(View v){
        Intent intent = new Intent(this, SetEmailBetaTest.class);
        startActivity(intent);
    }
}

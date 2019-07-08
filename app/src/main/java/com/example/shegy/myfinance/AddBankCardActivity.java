package com.example.shegy.myfinance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddBankCardActivity extends AppCompatActivity {

    boolean set_first_param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bank_card);

        set_first_param = getIntent().getBooleanExtra("set_first_param", false);

        Spinner spTypeCard = findViewById(R.id.spTypeCard);
        String[] typeCard = {"Дебетовая", "Кредитовая"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeCard);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spTypeCard.setAdapter(adapter);

    }

    public void onClickContinue(View v){

        if (set_first_param) {
            Intent intent = new Intent(this, ThreePageSetFirstParamActivity.class);
            intent.putExtra("firstCard", false);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}

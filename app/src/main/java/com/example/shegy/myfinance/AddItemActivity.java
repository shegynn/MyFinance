package com.example.shegy.myfinance;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddItemActivity extends MainActivity {

    String typeItem;
    EditText etNameItem;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        typeItem = getIntent().getStringExtra("typeItem");

        etNameItem = findViewById(R.id.etNameItem);
    }

    public void saveItem(View v) {

        String nameItem = etNameItem.getText().toString();

        if (nameItem.isEmpty()) {
            Toast.makeText(this, "Не указано Наименование", Toast.LENGTH_LONG).show();
            return;
        }

        DbHelper mDbHelper = new DbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String SQL_INSERT_ITEM  = "INSERT INTO ITEM (NAME, TYPE) VALUES " +
                                  "('" + nameItem + "', '" + typeItem + "')";

        try {
            db.execSQL(SQL_INSERT_ITEM);
        }catch (Exception e){
            Toast.makeText(this, "Не удалось записать новую статью затрат", Toast.LENGTH_LONG);
            return;
        }

        Intent intent = new Intent(this, AddIncomeExpanseActivity.class);
        intent.putExtra("typeItem", typeItem);
        startActivity(intent);

    }

}

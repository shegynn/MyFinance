package com.example.shegy.myfinance;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddIncomeExpanseActivity extends MainActivity {

    private static  final int REQUEST_ACCESS_TYPE=1;
    String typeItem;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_income_expanse);

        typeItem = getIntent().getStringExtra("typeItem");

        fillSpinner(typeItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_select_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case R.id.add_item:
                Intent intent = new Intent(this, AddItemActivity.class);
                intent.putExtra("typeItem", typeItem);
                startActivityForResult(intent, REQUEST_ACCESS_TYPE);
                //startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public int getIdItem(String value, Map<Integer, String> hMap) {

        for(Map.Entry<Integer, String> item : hMap.entrySet()){

            if (value.equals(item.getValue())) {
                return item.getKey();
            }
        }

        return -1;

    }

    public void fillSpinner(final String typeItem) {

        ListView lvItems = findViewById(R.id.listItems);
        final List<String> arrayItems = new ArrayList<>();
        @SuppressLint("UseSparseArrays") final Map<Integer, String> hMap = new HashMap<Integer, String>();


        DbHelper mDbHelper = new DbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor cursorIncome;

        String selectQuery = "SELECT * FROM ITEM WHERE type = " + typeItem + "";
        cursorIncome = db.rawQuery(selectQuery, null);

        if (cursorIncome != null) {
            if (cursorIncome.moveToFirst()) {
                do {
                    Integer idItem = cursorIncome.getInt(cursorIncome.getColumnIndex("ID"));
                    String nameItem = cursorIncome.getString(cursorIncome.getColumnIndex("NAME"));
                    //
                    arrayItems.add(nameItem);
                    hMap.put(idItem, nameItem);
                    //
                } while (cursorIncome.moveToNext());
            }
            cursorIncome.close();
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayItems);
        lvItems.setAdapter(adapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                // по позиции получаем выбранный элемент
                String selectedItem = arrayItems.get(position);
                int idItem = getIdItem(selectedItem, hMap);

                if (idItem == -1) {
                    Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(AddIncomeExpanseActivity.this, SetSumActivity.class);
                intent.putExtra("nameItem", selectedItem);
                intent.putExtra("idItem", idItem);
                intent.putExtra("typeItem", typeItem);
                startActivity(intent);

            }
        });
    }
}

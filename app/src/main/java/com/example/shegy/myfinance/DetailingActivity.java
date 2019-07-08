package com.example.shegy.myfinance;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class DetailingActivity extends MainActivity {

    String startData;
    String endData;
    String typeItem;

    public String getDate(String DateTime) {

        int ind = DateTime.indexOf(" ");

        return DateTime.substring(0, ind);
    }

    public String getTime(String DateTime) {

        int ind = DateTime.indexOf(" ");

        return DateTime.substring(ind);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailing);

        startData = getIntent().getStringExtra("startDate");
        endData = getIntent().getStringExtra("endDate");
        typeItem = getIntent().getStringExtra("typeItem");

        ListView listView = findViewById(R.id.liDetail);

        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        HashMap<String, String> hashMap;

        DbHelper mDbHelper = new DbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor cursorExpense;

        String nameTable   = typeItem.equals("1") ? "EXPENSES" : "INCOME";
        String selectQuery = "SELECT " + nameTable + ".DATE_EVENT, ITEM.NAME, " + nameTable + ".SUM, " + nameTable + ".NOTE FROM " + nameTable + " INNER JOIN ITEM ON " + nameTable + ".ID_ITEM = ITEM.ID";

        if (!startData.equals("") || !endData.equals("")) {
            selectQuery += " WHERE " + nameTable + ".DATE_EVENT BETWEEN '" + startData + "' AND '" + endData + "'";
        }

        cursorExpense = db.rawQuery(selectQuery, null);

        if (cursorExpense != null) {
            if (cursorExpense.moveToFirst()) {
                do {

                    String sDateTime = cursorExpense.getString(cursorExpense.getColumnIndex("DATE_EVENT"));
                    String sDate = getDate(sDateTime);
                    String sTime = getTime(sDateTime);

                    hashMap = new HashMap<>();
                    hashMap.put("DATE", sDate);
                    hashMap.put("TIME", sTime);
                    hashMap.put("ITEM", cursorExpense.getString(cursorExpense.getColumnIndex("NAME")));
                    hashMap.put("SUM",  String.format("%.2f", cursorExpense.getDouble(cursorExpense.getColumnIndex("SUM"))));
//                    hashMap.put("NOTE", cursorExpense.getString(cursorExpense.getColumnIndex("NOTE")));
                    arrayList.add(hashMap);

                } while (cursorExpense.moveToNext());
            }
            cursorExpense.close();
        }

        ListAdapter adapter = new SimpleAdapter(this, arrayList, R.layout.list_item_detail,
                new String[]{"DATE", "TIME", "ITEM", "SUM"},
                new int[]{
                        R.id.tvDate, R.id.tvTime, R.id.tvItem, R.id.tvSum});
        listView.setAdapter(adapter);

    }

}

package com.example.shegy.myfinance;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SetSumActivity extends MainActivity {

    int idItem;
    String nameItem;
    String typeItem;
    long dateMillis;
    String saveDate;

    TextView tvDView;
    EditText etSum;
    EditText etComment;
    Calendar dateAndTime = Calendar.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_sum);

        idItem = getIntent().getIntExtra("idItem", -1);
        nameItem = getIntent().getStringExtra("nameItem");
        typeItem = getIntent().getStringExtra("typeItem");

        tvDView     = findViewById(R.id.tvDate);
        etSum       = findViewById(R.id.etSum);
        etComment   = findViewById(R.id.etComment);

        setInitialDateTime();

    }

    public void setDate(View v) {

        int currentYear = Calendar.YEAR;
        int currentMonth = Calendar.MONTH;
        int currentDayOfMonth = Calendar.DAY_OF_MONTH;

        DatePickerDialog dpDialog = new DatePickerDialog(this, dateListener, dateAndTime.get(currentYear), dateAndTime.get(currentMonth), dateAndTime.get(currentDayOfMonth));
        dpDialog.show();

    }

    public void saveResult(View v) {

        String currentSum = etSum.getText().toString();
        String currentDate = tvDView.getText().toString();
        String currentNote = etComment.getText().toString();

        if (currentSum.isEmpty()) {
            Toast.makeText(this, "Не указана СУММА", Toast.LENGTH_LONG).show();
            return;
        }

        if (currentDate.isEmpty()) {
            Toast.makeText(this, "Не указана ДАТА", Toast.LENGTH_LONG).show();
            return;
        }

        DbHelper mDbHelper = new DbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String nameTable        = typeItem.equals("1") ? "EXPENSES" : "INCOME";
        String SQL_INSERT_ITEM  = "INSERT INTO " + nameTable + " (ID_ITEM, DATE_EVENT, SUM, NOTE, FLAG_LOC_BASE, FLAG_SYNC) VALUES " +
                                  "(" + idItem + ", '" + saveDate + "', " + Double.parseDouble(currentSum) + ", '" + currentNote + "', 1, 0)";

        db.execSQL(SQL_INSERT_ITEM);

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    private void setInitialDateTime() {

        SimpleDateFormat formatDayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        dateMillis = dateAndTime.getTimeInMillis();
        saveDate = formatDayTime.format(dateMillis);

        String currentDate = DateUtils.formatDateTime(this, dateMillis,DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);// | DateUtils.FORMAT_SHOW_TIME);
        tvDView.setText(currentDate);
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

}

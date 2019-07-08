package com.example.shegy.myfinance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public String currentDateTime;
    public String startDateTime;
    public String startCurrentMonth;
    public String endCurrentMonth;
    public String startCurrentWeek;

    double sumIncomeTotal;
    double sumIncomeMonth;
    double sumIncomeWeek;
    double sumIncomeDay;

    double sumExpenseTotal;
    double sumExpenseMonth;
    double sumExpenseWeek;
    double sumExpenseDay;

    double sumBalanceTotal;
    double sumBalanceMonth;
    double sumBalanceWeek;
    double sumBalanceDay;

    // имя файла настройки
    public static final String APP_PREFERENCES = "appsettings";
    public static final String APP_PREFERENCES_FIRST_USE = "firstuse";
    public static final String APP_PREFERENCES_EMAIL_BETA_USER = "emailbetauser";

    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        boolean firstUse = mSettings.getBoolean(APP_PREFERENCES_FIRST_USE, true);

        setTitle("MY FINANCE");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int startDayWeek = calendar.getFirstDayOfWeek() - 1;

        Date currentDateTimeD = getDateTime(year, month, day, hour, minute, second);
        Date startDateTimeD = getDateTime(year, month, day, 1, 0, 0);
        Date startCurrentMonthD = getDateTime(year, month, 1, 1, 0, 0);
        Date endCurrentMonthD = getDateTime(year, month, day, 23, 59, 59);
        Date startCurrentWeekD = getDateTime(year, month, startDayWeek, 1, 0, 0);

        SimpleDateFormat formatDayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        currentDateTime = formatDayTime.format(currentDateTimeD);
        startDateTime = formatDayTime.format(startDateTimeD);
        startCurrentMonth = formatDayTime.format(startCurrentMonthD);
        endCurrentMonth = formatDayTime.format(endCurrentMonthD);
        startCurrentWeek = formatDayTime.format(startCurrentWeekD);

        if (!firstUse) {
            Intent intent = new Intent(this, OnePageSetFirstParamActivity.class);
            startActivity(intent);
            finish();
        }else{
            fillIndicators();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case R.id.about:
                Intent intent = new Intent(this, About.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


   private Date getDateTime(int year, int month, int day, int hour, int minute, int second) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, second);

        return calendar.getTime();
   }

   public void fillIndicators() {

        // Income
        TextView tvIncomeTotal  = findViewById(R.id.income_total);
        TextView tvIncomeMonth  = findViewById(R.id.income_month);
        TextView tvIncomeWeek   = findViewById(R.id.income_week);
        TextView tvIncomeDay    = findViewById(R.id.income_day);

        // Expenses
        TextView tvExpensesTotal  = findViewById(R.id.expenses_total);
        TextView tvExpensesMonth  = findViewById(R.id.expenses_month);
        TextView tvExpensesWeek   = findViewById(R.id.expenses_week);
        TextView tvExpensesDay    = findViewById(R.id.expenses_day);

        // Balance
        TextView tvBalanceTotal  = findViewById(R.id.balance_total);
        TextView tvBalanceMonth  = findViewById(R.id.balance_month);
        TextView tvBalanceWeek   = findViewById(R.id.balance_week);
        TextView tvBalanceDay    = findViewById(R.id.balance_day);

        // get data
        sumIncomeTotal = getSumIncome("", "");
        sumIncomeMonth = getSumIncome(startCurrentMonth, endCurrentMonth);
        sumIncomeWeek = getSumIncome(startCurrentWeek, currentDateTime);
        sumIncomeDay = getSumIncome(startDateTime, currentDateTime);

        sumExpenseTotal = getSumExpense("", "");
        sumExpenseMonth = getSumExpense(startCurrentMonth, endCurrentMonth);
        sumExpenseWeek = getSumExpense(startCurrentWeek, currentDateTime);
        sumExpenseDay = getSumExpense(startDateTime, currentDateTime);

        sumBalanceTotal = sumIncomeTotal - sumExpenseTotal;
        sumBalanceMonth = sumIncomeMonth - sumExpenseMonth;
        sumBalanceWeek = sumIncomeWeek - sumExpenseWeek;
        sumBalanceDay = sumIncomeDay - sumExpenseDay;

        // set data
        tvIncomeTotal.setText(String.format("%.2f", sumIncomeTotal) + " руб");
        tvIncomeMonth.setText(String.format("%.2f", sumIncomeMonth) + " руб");
        tvIncomeWeek.setText(String.format("%.2f", sumIncomeWeek) + " руб");
        tvIncomeDay.setText(String.format("%.2f", sumIncomeDay) + " руб");

        tvExpensesTotal.setText(String.format("%.2f", sumExpenseTotal) + " руб");
        tvExpensesMonth.setText(String.format("%.2f", sumExpenseMonth) + " руб");
        tvExpensesWeek.setText(String.format("%.2f", sumExpenseWeek) + " руб");
        tvExpensesDay.setText(String.format("%.2f", sumExpenseDay) + " руб");

        tvBalanceTotal.setText(String.format("%.2f", sumBalanceTotal) + " руб");
        tvBalanceMonth.setText(String.format("%.2f", sumBalanceMonth) + " руб");
        tvBalanceWeek.setText(String.format("%.2f", sumBalanceWeek) + " руб");
        tvBalanceDay.setText(String.format("%.2f", sumBalanceDay) + " руб");

        if (sumBalanceTotal < 0){
            tvBalanceTotal.setTextColor(Color.parseColor("#FF0000"));
        }

        if (sumBalanceMonth < 0){
            tvBalanceMonth.setTextColor(Color.parseColor("#FF0000"));
        }

        if (sumBalanceWeek < 0){
            tvBalanceWeek.setTextColor(Color.parseColor("#FF0000"));
        }

        if (sumBalanceDay < 0){
            tvBalanceDay.setTextColor(Color.parseColor("#FF0000"));
        }
    }


    public double getSumIncome(String startData, String endData) {

        DbHelper mDbHelper = new DbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor cursorIncome;
        double totalSum = 0;

        String selectQuery = "SELECT * FROM INCOME";

        if (!startData.equals("") || !endData.equals("")) {
            selectQuery += " WHERE DATE_EVENT BETWEEN '" + startData + "' AND '" + endData + "'";
        }

        cursorIncome = db.rawQuery(selectQuery, null);

        if (cursorIncome != null) {
            if (cursorIncome.moveToFirst()) {
                do {
                    totalSum += cursorIncome.getDouble(cursorIncome.getColumnIndex("SUM"));
                } while (cursorIncome.moveToNext());
            }
            cursorIncome.close();
        }

        return totalSum;
    }

    public double getSumExpense(String startData, String endData) {

        DbHelper mDbHelper = new DbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor cursorExpense;
        double totalSum = 0;

        String selectQuery = "SELECT * FROM EXPENSES";

        if (!startData.equals("") || !endData.equals("")) {
            selectQuery += " WHERE DATE_EVENT BETWEEN '" + startData + "' AND '" + endData + "'";
        }

        cursorExpense = db.rawQuery(selectQuery, null);

        if (cursorExpense != null) {
            if (cursorExpense.moveToFirst()) {
                do {
                    totalSum += cursorExpense.getDouble(cursorExpense.getColumnIndex("SUM"));
                } while (cursorExpense.moveToNext());
            }
            cursorExpense.close();
        }

        return totalSum;

    }


    public void onClickAddIncome(View v) {

        Intent intent = new Intent(MainActivity.this, AddIncomeExpanseActivity.class);
        intent.putExtra("typeItem", "2");
        startActivity(intent);

    }

    public void onClickAddExpense(View v) {

        Intent intent = new Intent(MainActivity.this, AddIncomeExpanseActivity.class);
        intent.putExtra("typeItem", "1");
        startActivity(intent);

    }


    public void onClickDetailTotalExpense(View v) {

        if (sumExpenseTotal != 0) {
            Intent intent = new Intent(MainActivity.this, DetailingActivity.class);
            intent.putExtra("typeItem", "1");
            intent.putExtra("startDate", "");
            intent.putExtra("endDate", "");
            startActivity(intent);
        }

    }

    public void onClickDetailMonthExpense(View v) {

        if (sumExpenseMonth != 0) {
            Intent intent = new Intent(MainActivity.this, DetailingActivity.class);
            intent.putExtra("typeItem", "1");
            intent.putExtra("startDate", startCurrentMonth);
            intent.putExtra("endDate", endCurrentMonth);
            startActivity(intent);
        }

    }

    public void onClickDetailWeekExpense(View v) {

        if (sumExpenseWeek != 0) {
            Intent intent = new Intent(MainActivity.this, DetailingActivity.class);
            intent.putExtra("typeItem", "1");
            intent.putExtra("startDate", startCurrentWeek);
            intent.putExtra("endDate", currentDateTime);
            startActivity(intent);
        }

    }

    public void onClickDetailDayExpense(View v) {

        if (sumExpenseDay != 0) {
            Intent intent = new Intent(MainActivity.this, DetailingActivity.class);
            intent.putExtra("typeItem", "1");
            intent.putExtra("startDate", startDateTime);
            intent.putExtra("endDate", currentDateTime);
            startActivity(intent);
        }

    }



    public void onClickDetailTotalIncome(View v) {

        if (sumIncomeTotal != 0) {
            Intent intent = new Intent(MainActivity.this, DetailingActivity.class);
            intent.putExtra("typeItem", "2");
            intent.putExtra("startDate", "");
            intent.putExtra("endDate", "");
            startActivity(intent);
        }

    }

    public void onClickDetailMonthIncome(View v) {

        if (sumIncomeMonth != 0) {
            Intent intent = new Intent(MainActivity.this, DetailingActivity.class);
            intent.putExtra("typeItem", "2");
            intent.putExtra("startDate", startCurrentMonth);
            intent.putExtra("endDate", endCurrentMonth);
            startActivity(intent);
        }

    }

    public void onClickDetailWeekIncome(View v) {

        if (sumIncomeWeek != 0) {
            Intent intent = new Intent(MainActivity.this, DetailingActivity.class);
            intent.putExtra("typeItem", "2");
            intent.putExtra("startDate", startCurrentWeek);
            intent.putExtra("endDate", currentDateTime);
            startActivity(intent);
        }

    }

    public void onClickDetailDayIncome(View v) {

        if (sumIncomeDay != 0) {
            Intent intent = new Intent(MainActivity.this, DetailingActivity.class);
            intent.putExtra("typeItem", "2");
            intent.putExtra("startDate", startDateTime);
            intent.putExtra("endDate", currentDateTime);
            startActivity(intent);
        }
    }
}

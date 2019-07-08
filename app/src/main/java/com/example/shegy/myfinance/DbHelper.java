package com.example.shegy.myfinance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_finance.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // ITEM TABLE (в этой таблице будем хранить статьи затрат)

        String SQL_CREATE_ITEM_TABLE    =   "CREATE TABLE ITEM (" +
                                            "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                            "NAME TEXT NOT NULL, " +
                                            "TYPE INTEGER NOT NULL)";

        // INCOME TABLE (в этой таблице будем хранить движения по доходам)

        String SQL_CREATE_INCOME_TABLE  =   "CREATE TABLE INCOME (" +
                                            "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                            "ID_ITEM INTEGER NOT NULL, " +
                                            "DATE_EVENT STRING NOT NULL, " +
                                            "SUM REAL NOT NULL, " +
                                            "NOTE TEXT, " +
                                            "FLAG_LOC_BASE INTEGER NOT NULL, " +
                                            "FLAG_SYNC INTEGER)";

        // EXPENSES TABLE (в этой таблице будем хранить движения по расходам)

        String SQL_CREATE_EXPENSES_TABLE  = "CREATE TABLE EXPENSES (" +
                                            "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                            "ID_ITEM INTEGER NOT NULL, " +
                                            "DATE_EVENT STRING NOT NULL, " +
                                            "SUM REAL NOT NULL, " +
                                            "NOTE TEXT, " +
                                            "FLAG_LOC_BASE INTEGER NOT NULL, " +
                                            "FLAG_SYNC INTEGER)";


        // Запускаем создание таблиц
        db.execSQL(SQL_CREATE_ITEM_TABLE);
        db.execSQL(SQL_CREATE_INCOME_TABLE);
        db.execSQL(SQL_CREATE_EXPENSES_TABLE);

        // Добавляем основные статьи
        String SQL_INSERT_ITEM =    "INSERT INTO ITEM (NAME, TYPE) VALUES " +
                                    "('Продукты питания', 1), ('Одежда', 1), ('Сотовая связь', 1), ('Интернет', 1), ('Бензин', 1), ('Комунальные платежи', 1), ('Кафе', 1), ('Развлечения', 1), ('Прочие расходы', 1), ('Заработная плата', 2), ('Прочие доходы', 2)";

        db.execSQL(SQL_INSERT_ITEM);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

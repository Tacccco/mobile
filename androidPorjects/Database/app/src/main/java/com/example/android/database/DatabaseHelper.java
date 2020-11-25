package com.example.android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = " UserDB.db ";
    public static final int DATABASE_VERSION = 3;
    public static final String TABLE_USER = " user_info ";
    public static final String COLUMN_USER_ID = " user_id ";
    public static final String COLUMN_USER_NAME = " user_name ";
    public static final String COLUMN_USER_PASS = " user_pass ";
    public static final String COLUMN_USER_GENDER = " user_gender ";
    public static final String COLUMN_USER_PHONE = " user_phone ";
    public static final String COLUMN_USER_AGE = " user_age ";
    public static final String COLUMN_USER_BIRTH = " user_birth ";
    public static final String TABLE_LAST = " last_user ";
    public static final String COLUMN_LAST = " user_name ";
    public static final String COLUMN_LAST_ID = " user_id ";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "Create table " + TABLE_USER + "( " +
                COLUMN_USER_ID + "INTEGER Primary Key AUTOINCREMENT, " +
                COLUMN_USER_NAME + " varchar(30), " +
                COLUMN_USER_PASS + " varchar(30), " +
                COLUMN_USER_GENDER + " varchar(1), " +
                COLUMN_USER_PHONE + " varchar(30), " +
                COLUMN_USER_AGE + " INTEGER, " +
                COLUMN_USER_BIRTH + " DATE " +
                ");";
        String query2 = "Create table " + TABLE_LAST + "("+
                COLUMN_LAST_ID + " INTEGER Primary Key AUTOINCREMENT, " +
                COLUMN_LAST + " varchar(30) " + ");";
        db.execSQL(query);
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAST);
        onCreate(db);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public void addUser(User user){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME, user.getName());
        contentValues.put(COLUMN_USER_PASS, user.getPassword());
        contentValues.put(COLUMN_USER_GENDER, user.getGender());
        contentValues.put(COLUMN_USER_PHONE, user.getPhone());
        contentValues.put(COLUMN_USER_AGE, user.getAge());
        contentValues.put(COLUMN_USER_BIRTH, user.getBirth());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_USER, null ,contentValues);
        db.close();
    }
    public void registerLastUser(User user){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_LAST, user.getName());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_LAST, null ,contentValues);
        db.close();
    }
    public String returnLastUser(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_LAST +"where " + COLUMN_LAST_ID + " order by user_id desc ;";
        Cursor c = db.rawQuery(query,null);
        String uname = "";
        if(c!= null && c.moveToFirst()){
            uname = c.getString(c.getColumnIndex("user_name"));
        }
        return uname;
    }
    public void DeleteAllUser(){

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_USER);
        db.close();
    }
    public boolean isUser(String name, String password){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_NAME + " = '" + name + "' and " + COLUMN_USER_PASS + " = '" + password + "';";
        Cursor c = db.rawQuery(query,null);
        //c.moveToFirst();
        if(c != null && c.moveToFirst() ) {
            Log.i("IDcheck", c.getString(c.getColumnIndex("user_name")));
            c.close();
            return true;
        }
        c.close();
        return false;

    }

    public User getUser(String name, String password){
        User user = new User();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_NAME + " = '" + name + "' and " + COLUMN_USER_PASS + " = '" + password + "';";
        Cursor c = db.rawQuery(query,null);

        if(c != null && c.moveToFirst() ) {
            user.setId(c.getInt(c.getColumnIndex("user_id")));
            user.setName(c.getString(c.getColumnIndex("user_name")));
            user.setPassword(c.getString(c.getColumnIndex("user_pass")));
            user.setAge(c.getInt(c.getColumnIndex("user_age")));
            user.setBirth(c.getString(c.getColumnIndex("user_birth")));
            user.setPhone(c.getString(c.getColumnIndex("user_phone")));
            user.setGender(c.getString(c.getColumnIndex("user_gender")));
            c.close();
            return user;
        }
        c.close();
        return null;
    }
    public void updateUser(User user){
        SQLiteDatabase db = getWritableDatabase();
        
        String query = "UPDATE " + TABLE_USER + " SET " + COLUMN_USER_NAME + " = '" + user.getName() +  "' , " +
                COLUMN_USER_PHONE + " = '" + user.getPhone()+  "' , " +
                COLUMN_USER_AGE + " = " + user.getAge() + " , " +
                COLUMN_USER_GENDER + " = '" + user.getGender() +  "' , " +
                COLUMN_USER_BIRTH + " = '" + user.getBirth() + "' WHERE user_id =  " + user.getId() + ";";
        db.execSQL(query);

    }

    public void updatePassword(int id, String new_pass){
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_USER + " SET " + COLUMN_USER_PASS + " = '" +new_pass+ "' where " + COLUMN_USER_ID + " = " + id +";";
        db.execSQL(query);
    }

    public String DBtoString(){
        String dbs = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE 1";

        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            dbs += cursor.getString(cursor.getColumnIndex("user_id"));
            dbs += " "+cursor.getString(cursor.getColumnIndex("user_name"));
            dbs += " "+cursor.getString(cursor.getColumnIndex("user_phone"));
            dbs += " "+cursor.getString(cursor.getColumnIndex("user_pass"));
            dbs += " "+cursor.getInt(cursor.getColumnIndex("user_age"));
            dbs += " "+cursor.getString(cursor.getColumnIndex("user_gender"));
            dbs += " "+cursor.getString(cursor.getColumnIndex("user_birth"));
            dbs += "\n";
            cursor.moveToNext();
        }
        db.close();
        return dbs;
    }

}

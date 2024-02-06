package com.designproject.designproject.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Register details table column variables
    public static final String DATABASE_PROJECT = "swanring.db";
    public static final String TABLE_REGISTER = "register_table";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String COLUMN_IS_LOGGED = "IS_LOGGED";


    //User details table column variables
    public static final String TABLE_USER_DETAILS = "user_details_table";
    public static final String USER_ID = "USER_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_FULL_NAME = "USER_FULL_NAME";
    public static final String USER_MOBILE = "USER_MOBILE";
    public static final String USER_ADDRESS = "USER_ADDRESS";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_PROJECT, null, 1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create register table
        String CREATE_REGISTER_TABLE_QUERY = "CREATE TABLE " + TABLE_REGISTER + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_IS_LOGGED + " INTEGER)";
        sqLiteDatabase.execSQL(CREATE_REGISTER_TABLE_QUERY);

        //Create user details table
        String CREATE_USER_DETAILS_TABLE_QUERY = "CREATE TABLE " + TABLE_USER_DETAILS + " (" +
                USER_ID + " INTEGER PRIMARY KEY, " +
                USER_NAME + " TEXT, " +
                USER_FULL_NAME + " TEXT, " +
                USER_MOBILE + " TEXT, " +
                USER_ADDRESS + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_USER_DETAILS_TABLE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists register_table");
        sqLiteDatabase.execSQL("drop Table if exists user_details_table");
    }

    // Insert data into the register table
    public boolean insertData(String username, String password, String email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_IS_LOGGED, 0);
        long result = sqLiteDatabase.insert(TABLE_REGISTER, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    // Check if a username exists in the register table
    public boolean checkusername(String username) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from register_table where USERNAME = ?", new String[]{username});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    // Check if a username, password, and email match in the register table
    public boolean checkusernamepassword(String username,String password,String email ) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from register_table where USERNAME = ? and PASSWORD = ? and EMAIL = ?", new String[]{username,password,email});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    // Update Login status in register table
    public void updateLoginStatus(String userName, int staus) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_LOGGED, staus);
        db.update(TABLE_REGISTER , values , "USERNAME = ?" , new String[]{userName});
    }

//    Update All Login Status in register table
    public void updateAllLoginStatus(int staus) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_LOGGED, staus);
        db.update(TABLE_REGISTER , values , null , null);
    }

    public Cursor getAllAccounts() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_REGISTER, null);
        return result;
    }

    //insert profile details when registering
    public boolean insertProfileDetails(int id, String userName, String fullName, String address, String mobile ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID, id);
        contentValues.put(USER_NAME, userName);
        contentValues.put(USER_FULL_NAME, fullName);
        contentValues.put(USER_ADDRESS, address);
        contentValues.put(USER_MOBILE, mobile);
        long result = db.insert(TABLE_USER_DETAILS, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //    UPDATE Password in Register Table
    public boolean updatePassword(String userName, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PASSWORD, newPassword);
        long count = db.update(TABLE_REGISTER, contentValues, "USERNAME = ?", new String[]{userName});
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    //get the data of specific user profile by account ID
    public Cursor getUserProfileById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_USER_DETAILS + " WHERE " + USER_ID + " = ?", new String[]{String.valueOf(id)});
        return result;
    }

    // update the profile details using account id
    public boolean updateProfileDetails(int id, String userName, String fullName, String address, String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, userName);
        contentValues.put(USER_FULL_NAME, fullName);
        contentValues.put(USER_MOBILE, mobile);
        contentValues.put(USER_ADDRESS, address);
        long count = db.update(TABLE_USER_DETAILS, contentValues,
                "USER_ID = ?", new String[]{String.valueOf(id)});
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
}




package com.saidur.blooddonor.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Db_helper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BloodDonate_Database";
    private static final int VERSION = 01;

    public final String TABLE_NAME = "Donors";

    public final String ID = "Id";
    public final String AGE = "Age";
    public final String NAME = "Name";
    public final String ADDRESS ="Address";
    public final String PROFILE_IMAGE ="Profile";
    public final String BLOOD_GROUP = "Bloodgroup";
    public final String COUNTRACT_NUMBERS = "Contact_numbr";
    public final String LAST_DONATON_DATE = "Last_donation_date";

    private final String sql = "CREATE TABLE "+TABLE_NAME+" ("+ID+" INTEGER primary key , "+NAME + " TEXT, "+AGE +" INTEGER ,"
            +PROFILE_IMAGE+" TEXT NOT NULL,"+BLOOD_GROUP+" TEXT ,"+COUNTRACT_NUMBERS+" TEXT ,"+LAST_DONATON_DATE+" TEXT ,"+ADDRESS+" TEXT "+");";

    @Override
    public void onCreate(SQLiteDatabase db) {
   db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Db_helper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

}

package com.shata.calculathandangle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Hand_DataBase extends SQLiteOpenHelper {

    public static final String DB_Name = "Hand.db";

    public Hand_DataBase(Context context) {
        super(context, DB_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table  Hand ( id INTEGER PRIMARY KEY AUTOINCREMENT , ImageName TEXT , Angle1 TEXT , Angle2 TEXT , Angle3 TEXT , Angle4 TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Hand");
        onCreate(db);
    }

    public boolean insert(Model_Hand hand) {

        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("ImageName", hand.getImageName());
        values.put("Angle1", hand.getAngle1());
        values.put("Angle2", hand.getAngle2());
        values.put("Angle3", hand.getAngle3());
        values.put("Angle4", hand.getAngle4());
        //                            table name                     values
        long result = DB.insert("Hand", null, values);
        if (result == -1)
            return false;
        else
            return true;
    }

    // Method Search By QRCode
    public boolean cheeckHand(String imageName) {

        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select * from Hand where ImageName like '%" + imageName + "%' order by ImageName ", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            return true;
        }
        return false;
    }

    // Method Search By QRCode
    public boolean cheeckHandID(int ID) {

        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select * from Hand where id like '%" + ID + "%' order by id ", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            return true;
        }
        return false;
    }

    public boolean DeleteHand(int id) {
        if (cheeckHandID(id)) {
            SQLiteDatabase DB = this.getWritableDatabase();
            long result = DB.delete("Hand", "id= ?", new String[]{String.valueOf(id)});
            if (result == -1)
                return false;
            else
                return true;
        }
        return false;
    }

}
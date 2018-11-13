package com.bignerdranch.android.criminalintent;

//this class is used to create the db
//need to use/import SQLiteOpenHelper (and other stuff)

//SQLite does most of the work of creating a db for you
//it will also be used in CrimeLab.java

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//fyi, the original import was the first line: the java page for the db
//by changing it to the second line, you skip right to the table when you refer to that page
//see the onCreate method below for the syntax; it knows you mean CrimeDbSchema.CrimeTable
import com.bignerdranch.android.criminalintent.database.CrimeDbSchema;
import com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeTable;

public class CrimeBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    //this runs if the db was not created before:
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + CrimeTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CrimeTable.Cols.UUID + ", " +
                CrimeTable.Cols.TITLE + ", " +
                CrimeTable.Cols.DATE + ", " +
                CrimeTable.Cols.SOLVED + ")" );
    }

    //the onCreate method is what creates the tables in the db.
    //they are merely named (their strings) in CrimeDbSchema. I think.
    //SQLite doesn't require specifying type of field in dbs at creation,
    //although it's a good idea.

    //if db already exists, this checks version and will upgrade if needed:
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //blank for now
    }
}

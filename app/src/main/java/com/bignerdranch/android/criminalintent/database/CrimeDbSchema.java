package com.bignerdranch.android.criminalintent.database;

//here is your database!
//there are different ways to make them for android apps, but this is a simple schema
//all code except two lines is created by user (you)

public class CrimeDbSchema {

    //this class is solely to define the constants needed in the db
    public static final class CrimeTable {
        //name of table:
        public static final String NAME = "crimes";

        //names of columns:
        //dunno why the former was a string but this is a class with strings
        //maybe becuase table is top level, columns are children?
        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
        }
    }
}

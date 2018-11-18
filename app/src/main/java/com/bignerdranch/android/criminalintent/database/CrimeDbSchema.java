package com.bignerdranch.android.criminalintent.database;

//here is your database!
//there are different ways to make them for android apps, but this is a simple schema
//all code except two lines is created by user (you)

public class CrimeDbSchema {
    public static final class CrimeTable {
        public static final String NAME = "crimes";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String SUSPECT =  "suspect";
        }
    }
}
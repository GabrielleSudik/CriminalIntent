package com.bignerdranch.android.criminalintent;

//this is the CrimeLab model

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {

    //singleton: an instance of a class that may be called only once.
    //this will have a private constructor and a get.

    private static CrimeLab sCrimeLab;
    //s prefix means static variable
    private List<Crime> mCrimes;

    public static CrimeLab get(Context context){
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }
    //see chapt 14 for more on Context object

    private CrimeLab(Context context){
        //this is the constructor
        //it's private so other classes can't create it
        mCrimes = new ArrayList<>();
        //this loop populates 100 generic crimes until the user creates some
        for (int i = 0; i < 100; i++){
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i%2 == 0);//every other crime is solved
            mCrimes.add(crime);
        }
    }

    public List<Crime> getCrimes(){
        return mCrimes;
    }
    //LIST is a java util interface to make an ordered list
    //it has methods to retreive, add and delete elements
    //ArrayList is a subset of List

    public Crime getCrime(UUID id){
        for (Crime crime : mCrimes){
            if (crime.getId().equals(id)){
                return crime;
            }
        }
        return null;
    }
}

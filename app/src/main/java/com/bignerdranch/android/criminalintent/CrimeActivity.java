package com.bignerdranch.android.criminalintent;

//this is a model

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CrimeActivity extends SingleFragmentActivity {

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        //this allows fragments to be used by this activity
        //and says what they are going to do (ie, add, remove, etc)
        FragmentManager fm = getSupportFragmentManager();

        //and this says which fragment(s):
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null){
            fragment = new CrimeFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }

        //fragment transactions: add, remove, attach, detach or replace
        // fragments in the fragment list. these are very basic and important!
    }
    */
    //That was old code, deleted because CrimeActivity now extends SFA,
    //which has its own onCreated(Bundle) code

    //this code also implements the extension of SFA.
    @Override
    protected Fragment createFragment(){
        return new CrimeFragment();
    }
}

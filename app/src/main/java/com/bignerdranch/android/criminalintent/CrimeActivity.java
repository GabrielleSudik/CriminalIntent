package com.bignerdranch.android.criminalintent;

//this is a model

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

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

    //here is chapter 10 stuff. creating a newIntent method
    //it tells CrimeFragment what crime to display

    public static final String EXTRA_CRIME_ID =
            "com.bignerdranch.android.criminalintent.crime_id";

    public static Intent newIntent(Context packageContext, UUID crimeId){
        //what this will do is create a new intent that passes a key-value pair
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    //this code also implements the extension of SFA.
    @Override
    protected Fragment createFragment(){
        //return new CrimeFragment();
        //that was coded out so we instead get CrimeActivity's extra and pass it
        //to the CrimeFragment.newInstance(UUID)
        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }
}

package com.bignerdranch.android.criminalintent;

//this is a controller
//we'll use it to create the RecyclerView
//note RecyclerView needs a LayoutManager to work
//I'm thinking of those boxes in the airport security.
//Boxes dictate what is inside and they get re-used.
//the machine dictates where they go and how.

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CrimeListFragment extends Fragment {

    //all this code hooks up the fragment_crime_list view to the fragment
    private RecyclerView mCrimeRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_crime_list,  container, false);
        mCrimeRecyclerView = (RecyclerView) view
                .findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

}

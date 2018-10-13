package com.bignerdranch.android.criminalintent;

//this is a controller
//we'll use it to create the RecyclerView
//note RecyclerView needs a LayoutManager to work
//I'm thinking of those boxes in the airport security.
//Boxes dictate what is inside and they get re-used.
//the machine dictates where they go and how.

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CrimeListFragment extends Fragment {

    //all this code hooks up the fragment_crime_list view to the fragment
    private RecyclerView mCrimeRecyclerView;
    //set the adapter:
    private CrimeAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_crime_list,  container, false);
        mCrimeRecyclerView = (RecyclerView) view
                .findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //call the updater when it's time to update the recycler:
        updateUI();

        return view;
    }

    //here's the updating method, used when it's time to update the recycler:
    private void updateUI(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);

        //later chapters will expand what updateUI does.
    }

    //this defines the view holder for the list of crimes
    //it's the class with a constructor then you pass the super
    private class CrimeHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

        //these variables are for the binding of other code to the view widget:
        //ie, they are part of the methods that will do the binding.
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Crime mCrime;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            //make the ViewHolder and a Listener
            //so users can click it to make something happen
            itemView.setOnClickListener(this);

            //the next lines will do the binding:
            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
        }

        //this method will bind each crime to the text views:
        //basically, each time CrimeHolder gets a Crime,
        //it will update the title and date text views
        //this method will be called each time RecyclerView requests a new bind
        //between CrimeHolder and a specific crime
        public void bind(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
        }

        //this method says what to do when a user clicks the View Holder
        //ie, when a user clicks an item on the list
        @Override
        public void onClick(View view){
            Toast.makeText(getActivity(), mCrime.getTitle() + " clicked!",
                    Toast.LENGTH_LONG).show();
            //fyi for now it's a toast, in chapt 10 we'll send user to the details instead
        }
    }

    //this is an adapter to make the view holder show the views:
    //recycler view doesn't know anything about the Crime object
    //but the adapter does
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
        private List<Crime> mCrimes;
        public CrimeAdapter(List<Crime> crimes){
            mCrimes = crimes;
        }

        //you used the Implement Methods feature of AS to add the bulk of the following code:
        //you made some adjustments to match textbook
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                return new CrimeHolder(layoutInflater, parent);
            }


        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            //every time the Recycler is called, this will call the method
            //that binds a particular crime to a CrimeHolder
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}

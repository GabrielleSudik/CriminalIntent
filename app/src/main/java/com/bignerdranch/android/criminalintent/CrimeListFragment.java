package com.bignerdranch.android.criminalintent;

//this is a controller
//we'll use it to create the RecyclerView
//note RecyclerView needs a LayoutManager to work
//I'm thinking of those boxes in the airport security.
//Boxes dictate what is inside and they get re-used.
//the machine dictates where they go and how.

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CrimeListFragment extends Fragment {

    //all this code hooks up the fragment_crime_list view to the fragment
    private RecyclerView mCrimeRecyclerView;
    //set the adapter:
    private CrimeAdapter mAdapter;
    //to keep status of subtitle/number of crimes as true/false:
    private boolean mSubtitleVisible;
    //to fix rotation issue with the subtitle:
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    //this method lets the FragmentManager know that CrimeListFragment
    //needs to receive menu callbacks (menu is created below)
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_crime_list,  container, false);
        mCrimeRecyclerView = (RecyclerView) view
                .findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //to fix problem with disappearing subtitle upon rotation:
        if (savedInstanceState != null){
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        //call the updater when it's time to update the recycler:
        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    //this method creates the menu bar:
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        //this re-creates the action item of subtitle (ie, number of crimes shown in menu):
        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        }
        else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    //this one says what to do when the user selects the Menu Item
    //ie, it allows for creation of a new crime.
    //note Menus can allow more than one thing; they are identified by IDs
    //later we added an item to show the number of crimes
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            //to create new crime:
            case R.id.new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent intent = CrimePagerActivity
                        .newIntent(getActivity(), crime.getId());
                startActivity(intent);
                return true;
                //to show count of crimes:
            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
                //default:
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //this will show the number of crimes in the menu bar via a subtitle:
    private void updateSubtitle(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getString(R.string.subtitle_format, crimeCount);

        //this will show or hide the subtitle's status upon rotation:
        if (!mSubtitleVisible){
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    //here's the updating method, used when it's time to update the recycler:
    private void updateUI(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        //the next if-else statement governs the subtitle/number of crimes
        //specifically, it will update the number correctly when adding a new crime
        if(mAdapter == null){
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }
        else{
            mAdapter.notifyDataSetChanged();
        }
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
        //this next one line is part of the code to control when the handcuff images shows
        //it will show only when a crime is marked solved.
        private ImageView mSolvedImageView;
        private Crime mCrime;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            //make the ViewHolder and a Listener
            //so users can click it to make something happen
            itemView.setOnClickListener(this);

            //the next lines will do the binding:
            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            //the next line relates to when the handcuffs show
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
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
            //this next part toggles the view of the handcuffs
            //they will show only when a crime is marked solved
            mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }

        //this method says what to do when a user clicks the View Holder
        //ie, when a user clicks an item on the list
        @Override
        public void onClick(View view){
            //Toast.makeText(getActivity(), mCrime.getTitle() + " clicked!",
                   // Toast.LENGTH_LONG).show();
            //toast is deleted to make way for creating new instances of CrimeActivity:

            //next part deleted and replaced with new intent of CrimePagerActivity
            //ie, we are changing from static to swipeable list
            //Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
            //startActivity(intent);
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivity(intent);
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

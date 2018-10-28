package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.UUID;

//to convert a class to a subclass of fragment, "extends Fragment"
public class CrimeFragment extends Fragment {

    //this field is part of the newInstance method for passing crime info to the fragment:
    private static final String ARG_CRIME_ID = "crime_id";

    //this constant is for the date picker
    private static final String DIALOG_DATE = "DialogDate";

    //these are more basic, from much earlier in the code creation:
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    //this method accepts the crime's UUID, creates an arguments bundle,
    //creates an instance of a fragment, then attaches the arguments to the fragment
    //it needs to be in that order because the bundle and fragment must be created
    //before the arguments can attach to the fragment (makes sense -- you can't attach to nothing!)
    public static CrimeFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mCrime = new Crime(); //coded out so we can fetch specific crimes instead:
        //UUID crimeId = (UUID) getActivity().getIntent()
           //     .getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);
        //that last line will be replaced with:
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        //what you did: instead of getting the intent directly from CrimeActivity
        //you now get fragment arguments, which create and attach their own discrete info
        //it takes more code, but you can be more specific this way
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
        //basically, that is calling the intent and the intent's extra -- the crime's ID
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        //that was the line included by the IDE. your re-write:
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        //here are your widgets. based on what you wrote in fragment_crime.xml:
        //crime title first:
        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        //this next line shows specific crime info after CrimeFragment fetches a Crime:
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //left blank for now
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //left blank for now
            }
        });

        //now date button (set to not allow presses):
        mDateButton = (Button) v.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getDate().toString());
        //mDateButton.setEnabled(false);
        //you coded out that last line to add the following
        //which will implement the ability to pick dates from the calendar
        mDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.show(manager, DIALOG_DATE);
            }
        });

        //checkbox for solved:
        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        //next line checks for solved status for whatever specific crime has been fetched:
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        return v;
    }



    //fyi, fragment lifecycle methods must be public because
    // they are called by the hosting activity (ie, something else)
}

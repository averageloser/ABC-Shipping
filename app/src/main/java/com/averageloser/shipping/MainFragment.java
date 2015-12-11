package com.averageloser.shipping;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by tom on 12/11/2015.
 *
 * Represents the UI for the input form.
 */
public class MainFragment extends android.support.v4.app.Fragment  {
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private TextView fullNameTextView;
    private Spinner nameSortSpinner;
    private Person person;
    private ShippingFragmentListener listener;

    public interface ShippingFragmentListener {
        void onPeopleListRequested();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ShippingFragmentListener) getActivity();
        } catch (ClassCastException e) {
            Log.e("ERROR", "Hosts must implement ShippingFragmentListener!");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        listener = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.shipping_fragment, container, false);

        nameSortSpinner = (Spinner) view.findViewById(R.id.name_sort_spinner);
        nameSortSpinner.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.name_sort_array)));

        firstNameEditText = (EditText) view.findViewById(R.id.first_name_edit_text);

        lastNameEditText = (EditText) view.findViewById(R.id.last_name_edit_text);

        fullNameTextView = (TextView) view.findViewById(R.id.full_name_text_view);

        Button nameButton = (Button) view.findViewById(R.id.name_button);
        nameButton.setOnClickListener(NameButtonClick);

        Button loadPeopleButton = (Button) view.findViewById(R.id.load_people_button);
        loadPeopleButton.setOnClickListener(LoadPeopleButtonClick);
        return view;
    }

    //Called when the name button is clicked.
    private View.OnClickListener NameButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String firstName = firstNameEditText.getText().toString();

            String lastName = lastNameEditText.getText().toString();

            //Make sure that both name fields have content.
            if (firstName.isEmpty()) {
                firstNameEditText.setError("You need to enter a first name!");
            } else if (lastName.isEmpty()) {
                lastNameEditText.setError("You need to enter a last name!");
            }

            if (!firstName.isEmpty() && !lastName.isEmpty()){
        /*It's easy just to copy the contents of the edit text fields to the text field, but I
          assume that the point of the exercise it to demonstrate object creation. */
                Person person = new Person(firstName,
                        lastName);

                fullNameTextView.setText(getOrderedName(person));
            }
        }
    };

    //called when the people list button is clicked.
    private View.OnClickListener LoadPeopleButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //this Fragment will ball back to its activity for tell it to replace this with the people list fragment.
            listener.onPeopleListRequested();
        }
    };

    /*Sets the order in which the person's name is displayed e.g. first name first or last name first,
    then returns it. */
    private String getOrderedName(Person person) {
        String fullName = "";

        //Get which option to which the name spinner is set for ordering.
        int selectedPosition = nameSortSpinner.getSelectedItemPosition();

        switch (selectedPosition) {
            case 0:
                fullName = person.toString();
                break;
            case 1:
                fullName = person.toStringLast();
        }

        return fullName;
    }
}

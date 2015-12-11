package com.averageloser.shipping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tom on 12/11/2015.
 * <p/>
 * This ListFragment parses a list of names from a JSON source and displays it in its ListView.
 */
public class PeopleListFragment extends android.support.v4.app.ListFragment {

    private List<Person> people = new ArrayList();
    private PeopleListAdapter peopleListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.people_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        peopleListAdapter = new PeopleListAdapter(getContext(), R.layout.people_list_row, new ArrayList<Person>());

        setListAdapter(peopleListAdapter);

        setRetainInstance(true);
    }

    //method to update the list adapter when json data download is complete.  This method is called by its activity.
    public void updateAdapter(List<Person> people) {
        peopleListAdapter.clear();
        peopleListAdapter.addAll(people);
        peopleListAdapter.notifyDataSetChanged();
    }
}

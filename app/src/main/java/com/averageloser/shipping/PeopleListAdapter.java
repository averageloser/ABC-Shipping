package com.averageloser.shipping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tj on 12/11/2015.
 *
 * This is the adapter for the PeopleListFragment.  It supplies a list of names to its ListView.
 */
public class PeopleListAdapter extends ArrayAdapter<Person> {

    private List<Person> people;

    public PeopleListAdapter(Context context, int resource, List<Person> objects) {
        super(context, resource, objects);

        people = objects;
    }

    @Override
    public int getCount() {
        return people.size();
    }

    @Override
    public Person getItem(int position) {
        return people.get(position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.people_list_row, parent, false);

            //create the holder.
            holder = new ViewHolder();
            holder.nameTextView = (TextView) convertView.findViewById(R.id.name_text_view);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Now bind the data to the views.

        holder.nameTextView.setText(getItem(position).toString());

        return convertView;
    }

    //The viewholder for the list.
    private static class ViewHolder {
        TextView nameTextView;
    }
}

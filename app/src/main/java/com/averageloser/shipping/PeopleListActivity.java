package com.averageloser.shipping;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.List;

/**
 * Created by tj on 12/11/2015.
 */
public class PeopleListActivity extends AppCompatActivity implements PeopleDownloaderFragment.PeopleDownloaderListener{
    private PeopleListFragment peopleListFragment;
    private PeopleDownloaderFragment peopleDownloaderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity); //shared with the shipping activity to show toolbar, etc.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        peopleListFragment = (PeopleListFragment) getSupportFragmentManager().findFragmentByTag("peopleListFragment");

        if (peopleListFragment == null) {
            peopleListFragment = new PeopleListFragment();

            getSupportFragmentManager().beginTransaction().add(R.id.app_content_container,
                    peopleListFragment, "peopleListFragment").commit();
        }

        peopleDownloaderFragment = (PeopleDownloaderFragment) getSupportFragmentManager().findFragmentByTag("peopleDownloaderFragment");

        if (peopleDownloaderFragment == null) {
            peopleDownloaderFragment = new PeopleDownloaderFragment();

            //Add the headless downloader fragment to this activity.
            getSupportFragmentManager().beginTransaction().add(R.id.app_content_container, peopleDownloaderFragment, "peopleDownloaderFragment").commit();
        }
    }

    @Override
    public void onPeopleDownloadComplete(List<Person> people) {
        //I have data, so I update the people list fragment.
        peopleListFragment.updateAdapter(people);
    }

    @Override
    public void onError() {
        new AlertDialog.Builder(this)
                .setMessage("Something bad happened.  Check error logs.")
                .show();
    }


}

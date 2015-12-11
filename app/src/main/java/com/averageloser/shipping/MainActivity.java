package com.averageloser.shipping;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

/*
    The main activity for the application.   Menu code has been removed as it is not used in this example.
 */
public class MainActivity extends AppCompatActivity implements MainFragment.ShippingFragmentListener{

    private MainFragment shippingFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shippingFragment = (MainFragment) getSupportFragmentManager().findFragmentByTag("shippingFragment");

        if (shippingFragment == null) {
            shippingFragment = new MainFragment();

            //attach the shipping activity Fragment to this activity and display it.
            getSupportFragmentManager().beginTransaction().add(R.id.app_content_container, shippingFragment,
                    "shippingFragment").commit();
        }
    }

    @Override
    public void onPeopleListRequested() {
        //Check for Internet connection.
        if (isDataNetworkActive()) {
            //The user has requested the list of people to be displayed.  Start it via intent.
            startActivity(new Intent(this, PeopleListActivity.class));
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("Uh-Oh.  It seems like your Internet connection is not working!  You can't " +
                            "download JSON data without it.  Check that and try again")
                    .show();
        }

    }


    /* Little utilty method to check the status of Internet connection.  It's not possible to download
    JSON data without it. */
    private boolean isDataNetworkActive() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo[] netInfo = cm.getAllNetworkInfo();

        Log.i("NetworkInfoSize", String.valueOf(netInfo.length));

        for (NetworkInfo ni : netInfo) {
            int type = ni.getType();

            if (type == ConnectivityManager.TYPE_WIFI || type == ConnectivityManager.TYPE_MOBILE) {
                if (ni.isConnected()) {
                    return true;
                }
            }
        }

        return false;
    }

}

package com.averageloser.shipping;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tj on 12/11/2015.
 * <p/>
 * A headless Fragment used for downloading and parsing json data from https://gist.github.com/zorn/6572637c1a1cbb89d4c9
 * If this Fragment is successful in its job, it will notify its host and pass it the data.
 * <p/>
 * The Loader framework is also an option.
 * <p/>
 * This Fragment uses Google's volley library for handling background downloading, as opposed to an AsyncTask and HttpUrlConnection.
 * Volley handles all of this internally, making the job much easier and quicker, so there is no good reason not to use it.
 * <p/>
 * If you are interested in seeing me download data with HttpUrlConnection, a simple example is available here in a Stock Quote downloader I wrote.
 * http://github.com/averageloser/TJFStockQuotes/blob/master/app/src/main/java/com/example/tj/tjfstockquotes/Model/StockQuoteModel.java
 */
public class PeopleDownloaderFragment extends android.support.v4.app.Fragment {

    public static final String URL = "https://gist.githubusercontent.com/zorn/6572637c1a1cbb89d4c9/raw/88c80043d4bf6d0feac11de9a575db4573a9b024/people.json";

    public interface PeopleDownloaderListener {
        void onPeopleDownloadComplete(List<Person> people);

        void onError();
    }

    private PeopleDownloaderListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (PeopleDownloaderListener) getActivity();
        } catch (ClassCastException e) {
            Log.e("ERROR", "Hosts must implement PeopleDownloadListener!");
        }

        setRetainInstance(true);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //The Fragment has been added and initialized.  This is not safe to run.
        download();
    }

    public void download() {
        getData();
    }

    /* Uses volley to asynchronously download data from the rest source and fills the list of items.
    Processing begins asa soon as the request is added to thw que. */
    private void getData() {
        Log.i("getData", "called");

        RequestQueue que = Volley.newRequestQueue(getContext());

        StringRequest request = new StringRequest(
                Request.Method.GET,
                URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i("onResponse", response);

                        /*
                        new AlertDialog.Builder(getContext())
                                .setMessage(response).show(); */
                        try {
                            //create the list from the data here and pass it to the listener.
                            listener.onPeopleDownloadComplete(createList(response));
                        } catch (JSONException e) {
                            Log.e("ERROR", "JSONException.");

                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    listener.onError();
                                }
                            });
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley error", error.getMessage());

                        //I am not sure if this happens on the UI thread, so I'll do this.
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                listener.onError();
                            }
                        });
                    }
                }
        );

        que.add(request);
    }

    private List<Person> createList(String data) throws JSONException {
        Log.i("data", data);

        List<Person> people = new ArrayList();

        JSONObject jsonObj = new JSONObject(data);

        //Grab the array of people.
        JSONArray peopleArray = jsonObj.optJSONArray("people");

        //Iterate over the array, create people and add them to the list.
        for (int i = 0; i < peopleArray.length(); i++) {
            JSONObject tempPerson = peopleArray.optJSONObject(i);

            Person person = new Person(tempPerson.optString("first_name"), tempPerson.optString("last_name"));

            people.add(person);
        }

        return people;
    }


}

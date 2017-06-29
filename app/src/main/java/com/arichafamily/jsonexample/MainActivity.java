package com.arichafamily.jsonexample;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Make call to AsyncTask
        new AsyncLogin().execute();
    }

    private class AsyncLogin extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected String doInBackground(String... params) {
            BufferedReader jsonReader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.trivia)));
            StringBuilder jsonBuilder = new StringBuilder();
            JSONTokener tokener;
            JSONArray jsonArray = null;

            try
            {
                for (String line; (line = jsonReader.readLine()) != null;)
                {
                    jsonBuilder.append(line).append("/n");
                }
                tokener = new JSONTokener(jsonBuilder.toString());
                jsonArray = new JSONArray(tokener);
            }
            catch (FileNotFoundException e)
            {
                Log.e("JsonFile", "File not found");
            }
            catch (IOException e)
            {
                Log.e("JsonFile", "IO Error");
            }
            catch (JSONException e)
            {
                Log.e("JsonFile", "Error while parsing json");
            }
            return jsonArray != null ? jsonArray.toString() : null;
        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread
            pdLoading.dismiss();
            List<TriviaCard> data = new ArrayList<>();

            try
            {
                JSONArray jArray = new JSONArray(result);

                // Extract data from json and store into ArrayList as class objects
                for(int i = 0; i < jArray.length(); i++)
                {
                    JSONObject json_data = jArray.getJSONObject(i);
                    TriviaCard triviaCard = new TriviaCard();
                    triviaCard.question = json_data.getString("question");
                    triviaCard.answerA = json_data.getString("A");
                    triviaCard.answerB = json_data.getString("B");
                    triviaCard.answerC = json_data.getString("C");
                    triviaCard.answerD = json_data.getString("D");
                    triviaCard.correctAnswer = json_data.getString("answer");
                    data.add(triviaCard);
                }

                // Setup and Handover data to recyclerview
                RecyclerView mRVTriviaCard = (RecyclerView) findViewById(R.id.triviaCardList);
                AdapterTriviaCard mAdapter = new AdapterTriviaCard(MainActivity.this, data);
                mRVTriviaCard.setAdapter(mAdapter);
                mRVTriviaCard.setLayoutManager(new LinearLayoutManager(MainActivity.this));

            }
            catch (JSONException e)
            {
                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
}

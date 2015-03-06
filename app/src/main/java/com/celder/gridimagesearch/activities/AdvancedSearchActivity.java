package com.celder.gridimagesearch.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import org.apache.commons.io.FileUtils;

import com.celder.gridimagesearch.BuildConfig;
import com.celder.gridimagesearch.R;
import com.celder.gridimagesearch.models.FilterState;
import com.celder.gridimagesearch.models.ImageResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Filter;

public class AdvancedSearchActivity extends ActionBarActivity {

    private Spinner imageSizeSpinner;
    private Spinner imageColorSpinner;
    private Spinner imageTypeSpinner;
    private EditText siteFilterQuery;
    // private Button saveButton;
    private ArrayList<String> imageFilters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);
        Log.d("DEBUG", "AdvancedSearchActivity.onCreate called");
        imageFilters = new ArrayList<String>();
        setupViews();
    }

    private void setupViews() {
        imageSizeSpinner = (Spinner) findViewById(R.id.imageSizeSpinner);
        imageColorSpinner = (Spinner) findViewById(R.id.imageColorSpinner);
        imageTypeSpinner = (Spinner) findViewById(R.id.imageTypeSpinner);
        siteFilterQuery = (EditText) findViewById(R.id.siteFilterQuery);

        // TODO: populate spinners with persistent values
        // ArrayList<String> filters = readImageFilters();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d("DEBUG", "AdvancedSearchActivity.onCreateOptionsMenu called");
        getMenuInflater().inflate(R.menu.menu_advanced_search, menu);
        return true;
    }

    //fired whenever button pressed
    public void onFilterSave(View v) {
        imageFilters.add(imageSizeSpinner.getSelectedItem().toString());
        imageFilters.add(imageColorSpinner.getSelectedItem().toString());
        imageFilters.add(imageTypeSpinner.getSelectedItem().toString());
        imageFilters.add(siteFilterQuery.getText().toString());

        Log.i("INFO", "filter values to save: " + imageFilters);

        // FilterState filterState = new FilterState(imageSize, imageColor, imageType, filterQuery);
        //save filterState in persistent storage
        writeItems();

        // navigate back to searchactivity
        // creating an intent
        Intent i = new Intent(AdvancedSearchActivity.this, SearchActivity.class);
        // launch the new activity
        startActivity(i);
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File filterFile = new File(filesDir, "imagefilters.txt");
        try {
            FileUtils.writeLines(filterFile, imageFilters);
            if (BuildConfig.DEBUG) {
                Log.d("DEBUG", "writing imageFilters " + imageFilters + " to file: " + filterFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> readImageFilters() {
        ArrayList<String> filterItems;
        File filesDir = getFilesDir();
        File filterFile = new File(filesDir, "imagefilters.txt");
        try {
            filterItems = new ArrayList<String>(FileUtils.readLines(filterFile));
        } catch (IOException e) {
            filterItems = new ArrayList<String>();
        }
        Log.i("INFO", "reading image filters: " + filterItems.toString());
        return filterItems;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d("DEBUG", "AdvancedSearchActivity.onOptionsItemSelected called");
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // navigate back to searchactivity
            // creating an intent
            Intent i = new Intent(AdvancedSearchActivity.this, SearchActivity.class);
            // launch the new activity
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

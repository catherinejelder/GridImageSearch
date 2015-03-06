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

import com.celder.gridimagesearch.R;
import com.celder.gridimagesearch.models.FilterState;
import com.celder.gridimagesearch.models.ImageResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Filter;

public class AdvancedSearchActivity extends ActionBarActivity {

    private Spinner imageSizeSpinner;
    private Spinner imageColorSpinner;
    private Spinner imageTypeSpinner;
    private EditText siteFilterQuery;
    // private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);
        Log.d("DEBUG", "AdvancedSearchActivity.onCreate called");
        setupViews();
    }

    private void setupViews() {
        imageSizeSpinner = (Spinner) findViewById(R.id.imageSizeSpinner);
        imageColorSpinner = (Spinner) findViewById(R.id.imageColorSpinner);
        imageTypeSpinner = (Spinner) findViewById(R.id.imageTypeSpinner);
        siteFilterQuery = (EditText) findViewById(R.id.siteFilterQuery);
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
        String imageSize = imageSizeSpinner.getSelectedItem().toString();
        String imageColor = imageColorSpinner.getSelectedItem().toString();
        String imageType = imageTypeSpinner.getSelectedItem().toString();
        String filterQuery = siteFilterQuery.getText().toString();

        Log.i("INFO", "filter values saved: imageSize = " + imageSize + ", imageColor = " + imageColor
                + ", imageType = " + imageType + ", filterQuery = " + filterQuery);

        FilterState filterState = new FilterState(imageSize, imageColor, imageType, filterQuery);
        // TODO: save filterState in persistent storage

        // navigate back to searchactivity
        // creating an intent
        Intent i = new Intent(AdvancedSearchActivity.this, SearchActivity.class);
        // launch the new activity
        startActivity(i);
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

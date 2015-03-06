package com.celder.gridimagesearch.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.celder.gridimagesearch.models.EndlessScrollListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.celder.gridimagesearch.adapters.ImageResultsAdapter;
import com.celder.gridimagesearch.R;
import com.celder.gridimagesearch.models.ImageResult;


public class SearchActivity extends ActionBarActivity {

    private EditText etQuery;
    private GridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        imageResults = new ArrayList<ImageResult>();
        // attaches data source to adapter
        aImageResults = new ImageResultsAdapter(this, imageResults);
        // link adapter to adapter view (gridview)
        gvResults.setAdapter(aImageResults);
    }

    private void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // launch image display activity
                // creating an intent
                Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                // get image result to display
                ImageResult result = imageResults.get(position);
                // pass image result into intent
                i.putExtra("url", result.fullUrl); /// need to be either serializable or parseable
                // i.putExtra("url", result); /// need to be either serializable or parseable
                // launch the new activity
                startActivity(i);
            }
        });
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Log.d("DEBUG", "on load more: page = " + page + ", totalItemsCount = " + totalItemsCount);
                customLoadMoreDataFromApi(totalItemsCount);
            }
        });
    }

    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
        String query = etQuery.getText().toString();
        // Toast.makeText(this, "Search for: " + query, Toast.LENGTH_SHORT).show();

        AsyncHttpClient client = new AsyncHttpClient();
        String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8" + "&start=" + offset;
        Log.d("DEBUG", "query triggered by scroll: " + searchUrl);

        client.get(searchUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // super.onSuccess(statusCode, headers, response);
                Log.d("DEBUG", response.toString());
                JSONArray imageResultsJson = null;
                try {
                    imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    // changes to adapter do change underlying data
                    imageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
                    aImageResults.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("INFO", imageResults.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    //fired whenever button pressed
    public void onImageSearch(View v) {
        String query = etQuery.getText().toString();
        // Toast.makeText(this, "Search for: " + query, Toast.LENGTH_SHORT).show();

        AsyncHttpClient client = new AsyncHttpClient();
        String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8";
        client.get(searchUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // super.onSuccess(statusCode, headers, response);
                Log.d("DEBUG", response.toString());
                JSONArray imageResultsJson = null;
                try {
                    imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    imageResults.clear(); // clear existing images from array (on new search)
                    // changes to adapter do change underlying data
                    imageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
                    aImageResults.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("INFO", imageResults.toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.i("INFO", "SearchActivity.onOptionsItemSelected called, with id num: " + id);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.i("INFO", "action_settings selected");

            // launch advanced search activity
            // creating an intent
            Intent i = new Intent(SearchActivity.this, AdvancedSearchActivity.class);
            // launch the new activity
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.example.devanshus.firstapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class CategoryListActivity extends AppCompatActivity {

    String[] stringArray;
    ArrayAdapter<String> productArrayAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Category");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> list, ParseException e) {

                if (e == null && list.size()>0) {
                    stringArray = new String[list.size()];

                    for (int i = 0; i < list.size(); i++) {
                        String category = list.get(i).getString("Type");
                        stringArray[i] = category;
                    }
                    productArrayAdapter = new ArrayAdapter<String>(CategoryListActivity.this, android.R.layout.simple_list_item_1, stringArray);
                    listView = (ListView) findViewById(R.id.categoryListView);
                    listView.setAdapter(productArrayAdapter);


                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(CategoryListActivity.this, ProductListActivity.class);
                            // intent.putExtra("category", (Object)list.get(position));//stringArray[position] );
                            intent.putExtra("category", list.get(position).getObjectId());
                            startActivity(intent);
                        }
                    });
                }


            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater mif= getMenuInflater();
        mif.inflate(R.menu.menu_main, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by defaul
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings){
            ParseUser.logOutInBackground();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);


            return true;}
        else if(item.getItemId() == R.id.action_add){
            Intent intent = new Intent(this, AddProducts.class);
            startActivity(intent);
            return true;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();

    }
}
package com.example.devanshus.firstapp;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import java.util.*;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity  {

    String[] stringArray;
    ArrayAdapter<String> productArrayAdapter;
    ListView productListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        ParseQuery innerQuery1 = new ParseQuery("Category");
        innerQuery1.whereEqualTo("objectId", getIntent().getStringExtra("category"));


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
        query.whereMatchesQuery("category", innerQuery1);
        query.

        findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> productList, ParseException e) {

                if (e == null && productList.size() > 0) {
                    stringArray = new String[productList.size()];

                    for (int i = 0; i < productList.size(); i++) {
                        String product = productList.get(i).getString("productName");
                        stringArray[i] = (product);
                    }
                    productArrayAdapter = new ArrayAdapter<String>(ProductListActivity.this, android.R.layout.simple_list_item_1, stringArray);
                    productListView = (ListView) findViewById(R.id.listViewId);
                    productListView.setAdapter(productArrayAdapter);

                 /*   ParseCloud.callFunctionInBackground("hello", new HashMap<String, String>(), new FunctionCallback<Object>() {

                                public void done(Object resp, ParseException e1) {
                                    if (e1 == null) {
                                        Toast.makeText(ProductListActivity.this, resp.toString(), Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(ProductListActivity.this, e1.toString(), Toast.LENGTH_LONG).show();
                                    }
                                }

                            }


                    );*/

                    productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                            ParseQuery innerQuery = new ParseQuery("Product");
                            innerQuery.whereEqualTo("objectId", productList.get(position).getObjectId());//whereEqualTo("objectId", getIntent().getStringExtra("product"));

                            ParseQuery<ParseObject> query = ParseQuery.getQuery("Product_Details");
                            query.whereMatchesQuery("product", innerQuery);
                            query.findInBackground(new FindCallback<ParseObject>() {
                                @Override
                                public void done(List<ParseObject> list, ParseException e) {
                                    if (e == null && list.size() > 0) {

                                            Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
                                            intent.putExtra("productName", list.get(0).get("Product_Name").toString());
                                            intent.putExtra("productBrand", list.get(0).get("Product_Brand").toString());
                                            intent.putExtra("manufacturer", list.get(0).get("Product_Manufacturer").toString());
                                            intent.putExtra("quantity",productList.get(position).get("availableQty").toString());
                                            intent.putExtra("price",productList.get(position).get("unitPrice").toString());
                                        intent.putExtra("productObjectId",productList.get(position).getObjectId());
                                        intent.putExtra("currency",(productList.get(position).getParseObject("currency")).getObjectId());
                                        startActivity(intent);



                                    } else {

                                        Toast.makeText(ProductListActivity.this, "Product details not available", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });


                        }
                    });


                } else {
                    stringArray = new String[1];
                    stringArray[0] = "No products found ";
                    productArrayAdapter = new ArrayAdapter<String>(ProductListActivity.this, android.R.layout.simple_list_item_1, stringArray);
                    productListView = (ListView) findViewById(R.id.listViewId);
                    productListView.setAdapter(productArrayAdapter);
                    //Toast.makeText(ProductListActivity.this, "Could not fetch products", Toast.LENGTH_LONG).show();
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
   /*   if (item.getItemId() == R.id.action_search) {
            onSearchRequested();
            return true;
        }else {*/
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



   
}

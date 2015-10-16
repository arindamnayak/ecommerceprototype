package com.example.devanshus.firstapp;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ListView listView ;
    String[] stringArray;
    ArrayAdapter<String> productArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listView = (ListView) findViewById(R.id.searchListView);
        handleIntent(getIntent());


    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //Toast.makeText(this, query, Toast.LENGTH_LONG).show();
             //use the query to search your data somehow
            ParseQuery parseQuery = new ParseQuery("Product");
           parseQuery.whereContains("productDesc",query).findInBackground(new FindCallback<ParseObject>() {
               @Override
               public void done(final List<ParseObject> productlist, ParseException e) {
                   if (e == null && productlist.size() > 0) {
                       stringArray = new String[productlist.size()];
                       for (int i = 0; i < productlist.size(); i++) {
                           String product = productlist.get(i).getString("productName");
                           stringArray[i] = (product);
                       }
                       productArrayAdapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, stringArray);

                       listView.setAdapter(productArrayAdapter);

                       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                           @Override
                           public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {

                               ParseQuery innerQuery = new ParseQuery("Product");
                               innerQuery.whereEqualTo("objectId", productlist.get(position).getObjectId());//whereEqualTo("objectId", getIntent().getStringExtra("product"));

                               ParseQuery<ParseObject> query = ParseQuery.getQuery("Product_Details");
                               query.whereMatchesQuery("product", innerQuery);
                               query.findInBackground(new FindCallback<ParseObject>() {
                                   @Override
                                   public void done(List<ParseObject> list, ParseException e) {
                                       if (e == null && list.size() > 0) {

                                           Intent intent = new Intent(SearchActivity.this, ProductDetailActivity.class);
                                           intent.putExtra("productName", list.get(0).get("Product_Name").toString());
                                           intent.putExtra("productBrand", list.get(0).get("Product_Brand").toString());
                                           intent.putExtra("manufacturer", list.get(0).get("Product_Manufacturer").toString());
                                           intent.putExtra("quantity",productlist.get(position).get("availableQty").toString());
                                           intent.putExtra("price",productlist.get(position).get("unitPrice").toString());
                                           intent.putExtra("productObjectId",productlist.get(position).getObjectId());
                                           intent.putExtra("currency",(productlist.get(position).getParseObject("currency")).getObjectId());
                                           startActivity(intent);


                                       } else {

                                           Toast.makeText(SearchActivity.this, "Product details not available", Toast.LENGTH_LONG).show();
                                       }
                                   }
                               });


                           }
                       });


                   } else {
                       stringArray = new String[1];
                       stringArray[0] = "No results found";
                       productArrayAdapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, stringArray);

                       listView.setAdapter(productArrayAdapter);

                   }

               }


           });








        }
    }


}

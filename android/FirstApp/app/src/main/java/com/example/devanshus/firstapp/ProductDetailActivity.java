package com.example.devanshus.firstapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {
TextView header,body,date;
    Button addToCart;
    TextView quantity;
    String productName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        header = (TextView) findViewById(R.id.tweetTitle);
        body = (TextView) findViewById(R.id.tweetBody);
        date = (TextView) findViewById(R.id.tweetDate);
        addToCart = (Button) findViewById(R.id.btn_addToCart);

        quantity = (TextView)findViewById(R.id.fld_quantity);

        if(getIntent().getStringExtra("quantity")!=null && Integer.parseInt(getIntent().getStringExtra("quantity").toString())>0) {

            quantity.setHint(Integer.parseInt(getIntent().getStringExtra("quantity").toString()) + " items available");
        }else{
            quantity.setHint("out of stock");
            addToCart.setEnabled(false);
        }
        productName=getIntent().getStringExtra("productName");
        header.setText(productName);
        body.setText(getIntent().getStringExtra("productBrand"));
        date.setText(getIntent().getStringExtra("manufacturer"));

       /* ParseQuery innerQuery = new ParseQuery("Product");
        innerQuery.whereEqualTo("objectId",getIntent().getStringExtra("product"));//whereEqualTo("objectId", getIntent().getStringExtra("product"));

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Product_Details");
        query.whereMatchesQuery("product", innerQuery);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null && list.size()>0) {

                    try {
                        productDetails = list.get(0);
                        header.setText(list.get(0).get("Product_Name").toString());
                        body.setText(list.get(0).get("Product_Brand").toString());
                        date.setText(list.get(0).get("Product_Manufacturer").toString());



                       *//* ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Product");
                        ParseObject product1=(ParseObject)productDetails.get("product");
                        Log.d("dfbhdghfghsfdghxfgnh", product1.getString("objectId"));
                        query1.whereEqualTo("objectId", product1.get("objectId").toString()).findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(final List<ParseObject> list1, ParseException e) {
                                if (e == null) {
                                    try {
                                        product = list1.get(0);
                                    } catch (Exception e11) {
                                        Toast.makeText(ProductDetailActivity.this, e11.toString(), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(ProductDetailActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });*//*
                    } catch (Exception e1) {
                        Toast.makeText(ProductDetailActivity.this, e1.toString(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    header.setText( "Product details not available");
                    //Toast.makeText(ProductDetailActivity.this, "Product details not available", Toast.LENGTH_LONG).show();
                }
            }
        });*/

        try{
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

try{
                final int qty = Integer.parseInt(quantity.getText().toString());

    if(qty> Integer.parseInt(getIntent().getStringExtra("quantity"))){
        Exception exception = new Exception();
     throw    exception;
    }
                ParseQuery innerQuery = new ParseQuery("Product_Details");
                innerQuery.whereEqualTo("Product_Name", productName).findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, ParseException e) {
                        if (e == null && list.size() > 0) {

                            ParseACL roleACL = new ParseACL();
                            roleACL.setPublicReadAccess(false);
                            roleACL.setPublicWriteAccess(false);
                            roleACL.setRoleReadAccess("Administrator", true);
                            roleACL.setRoleWriteAccess("Administrator", true);
                            roleACL.setRoleWriteAccess(ParseUser.getCurrentUser().getObjectId(), true);
                            roleACL.setRoleReadAccess(ParseUser.getCurrentUser().getObjectId(), true);
                            ParseObject order = new ParseObject("Order");
                            order.put("user", ParseUser.getCurrentUser());
                            order.put("product",ParseObject.createWithoutData("Product", getIntent().getStringExtra("productObjectId")));
                            order.put("ACL", roleACL);
                            order.put("TotalQty", qty);
                            order.put("SubTotal", qty * Integer.parseInt(getIntent().getStringExtra("price")));
                            order.put("TotalAmount", Integer.parseInt(quantity.getText().toString()) * Integer.parseInt(getIntent().getStringExtra("price")));
                            order.put("TotalDiscountAmt", 0);
                            order.put("TotalTaxAmt", 0);
                            order.put("OrderNo", new Utility().productIdGenerator());
                            order.put("Status", "Progress");
                            //Log.d("product object id", list.get(0).get("currency").toString());
                           // order.put("currency",list.get(0).getParseObject("currency") );
                            order.put("currency", ParseObject.createWithoutData("Currency",getIntent().getStringExtra("currency")));
                                    order.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e1) {
                                            if (e1 == null) {
                                                Toast.makeText(ProductDetailActivity.this, "Added To Cart", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(ProductDetailActivity.this, e1.toString(), Toast.LENGTH_LONG).show();
                                                Log.d("", e1.toString());
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(ProductDetailActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }


                });
            }
                catch(Exception intParse){
                    Toast.makeText(ProductDetailActivity.this, "Please try again", Toast.LENGTH_LONG).show();
                }

             }




        });} catch(Exception e){
            Toast.makeText(ProductDetailActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater mif= getMenuInflater();
        mif.inflate(R.menu.menu_main, menu);
       // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by defaul
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_settings){
            ParseUser.logOutInBackground();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        else if(item.getItemId() == R.id.action_add){
            Intent intent = new Intent(this, AddProducts.class);
            startActivity(intent);
            return true;
        }
        return true;
    }

}

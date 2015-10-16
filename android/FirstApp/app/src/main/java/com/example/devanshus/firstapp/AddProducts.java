package com.example.devanshus.firstapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

public class AddProducts extends AppCompatActivity {

    EditText productName,brand,manufacturer;
    AutoCompleteTextView product;
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);


        productName = (EditText) findViewById(R.id.fld_productName);
        brand = (EditText) findViewById(R.id.fld_brand);
        manufacturer = (EditText) findViewById(R.id.fld_manufacturer);
        product = (AutoCompleteTextView) findViewById(R.id.fld_product);
        add = (Button) findViewById(R.id.btn_add);

        ParseQuery query = new ParseQuery("Product");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> list, ParseException e) {

                if (e == null && list.size()>0) {
                    String[] products = new String[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        products[i] = list.get(i).getString("productName");

                    }
                        //Create Array Adapter
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddProducts.this, android.R.layout.select_dialog_singlechoice, products);
                        //Find TextView control

                        //Set the number of characters the user must type before the drop down list is shown
                        product.setThreshold(1);
                        //Set the adapter
                        product.setAdapter(adapter);


                        add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ParseObject prod = new ParseObject("Product");
                                for (int i = 0; i < list.size(); i++) {
                                    if (list.get(i).getString("productName").equals(product.getText().toString())) {
                                        prod = list.get(i);
                                        break;
                                    }
                                }

                                ParseObject productDetails = new ParseObject("Product_Details");
                                productDetails.put("Product_Brand", brand.getText().toString());
                                productDetails.put("Product_Manufacturer", manufacturer.getText().toString());
                                productDetails.put("Product_Name", productName.getText().toString());
                                productDetails.put("product", prod);
                                productDetails.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            Toast.makeText(AddProducts.this, "Product Details added", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(AddProducts.this, e.toString(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        });

                    } else{
                    Toast.makeText(AddProducts.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }


        });

    }
}

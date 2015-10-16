package com.example.devanshus.firstapp;

import com.parse.ParseUser;

/**
 * Created by DevanshuS on 15-10-2015.
 */
public class Utility {

    public  String productIdGenerator(){

        return (ParseUser.getCurrentUser().toString()+""+System.currentTimeMillis()).substring(20);

    }
}

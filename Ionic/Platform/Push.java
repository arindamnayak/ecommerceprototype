package com.ionicframework.starter;

import android.os.*;
import android.app.Application;
import org.apache.cordova.*;
import com.parse.*;

public class Push extends Application
{
@Override
public void onCreate() {
   Parse.initialize(this, "qG2zJIcWHHBXSHoy8XJV0TLRLpTkdrbg1rHtOUFX", "VG5mKd6Kd4EC9ZqXtSPg2Ts0I7Dty2mrHixpjNVv");
        ParseInstallation.getCurrentInstallation().saveInBackground();
}
}

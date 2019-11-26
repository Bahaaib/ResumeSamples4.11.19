package com.resumesamples.resumesamples;

import android.content.Context;
import android.content.SharedPreferences;

public class Storage {

    private static final String STORAGE_NAME = "com.itomych.keen.STORAGE_NAME";
    private static final String IS_FIRST_LAUNCH = "com.itomych.keen.IS_FIRST_LAUNCH";

    private SharedPreferences sharedPreferences;

    private Storage() {
        sharedPreferences = MyApp.getAppContext().getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
    }

    public static Storage getInstance() {
        return new Storage();
    }

    public void setFirstAppStart() {
        setFirstAppStart(false);
    }

    public void setFirstAppStart(Boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_FIRST_LAUNCH, value);
        editor.apply();
    }

    public Boolean isFirstAppStart() {
        return sharedPreferences.getBoolean(IS_FIRST_LAUNCH, true);
    }

}
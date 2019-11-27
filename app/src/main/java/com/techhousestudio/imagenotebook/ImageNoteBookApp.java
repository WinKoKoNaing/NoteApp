package com.techhousestudio.imagenotebook;

import android.app.Application;

import timber.log.Timber;

public class ImageNoteBookApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}


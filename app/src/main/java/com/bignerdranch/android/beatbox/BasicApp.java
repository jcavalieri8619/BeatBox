package com.bignerdranch.android.beatbox;

import android.app.Application;

import com.bignerdranch.android.beatbox.data.BeatBox;

public class BasicApp extends Application {

    public BeatBox getBeatBox() {
        return Injection.provideBeatBox(this);
    }

}

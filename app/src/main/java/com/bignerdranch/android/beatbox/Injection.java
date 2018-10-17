package com.bignerdranch.android.beatbox;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import com.bignerdranch.android.beatbox.data.BeatBox;
import com.bignerdranch.android.beatbox.viewmodel.Factory;
import com.bignerdranch.android.beatbox.viewmodel.SoundViewModel;

public class Injection {

    private static BeatBox sBeatBox = null;

    public static BeatBox provideBeatBox(Context ctx) {
        synchronized (Injection.class) {
            if (sBeatBox == null) {
                sBeatBox = new BeatBox(ctx);
            }
        }
        return sBeatBox;
    }

    public static Factory provideViewModelFactory(Context ctx) {
        return new Factory(((Activity) ctx).getApplication());
    }



}

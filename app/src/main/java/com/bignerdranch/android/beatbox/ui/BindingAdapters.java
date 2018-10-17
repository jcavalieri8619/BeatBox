package com.bignerdranch.android.beatbox.ui;


import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.util.Log;
import android.widget.SeekBar;

public class BindingAdapters {
    private static final String TAG = "BindingAdapters";

    @BindingAdapter("seekValue")
    public static void setSeekProgressValue(SeekBar view, int progress) {
        Log.d(TAG, "setSeekProgressValue: JPC  value: "+ progress);

        view.setProgress(progress);

    }

    @InverseBindingAdapter(attribute = "seekValue")
    public static int getSeekProgressValue(SeekBar view) {
        Log.d(TAG, "getSeekProgressValue: JPC getting progress");

        return view.getProgress();
    }

}

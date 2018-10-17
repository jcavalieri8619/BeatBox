package com.bignerdranch.android.beatbox.ui;

import android.support.v4.app.Fragment;

public class BeatBoxActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        BeatBoxFragment beatBoxFragment = BeatBoxFragment.newInstance("", "");
        return beatBoxFragment;
    }
}

package com.bignerdranch.android.beatbox;

import android.arch.lifecycle.ViewModel;

public class SoundViewModel extends ViewModel {

    private Sound mSound;
    private BeatBox mBeatBox;

    public SoundViewModel(final BeatBox beatBox) {
        mBeatBox = beatBox;
    }

    public Sound getSound() {
        return mSound;
    }

    public void setSound(final Sound sound) {
        mSound = sound;
    }

   
}

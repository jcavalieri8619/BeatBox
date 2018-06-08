package com.bignerdranch.android.beatbox;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BeatBox {
    private static final String TAG = "BeatBox";
    private static final String SOUNDS_FOLDER = "sample_sounds";
    private final AssetManager mAssetManager;
    private final List<Sound> mSounds = new ArrayList<>();

    public BeatBox(Context ctx) {
        mAssetManager = ctx.getAssets();

        loadSounds();
    }

    private void loadSounds() {
        String[] soundNames;

        try{
            soundNames = mAssetManager.list(SOUNDS_FOLDER);

            Log.i(TAG, "loadSounds: Found " + soundNames.length + " sounds");
        } catch (IOException e) {
            Log.e(TAG, "loadSounds: could not load sounds", e);
            return;
        }

        for (String filename :
                soundNames) {

            String assetPath = SOUNDS_FOLDER + "/" + filename;

            Sound sound = new Sound(assetPath);
            mSounds.add(sound);

        }


    }

    public List<Sound> getSounds() {
        return mSounds;
    }
}

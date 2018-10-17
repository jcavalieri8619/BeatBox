package com.bignerdranch.android.beatbox.data;

import com.bignerdranch.android.beatbox.model.Sound;

public class SoundData implements Sound{
    private String mAssetPath;
    private String mName;
    private Integer mSoundID;

    public SoundData(final String assetPath) {
        mAssetPath = assetPath;

        String[] components = assetPath.split("/");
        String filename = components[components.length - 1];
        mName = filename.replace(".wav", "");


    }

    public String getAssetPath() {
        return mAssetPath;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public Integer getID() {
        return mSoundID;
    }

    @Override
    public void setID(int ID) {
        mSoundID = ID;
    }
}

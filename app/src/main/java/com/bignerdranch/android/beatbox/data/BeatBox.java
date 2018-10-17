package com.bignerdranch.android.beatbox.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.util.Log;

import com.bignerdranch.android.beatbox.model.Sound;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class BeatBox {
    private static final String TAG = "BeatBox";
    private static final int MAX_SOUNDS = 5;
    private static final String SOUNDS_FOLDER = "sample_sounds";
    private final AssetManager mAssetManager;
    private final MutableLiveData<List<SoundData>> mSoundsList = new MutableLiveData<>();

    private SoundPool mSoundPool;


    public BeatBox(Context ctx) {
        mAssetManager = ctx.getAssets();
        SoundPool.Builder builder = new SoundPool.Builder();

        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_MUSIC);

        builder.setMaxStreams(MAX_SOUNDS).setAudioAttributes(attrBuilder.build());

        mSoundPool = builder.build();

        final AsyncTask<Void, Void, List<SoundData>> asyncLoadSounds = new AsyncTask<Void, Void, List<SoundData>>() {
            @Override
            protected List<SoundData> doInBackground(final Void... voids) {
                return loadSoundsList();
            }

            @Override
            protected void onPostExecute(final List<SoundData> soundData) {
                mSoundsList.setValue(soundData);

            }
        };

        asyncLoadSounds.execute();
    }

    private List<SoundData> loadSoundsList() {
        String[] soundNames;

        try{
            soundNames = mAssetManager.list(SOUNDS_FOLDER);

            Log.i(TAG, "loadSoundsList: Found " + soundNames.length + " sounds");
        } catch (IOException e) {
            Log.e(TAG, "loadSoundsList: could not load sounds", e);
            return null;
        }

        List<SoundData> sounds = new ArrayList<>();
        for (String filename :
                soundNames) {

            String assetPath = SOUNDS_FOLDER + "/" + filename;

            SoundData sound = new SoundData(assetPath);
            try {
                loadSound(sound);
            } catch (IOException e) {
                Log.e(TAG, "loadSoundsList: failed to load .wav into soundData ",e);
            }
            sounds.add(sound);

        }

        return sounds;



    }

    public void releaseSoundPool() {
        mSoundPool.release();

    }

    public void playSound(Sound soundData, Float playrate) {

        if (playrate == null) {
            playrate=1.0f;
        }

        Integer soundID = soundData.getID();
        if (soundID == null) {
            return;
        }

        mSoundPool.play(soundID, 1.0f, 1.0f, 1, 0, playrate);

    }

    private void loadSound(SoundData soundData) throws IOException {
        AssetFileDescriptor aFD = mAssetManager.openFd(soundData.getAssetPath());
        int soundID = mSoundPool.load(aFD, 1);
        soundData.setID(soundID);

    }

    public LiveData<List<SoundData>> getSoundsList() {
        return mSoundsList;
    }


}

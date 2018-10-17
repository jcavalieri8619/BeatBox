package com.bignerdranch.android.beatbox.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;
import android.util.Log;

import com.bignerdranch.android.beatbox.BR;
import com.bignerdranch.android.beatbox.BasicApp;
import com.bignerdranch.android.beatbox.data.SoundData;
import com.bignerdranch.android.beatbox.model.Sound;

import java.util.List;

public class SoundViewModel extends AndroidViewModel implements Observable {

    private static final String TAG = "SoundViewModel";

    private PropertyChangeRegistry mCallbacks = new PropertyChangeRegistry();


    private int mSeekValue = 1;



    private LiveData<List<SoundData>> mSoundList;

    public SoundViewModel(Application app) {
        super(app);

        mSoundList = ((BasicApp) app).getBeatBox().getSoundsList();
    }

    public LiveData<List<SoundData>> getSoundList() {
        return mSoundList;

    }


    @Bindable
    public int getSeekValue() {
        Log.d(TAG, "getSeekValue: JPC returning : " + mSeekValue);
        return mSeekValue;
    }

    public void setSeekValue(int progress) {
        Log.d(TAG, "setSeekValue: JPC value: "+ progress);
        if (progress != mSeekValue) {

            mSeekValue = progress;

            notifyPropertyChanged(BR.seekValue);

        }

    }

    /**
     * Adds a callback to listen for changes to the Observable.
     *
     * @param callback The callback to start listening.
     */
    @Override
    public void addOnPropertyChangedCallback(final OnPropertyChangedCallback callback) {
        mCallbacks.add(callback);

    }

    /**
     * Removes a callback from those listening for changes.
     *
     * @param callback The callback that should stop listening.
     */
    @Override
    public void removeOnPropertyChangedCallback(final OnPropertyChangedCallback callback) {

        mCallbacks.remove(callback);
    }

    /**
     * Notifies observers that all properties of this instance have changed.
     */
    void notifyChange() {
        mCallbacks.notifyCallbacks(this, 0, null);
    }

    /**
     * Notifies observers that a specific property has changed. The getter for the
     * property that changes should be marked with the @Bindable annotation to
     * generate a field in the BR class to be used as the fieldId parameter.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    void notifyPropertyChanged(int fieldId) {
        mCallbacks.notifyCallbacks(this, fieldId, null);
    }
}

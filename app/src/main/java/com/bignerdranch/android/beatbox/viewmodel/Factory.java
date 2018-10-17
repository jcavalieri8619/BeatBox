package com.bignerdranch.android.beatbox.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

import com.bignerdranch.android.beatbox.BasicApp;
import com.bignerdranch.android.beatbox.data.BeatBox;

public class Factory extends ViewModelProvider.AndroidViewModelFactory {


//    BasicApp app;

    /**
     * Creates a {@code AndroidViewModelFactory}
     *
     * @param application an application to pass in {link AndroidViewModel}
     */
    public Factory(@NonNull final Application application) {
        super(application);
    }

//    /**
//     * Creates a new instance of the given {@code Class}.
//     * <p>
//     *
//     * @param modelClass a {@code Class} whose instance is requested
//     * @return a newly created ViewModel
//     */
//    @NonNull
//    @Override
//    public <T extends ViewModel> T create(@NonNull final Class<T> modelClass) {
//        if (modelClass.isAssignableFrom(SoundViewModel.class)) {
//
//            return (T) new SoundViewModel(app);
//
//        }
//        throw new ClassCastException("invalid argument to viewModel factory");
//
//    }
}

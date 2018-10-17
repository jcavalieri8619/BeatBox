package com.bignerdranch.android.beatbox.ui;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.test.mock.MockApplication;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.bignerdranch.android.beatbox.BasicApp;
import com.bignerdranch.android.beatbox.Injection;
import com.bignerdranch.android.beatbox.R;
import com.bignerdranch.android.beatbox.model.Sound;
import com.bignerdranch.android.beatbox.viewmodel.SoundViewModel;
import com.bignerdranch.android.beatbox.databinding.FragmentBeatBoxBinding;
import com.bignerdranch.android.beatbox.databinding.ListItemSoundBinding;
import com.bignerdranch.android.beatbox.data.SoundData;

import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BeatBoxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeatBoxFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "BeatBoxFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SoundAdapter mAdapter;
    private SoundViewModel mViewModel;
    private final Observer<List<SoundData>> mSoundListObserver=new Observer<List<SoundData>>() {
        @Override
        public void onChanged(@Nullable final List<SoundData> soundDataItems) {
            mAdapter.submitItems(soundDataItems);

        }
    };
    private FragmentBeatBoxBinding mFragmentBinding;


    public BeatBoxFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BeatBoxFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BeatBoxFragment newInstance(String param1, String param2) {
        BeatBoxFragment fragment = new BeatBoxFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //TODO this may not belong here since this fragment will be destroyed on rotation
        // but the singleton BeatBox lives in Application and it will not; so after roation,
        //there will be no way to retrieve the soundPool again; perhaps but this in
        // application ondestroy
//        ((BasicApp) getActivity().getApplication()).getBeatBox().releaseSoundPool();

    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // get viewModel in onActivityCreated because this is only called when a
        // fragment is created; not restarted. so getting it here instead of oncreateView
        // prevents wasting time by getting a viewModel that we already have a valid reference to

        final ViewModelProvider.Factory factory = Injection.provideViewModelFactory(getActivity());
        mViewModel = ViewModelProviders.of(this,factory).get(SoundViewModel.class);


        subscribeToModel();





    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // either unobserve here or do a re-observe in subscribeToModel by unobserving then observing
//        mViewModel.getSoundList().removeObserver(mSoundListObserver);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_beat_box, container, false);


        mFragmentBinding.setViewModel(mViewModel);


        setupCallbacks();




        setupRecyclerView();



        return mFragmentBinding.getRoot();



    }

    private void setupRecyclerView() {
        mAdapter = new SoundAdapter();

        mFragmentBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));


        mFragmentBinding.recyclerView.setAdapter(mAdapter);
    }

    private void setupCallbacks() {
        mFragmentBinding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
                Log.d(TAG, "onProgressChanged: JPC new value " + progress);
                mViewModel.setSeekValue(progress);

            }

            @Override
            public void onStartTrackingTouch(final SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {

            }
        });
    }

    private void subscribeToModel() {

        // a RE-observe
        mViewModel.getSoundList().removeObserver(mSoundListObserver);
        mViewModel.getSoundList().observe(this, mSoundListObserver);
    }

    private SoundClickCallback mSoundClickCallback = new SoundClickCallback() {
        @Override
        public void onClick(final Sound sound) {


            ((BasicApp) getActivity().getApplication()).getBeatBox().playSound(sound, (float) mViewModel.getSeekValue());

        }
    };

    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder> {

        private List<? extends Sound> mSounds;

        public SoundAdapter() {

        }

        public void submitItems(List<? extends Sound> items) {
            mSounds = items;
            notifyItemRangeChanged(0, items.size());

        }

        /**
         * Called when RecyclerView needs a new {link ViewHolder} of the given type to represent
         * an item.
         * <p>
         * This new ViewHolder should be constructed with a new View that can represent the items
         * of the given type. You can either create a new View manually or inflate it from an XML
         * layout file.
         * <p>
         * The new ViewHolder will be used to display items of the adapter using
         * {link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
         * different items in the data set, it is a good idea to cache references to sub views of
         * the View to avoid unnecessary {@link View#findViewById(int)} calls.
         *
         * @param parent   The ViewGroup into which the new View will be added after it is bound to
         *                 an adapter position.
         * @param viewType The view type of the new View.
         * @return A new ViewHolder that holds a View of the given view type.
         * @see #getItemViewType(int)
         * see #onBindViewHolder(ViewHolder, int)
         */
        @NonNull
        @Override
        public SoundHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {


            ListItemSoundBinding mListItemSoundBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.list_item_sound, parent, false);

            return new SoundHolder(mListItemSoundBinding);
        }




        /**
         * Called by RecyclerView to display the data at the specified position. This method should
         * update the contents of the {link ViewHolder#itemView} to reflect the item at the given
         * position.
         * <p>
         * Note that unlike {link ListView}, RecyclerView will not call this method
         * again if the position of the item changes in the data set unless the item itself is
         * invalidated or the new position cannot be determined. For this reason, you should only
         * use the <code>position</code> parameter while acquiring the related data item inside
         * this method and should not keep a copy of it. If you need the position of an item later
         * on (e.g. in a click listener), use {link ViewHolder#getAdapterPosition()} which will
         * have the updated adapter position.
         * <p>
         * Override {link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
         * handle efficient partial bind.
         *
         * @param holder   The ViewHolder which should be updated to represent the contents of the
         *                 item at the given position in the data set.
         * @param position The position of the item within the adapter's data set.
         */
        @Override
        public void onBindViewHolder(@NonNull final SoundHolder holder, final int position) {
            holder.bind(mSounds.get(position));
            holder.mBinding.executePendingBindings();

        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            return mSounds==null? 0:mSounds.size();
        }

    }


    private class SoundHolder extends RecyclerView.ViewHolder {

        final ListItemSoundBinding mBinding;

        public SoundHolder(final ListItemSoundBinding binding) {
            super(binding.getRoot());

            mBinding = binding;


        }


        public void bind(Sound sound) {


            mBinding.setSound(sound);
            mBinding.setOnSoundClick(mSoundClickCallback);


        }


    }

}


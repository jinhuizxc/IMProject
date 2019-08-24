package com.example.improject.fragments.main;


import android.app.Fragment;

import com.example.improject.common.app.BaseFragment;
import com.example.improject.widget.GalleryView;
import com.example.improject.R;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActiveFragment extends BaseFragment {

    @BindView(R.id.galleryView)
    GalleryView gallery;


    public ActiveFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_active;
    }

    @Override
    protected void initData() {
        super.initData();

        gallery.setup(getLoaderManager(), new GalleryView.SelectedChangeListener() {
            @Override
            public void onSelectedCountChanged(int count) {

            }
        });

    }
}

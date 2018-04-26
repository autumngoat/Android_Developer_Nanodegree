package com.example.full_dream.popularmoviesstage1;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final int NUMBER_OF_COLUMNS = 2;
    private static final int MOVIE_SEARCH_LOADER = 100;

    // Following the tutorial code for initial setup of RecyclerView:
    // https://developer.android.com/guide/topics/ui/layout/recyclerview.html#java
    @BindView(R.id.recyclerview) private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        // This setting is used to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView, which
        // should be true of the movie posters based on the sample UI of Stage 2
        mRecyclerView.setHasFixedSize(true);

        // Use a Grid Layout Manager as per the rubric
        mLayoutManager = new GridLayoutManager(this, NUMBER_OF_COLUMNS);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //TODO Specify an adapter
        //mAdapter = new mAdapter(dataSet);
        mRecyclerView.setAdapter(mAdapter);

        // Initialize the loader with MOVIE_SEARCH_LOADER as the ID, null for the bundle, and this
        // for the context
        // Remember to use 'android.support.v4.app.LoaderManager' not 'android.app.LoaderManager'
        // AndroidStudio auto-imports the latter and that class was deprecated in API level P
        getLoaderManager().initLoader(MOVIE_SEARCH_LOADER, null, this);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<String>(this) {
            @Nullable
            @Override
            public String loadInBackground() {
                return null;
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        // Not required as per the rubric (I think)
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        // Empty on purpose
    }
}

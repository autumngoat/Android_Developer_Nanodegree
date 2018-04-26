package com.example.full_dream.popularmoviesstage1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    private static final int NUMBER_OF_COLUMNS = 2;

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
    }
}

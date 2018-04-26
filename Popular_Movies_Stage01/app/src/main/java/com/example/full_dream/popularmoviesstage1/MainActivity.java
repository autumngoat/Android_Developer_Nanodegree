package com.example.full_dream.popularmoviesstage1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.full_dream.popularmoviesstage1.model.Movie;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    private static final int NUMBER_OF_COLUMNS = 2;

    @BindView(R.id.recyclerview) private RecyclerView mRecyclerView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayoutManager layoutManager = new GridLayoutManager(this, NUMBER_OF_COLUMNS);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);
    }
}

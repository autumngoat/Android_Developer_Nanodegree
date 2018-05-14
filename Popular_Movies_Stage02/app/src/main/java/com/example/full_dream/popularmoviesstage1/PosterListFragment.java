package com.example.full_dream.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.full_dream.popularmoviesstage1.model.Movie;
import com.example.full_dream.popularmoviesstage1.model.MovieResponse;
import com.example.full_dream.popularmoviesstage1.utils.RetrofitClient;
import com.example.full_dream.popularmoviesstage1.utils.TheMovieDBService;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PosterListFragment extends Fragment implements PosterAdapter.PosterAdapterOnClickHandler{

    private boolean mToggleSearchOption = true;
    private static final String MOST_POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";
    private static final String API_KEY = "GetYourOwnApi";
    private static final int NUMBER_OF_COLUMNS = 3;
    private GridLayoutManager mLayoutManager;
    private PosterAdapter mPosterAdapter;
    @BindString(R.string.title)
    String title;
    @BindString(R.string.release_date)
    String releaseDate;
    @BindString(R.string.poster_path)
    String posterPath;
    @BindString(R.string.vote_average)
    String voteAvg;
    @BindString(R.string.plot_synopsis)
    String plotSynopsis;
    @BindView(R.id.rv_poster_list)
    RecyclerView mRecyclerView;

    /**
     * Mandatory empty constructor
     */
    public  PosterListFragment(){}

    /**
     * Setup the data component of the Fragment.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the dataset.  The data comes either from a local ContentProvider or
        // remote server.

    }

    /**
     * Setup the UI component of the Fragment.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate fragment layout
        View rootView = inflater.inflate(R.layout.fragment_poster_list, container, false);
        // Grab the view from the fragment layout
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_poster_list);
        // Use a Grid Layout Manager as per the rubric
        mLayoutManager = new GridLayoutManager(getActivity(), NUMBER_OF_COLUMNS);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mPosterAdapter = new PosterAdapter(this);
        // Set the view to the empty PosterAdapter
        mRecyclerView.setAdapter(mPosterAdapter);

        // Checked out https://developer.android.com/training/basics/network-ops/managing
        // for how to check for network status check
//        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//        if(netInfo != null && netInfo.isConnected()){
//            callRetrofit();
//        } else {
//            Toast.makeText(this, netConnectMsg, Toast.LENGTH_LONG).show();
//        }

        // Fill the PosterAdapter with the initial data from the network call
        callRetrofit();

        // Return the fragment view
        return rootView;
    }

    /**
     * Handle RecyclerView item clicks to launch DetailActivity.
     */
    @Override
    public void onClick(Movie movie) {
        Context context = getActivity();
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        // I didn't know you could name the EXTRA's!!!
        // No need for enumeration or remembering the order of the EXTRAs for extraction like before
        intentToStartDetailActivity.putExtra(title, movie.getTitle());
        intentToStartDetailActivity.putExtra(releaseDate, movie.getReleaseDate());
        intentToStartDetailActivity.putExtra(posterPath, movie.getPosterPath());
        intentToStartDetailActivity.putExtra(voteAvg, Double.toString(movie.getVoteAverage()));
        intentToStartDetailActivity.putExtra(plotSynopsis, movie.getOverview());
        startActivity(intentToStartDetailActivity);
    }

    /**
     * Helper function to be able to call Retrofit whenever data needs to be retrieved.
     */
    public void callRetrofit(){
        // Instantiate the Retrofit (type safe HTTP) client
        RetrofitClient client = new RetrofitClient();

        // Pass service interface to create() to generate an implementation of the API endpoint
        TheMovieDBService service = client.getClient().create(TheMovieDBService.class);

        // Call represents the HTTP request while the generic parameter, in this case
        // MovieResponse, represents the HTTP response body type which will be converted
        // by one of the Converter.Factory instances (Moshi) to JSON to POJO(s).
        Call<MovieResponse> call;

        // Popular or Top Rated?
        if(mToggleSearchOption){
            call = service.getPopularMovies(MOST_POPULAR, API_KEY);
        } else {
            call = service.getTopRatedMovies(TOP_RATED, API_KEY);
        }

        // Asynchronously send the HTTP request and notify the callback of its HTTP response
        // or if an error occurred talking to the server, creating the HTTP request
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movies = response.body().getResults();
                mPosterAdapter.setMovieData(movies);
                mPosterAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

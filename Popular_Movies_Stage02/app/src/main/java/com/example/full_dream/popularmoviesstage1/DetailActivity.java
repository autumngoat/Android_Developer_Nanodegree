/*
 *
 *  * PROJECT LICENSE
 *  *
 *  * This project was submitted by Seong Wang as part of the Android Developer Nanodegree at Udacity.
 *  *
 *  * As part of Udacity Honor code:
 *  *   - I hereby confirm that all project submissions consist of my own work. Accordingly, I will document and cite the origins of any part(s) of my project submissions that were taken from websites, books, forums, blog posts, GitHub repositories, or any other source and explain why I used them for any part of my submission.
 *  *     - Acceptable sources consist of:
 *  *         - Unmodified or modified code from the Udacity courses
 *  *         - A modest amount of unmodified or modified code from third-party sources with attribution
 *  *     - NOT ACCEPTABLE: Any part of another student’s work, even with attribution
 *  *   - I understand that Udacity will check my submission for plagiarism, and that failure to adhere to the Udacity Honor Code
 *  *   may result in the cancellation of my enrollment without refund.
 *  *   - I understand that I may be asked to explain my work in a video call with a Udacity Representative before my Nanodegree is
 *  *   conferred.
 *  *
 *  * I, the author of the project, allow you to check the code as a reference so long as you are not a fellow Android Developer
 *  * Nanodegree student, but if you submit it, it's your own responsibility if you get expelled.
 *  *
 *  * Copyright (c) 2018 Seong Wang
 *  *
 *  * Besides the above notice, the following license applies and this license notice must be included in all works derived from
 *  * this project.
 *  *
 *  * MIT License
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in all
 *  * copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  * SOFTWARE.
 *
 */

package com.example.full_dream.popularmoviesstage1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.full_dream.popularmoviesstage1.model.Movie;
import com.example.full_dream.popularmoviesstage1.model.Review;
import com.example.full_dream.popularmoviesstage1.model.Trailer;
import com.example.full_dream.popularmoviesstage1.utils.RetrofitClient;
import com.example.full_dream.popularmoviesstage1.utils.TheMovieDBService;
import com.squareup.picasso.Picasso;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
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
    @BindView(R.id.tv_detail_title)
    TextView mTitle;
    @BindView(R.id.tv_detail_vote_avg)
    TextView mRating;
    @BindView(R.id.tv_detail_release_date)
    TextView mReleaseDate;
    @BindView(R.id.tv_detail_summary)
    TextView mSummary;
    @BindView(R.id.iv_detail_poster)
    ImageView mPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {

            Bundle b = intentThatStartedThisActivity.getExtras();

            if(intentThatStartedThisActivity.hasExtra("deets")){

                Movie movie = b.getParcelable("deets");

                if(movie != null) {
                    // Icons made by "https://www.flaticon.com/authors/freprikepik"
                    // Title: "Popcorn"
                    // Licensed by Creative Commons BY 3.0
                    Picasso.get()
                            .load(movie.getPosterPath())
                            .placeholder(R.drawable.ic_popcorn)
                            .error(R.drawable.ic_popcorn)
                            .into(mPoster);

                    String title = movie.getTitle();
                    String rating = Double.toString(movie.getVoteAverage());
                    String date = movie.getReleaseDate();
                    String summary = movie.getOverview();

                    mTitle.setText(title);
                    mRating.setText(rating);
                    mReleaseDate.setText(date);
                    mSummary.setText(summary);


                }
            }
        }
    }

    /**
     * Helper function to be able to call Retrofit whenever data needs to be retrieved.
     */
    public void callRetrofitForTrailers(){
        // Instantiate the Retrofit (type safe HTTP) client
        RetrofitClient client = new RetrofitClient();

        // Pass service interface to create() to generate an implementation of the API endpoint
        TheMovieDBService service = client.getClient().create(TheMovieDBService.class);

        // Call represents the HTTP request while the generic parameter, in this case
        // MovieResponse, represents the HTTP response body type which will be converted
        // by one of the Converter.Factory instances (Moshi) to JSON to POJO(s).
        Call<Trailer> call;

        call = service.getTrailers("", "");

        // Asynchronously send the HTTP request and notify the callback of its HTTP response
        // or if an error occurred talking to the server, creating the HTTP request
        call.enqueue(new Callback<Trailer>() {
            @Override
            public void onResponse(Call<Trailer> call, Response<Trailer> response) {
//                List<Movie> movies = response.body().getResults();
//                mPosterAdapter.setMovieData(movies);
//                mPosterAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Trailer> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Helper function to be able to call Retrofit whenever data needs to be retrieved.
     */
    public void callRetrofitForReviews(){
        // Instantiate the Retrofit (type safe HTTP) client
        RetrofitClient client = new RetrofitClient();

        // Pass service interface to create() to generate an implementation of the API endpoint
        TheMovieDBService service = client.getClient().create(TheMovieDBService.class);

        // Call represents the HTTP request while the generic parameter, in this case
        // MovieResponse, represents the HTTP response body type which will be converted
        // by one of the Converter.Factory instances (Moshi) to JSON to POJO(s).
        Call<Review> call;

        call = service.getReviews("", "");

        // Asynchronously send the HTTP request and notify the callback of its HTTP response
        // or if an error occurred talking to the server, creating the HTTP request
        call.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
//                List<Movie> movies = response.body().getResults();
//                mPosterAdapter.setMovieData(movies);
//                mPosterAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

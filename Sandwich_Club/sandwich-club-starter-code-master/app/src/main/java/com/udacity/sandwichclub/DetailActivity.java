/*
 * PROJECT LICENSE
 *
 * This project was submitted by Seong Wang as part of the Android Developer Nanodegree at Udacity.
 *
 * As part of Udacity Honor code:
 *   - I hereby confirm that all project submissions consist of my own work. Accordingly, I will document and cite the origins of any part(s) of my project submissions that were taken from websites, books, forums, blog posts, GitHub repositories, or any other source and explain why I used them for any part of my submission.
 *     - Acceptable sources consist of:
 *         - Unmodified or modified code from the Udacity courses
 *         - A modest amount of unmodified or modified code from third-party sources with attribution
 *     - NOT ACCEPTABLE: Any part of another student’s work, even with attribution
 *   - I understand that Udacity will check my submission for plagiarism, and that failure to adhere to the Udacity Honor Code
 *   may result in the cancellation of my enrollment without refund.
 *   - I understand that I may be asked to explain my work in a video call with a Udacity Representative before my Nanodegree is
 *   conferred.
 *
 * I, the author of the project, allow you to check the code as a reference so long as you are not a fellow Android Developer
 * Nanodegree student, but if you submit it, it's your own responsibility if you get expelled.
 *
 * Copyright (c) 2018 Seong Wang
 *
 * Besides the above notice, the following license applies and this license notice must be included in all works derived from
 * this project.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Provides the details of each sandwich list item with an image
 * and a few texts explaining the sandwich.
 */

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    // Followed the ButterKnife tutorial:
    // https://www.androidhive.info/2017/10/android-working-with-butterknife-viewbinding-library/
    // as suggested by previous Reviewer
    @BindView(R.id.description_tv) TextView descriptionTv;
    @BindView(R.id.origin_tv) TextView originTv;
    @BindView(R.id.also_known_tv) TextView akaTv;
    @BindView(R.id.ingredients_tv) TextView ingredientsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Bind the view using ButterKnife
        ButterKnife.bind(this);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        descriptionTv.setText(sandwich.getDescription());
        originTv.setText(sandwich.getPlaceOfOrigin());

        List<String> aliases = sandwich.getAlsoKnownAs();
        for(int i=0; i<aliases.size(); i++){
            if(i+1 < aliases.size()){
                akaTv.append(aliases.get(i) + "\n");
            } else {
                akaTv.append(aliases.get(i));
            }
        }

        List<String> ingredients = sandwich.getIngredients();
        for(int i=0; i<ingredients.size(); i++){
            if(i+1 < ingredients.size()){
                ingredientsTv.append(ingredients.get(i) + ", ");
            } else {
                ingredientsTv.append("and " + ingredients.get(i));
            }
        }
    }
}

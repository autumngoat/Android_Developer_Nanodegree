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

package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Provides methods to parse JSON and load parsed data into data model(s).
 */

public class JsonUtils {
    private static final String TAG = JsonUtils.class.getSimpleName();

    /**
     * Parses the JSON string and loads the contents into a Sandwich object.
     */
    public static Sandwich parseSandwichJson(String json) {
        Sandwich breadMiddleBread = new Sandwich();
        ArrayList<String> aliases = new ArrayList<>();
        ArrayList<String> ingredients = new ArrayList<>();

        try {
            JSONObject sandwich = new JSONObject(json);
            JSONObject names = sandwich.getJSONObject("name");

            String mainName = names.getString("mainName");

            JSONArray akaArray = names.getJSONArray("alsoKnownAs");
            for (int i = 0; i < akaArray.length(); i++) {
                String alias = akaArray.getString(i);
                aliases.add(alias);
            }

            String origin = sandwich.getString("placeOfOrigin");

            String description = sandwich.getString("description");

            String image = sandwich.getString("image");

            JSONArray ingredientsArray = sandwich.getJSONArray("ingredients");
            for (int i = 0; i < ingredientsArray.length(); i++) {
                String ingredient = ingredientsArray.getString(i);
                ingredients.add(ingredient);
            }

            breadMiddleBread = new Sandwich(mainName, aliases, origin, description, image, ingredients);
        } catch (JSONException e) {
            Log.e("TAG", "Problem parsing the sandwich JSON " + e);
        }

        return breadMiddleBread;
    }
}

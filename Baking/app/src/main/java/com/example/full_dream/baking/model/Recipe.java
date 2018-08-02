/*
 *  PROJECT LICENSE
 *
 *  This project was submitted by Seong Wang as part of the Android Developer Nanodegree at Udacity.
 *
 *  As part of Udacity Honor code:
 *    - I hereby confirm that all project submissions consist of my own work. Accordingly, I will document and cite the origins of any part(s) of my project submissions that were taken from websites, books, forums, blog posts, GitHub repositories, or any other source and explain why I used them for any part of my submission.
 *      - Acceptable sources consist of:
 *          - Unmodified or modified code from the Udacity courses
 *          - A modest amount of unmodified or modified code from third-party sources with attribution
 *      - NOT ACCEPTABLE: Any part of another student’s work, even with attribution
 *    - I understand that Udacity will check my submission for plagiarism, and that failure to adhere to the Udacity Honor Code
 *    may result in the cancellation of my enrollment without refund.
 *    - I understand that I may be asked to explain my work in a video call with a Udacity Representative before my Nanodegree is
 *    conferred.
 *
 *  I, the author of the project, allow you to check the code as a reference so long as you are not a fellow Android Developer
 *  Nanodegree student, but if you submit it, it's your own responsibility if you get expelled.
 *
 *  Copyright (c) 2018 Seong Wang
 *
 *  Besides the above notice, the following license applies and this license notice must be included in all works derived from
 *  this project.
 *
 *  MIT License
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.example.full_dream.baking.model;

// Android Imports

// 3rd Party Import - Moshi
import com.squareup.moshi.Json;

// Java Imports
import java.util.List;

/**
 * Provides a data model to represent the initial JSON HTTP response as a POJO
 * that contains Recipe JSONObjects that contain an id, name, a list of Ingredients, a list of
 * steps, and an image.
 */
public class Recipe{
    @Json(name = "id")
    private Integer id;
    @Json(name = "name")
    private String name;
    @Json(name = "ingredients")
    private List<Ingredient> ingredients = null;
    @Json(name = "steps")
    private List<Step> steps = null;
    @Json(name = "servings")
    private Integer servings;
    @Json(name = "image")
    private String image;

    private static final String PLACEHOLDER = "empty";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    // Format servings to make UI clearer
    public String getServings() {
        return "Servings: " + servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        // Null and empty String check for image source
        // Null check and empty String check
        //  Must return non-empty non-null String
        //   Picasso.load() states that it will throw an IllegalArgumentException if the URL is
        //   empty or null.
        //    Source:
        //     https://square.github.io/picasso/2.x/picasso/
        if(image == null || image.isEmpty()){
            return PLACEHOLDER;
        } else {
            return image;
        }
    }

    public void setImage(String image) {
        this.image = image;
    }
}

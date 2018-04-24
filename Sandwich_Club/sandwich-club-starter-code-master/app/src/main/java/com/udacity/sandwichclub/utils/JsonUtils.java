package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {
    private static final String TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {
        Sandwich breadMiddleBread = new Sandwich();
        ArrayList<String> aliases = new ArrayList<>();
        ArrayList<String> ingredients = new ArrayList<>();

        try{
            JSONObject sandwich = new JSONObject(json);

            String name = sandwich.getString("mainName");

            JSONArray akaArray = sandwich.getJSONArray("alsoKnownAs");
            for(int i=0; i<akaArray.length(); i++){
                String alias = akaArray.getString(i);
                aliases.add(alias);
            }

            String description = sandwich.getString("description");

            String image = sandwich.getString("image");

            JSONArray ingredientsArray = sandwich.getJSONArray("ingredients");
            for(int i=0; i<ingredientsArray.length(); i++){
                String ingredient = ingredientsArray.getString(i);
                ingredients.add(ingredient);
            }

            breadMiddleBread.setMainName(name);
            breadMiddleBread.setAlsoKnownAs(aliases);
            breadMiddleBread.setDescription(description);
            breadMiddleBread.setImage(image);
            breadMiddleBread.setIngredients(ingredients);
        } catch (JSONException e){
            Log.e("TAG", "Problem parsing the sandwich JSON " + e);
        }

        return breadMiddleBread;
    }
}

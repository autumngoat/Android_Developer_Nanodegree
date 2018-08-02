package com.example.full_dream.baking.viewmodel;

// Android Imports
import android.arch.lifecycle.ViewModel;

// 3rd Party Imports - com - Baking
import com.example.full_dream.baking.data.RecipeRepository;

/**
 * This ViewModel is used to cache the list of Ingredient objects wrapped in a LiveData object, the
 * list of Step objects wrapped in a LiveData object, and modify data passed into it.
 */
public class RecipeDetailViewModel extends ViewModel {

    // Hold a reference to the repository
    private RecipeRepository mRecipeRepository;

    /**
     * Constructor to instantiate repository reference.
     */
    public RecipeDetailViewModel(){
        this.mRecipeRepository = new RecipeRepository();
    }


}

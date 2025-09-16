package com.zhogin.reciepeapp.features.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhogin.reciepeapp.features.detail.presentation.RecipeDetailUpdateIsFavUiState
import com.zhogin.reciepeapp.features.favorites.domain.FavoriteRecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoriteRecipeViewModel(
    private val favoriteRecipeRepository: FavoriteRecipeRepository
): ViewModel() {

    private val  _favoriteUiState = MutableStateFlow(FavoriteScreenUiState())
    val favoriteUiState = _favoriteUiState.asStateFlow()
    


    init {
        viewModelScope.launch {
            getFavoriteList()
        }
    }


    suspend fun getFavoriteList() {
        val recipeList = favoriteRecipeRepository.getAllFavoriteRecipes()
        if (recipeList.isSuccess) {
            _favoriteUiState.update {
                it.copy(
                    favoriteList = recipeList.getOrDefault(emptyList()),
                    favoriteListIsLoading = false
                )
            }
        } else {
            _favoriteUiState.update {
                it.copy(
                    favoriteError = recipeList.exceptionOrNull()?.message,
                    favoriteListIsLoading = false
                )
            }
        }
    }

}
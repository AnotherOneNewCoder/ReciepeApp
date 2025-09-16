package com.zhogin.reciepeapp.features.feed.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhogin.reciepeapp.features.feed.domain.repository.FeedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedViewModel(
    private val feedRepository: FeedRepository
): ViewModel() {
    private var _feedUiState = MutableStateFlow(FeedUiState())
    val feedUiState = _feedUiState.asStateFlow()

    init {
        viewModelScope.launch {
            getRecipeList()
        }
    }

    private suspend fun getRecipeList() {
       val recipes = feedRepository.getRecipeList()
        if (recipes.isSuccess) {
            _feedUiState.value = _feedUiState.value.copy(
                recipes = recipes.getOrDefault(emptyList()),
                recipeLoading = false,
            )
        } else {
            _feedUiState.update {
                it.copy(
                    recipeError = recipes.exceptionOrNull()?.message,
                    recipeLoading = false,
                )
            }
        }
    }
}
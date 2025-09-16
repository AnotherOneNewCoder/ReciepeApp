package com.zhogin.reciepeapp.features.detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhogin.reciepeapp.features.detail.domain.repository.RecipeDetailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private val recipeDetailRepository: RecipeDetailRepository
): ViewModel() {

    private var _detailUiState = MutableStateFlow(RecipeDetailUiState())
    val detailUiState = _detailUiState.asStateFlow()

    private val  _updateIsFavUiState = MutableStateFlow(RecipeDetailUpdateIsFavUiState())
    val updateIsFavUiState = _updateIsFavUiState.asStateFlow()

    fun getRecipeDetail(id: Long) {
        viewModelScope.launch {
            val recipeDetailResponse = recipeDetailRepository.getRecipeDetail(id)
            if (recipeDetailResponse.isSuccess) {
                _detailUiState.value = _detailUiState.value.copy(
                    recipeItem = recipeDetailResponse.getOrNull(),
                    detailIsLoading = false
                )
            } else {
                _detailUiState.value = _detailUiState.value.copy(
                    detailError = recipeDetailResponse.exceptionOrNull()?.message,
                    detailIsLoading = false
                )
            }
        }
    }
    fun updateIsFavorite(recipeId: Long, isAdding: Boolean) {
        viewModelScope.launch {
            try {
                _updateIsFavUiState.update {
                    it.copy(
                        isUpdating = true
                    )
                }
                if (isAdding) {
                    recipeDetailRepository.addToFavorite(recipeId)
                } else {
                    recipeDetailRepository.removeFromFavorite(recipeId)
                }
                _detailUiState.update {
                    it.copy(
                        recipeItem = _detailUiState.value.recipeItem?.copy(
                            isFavorite = isAdding
                        )
                    )
                }
                _updateIsFavUiState.update {
                    it.copy(
                        isSuccess = true,
                        isUpdating = false,
                    )
                }
            } catch (e: Exception) {
                _updateIsFavUiState.update {
                    it.copy(
                        error = e.message,
                        isUpdating = false,
                    )
                }
            }
        }

    }
}
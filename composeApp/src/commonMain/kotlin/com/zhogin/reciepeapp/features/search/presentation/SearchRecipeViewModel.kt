package com.zhogin.reciepeapp.features.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhogin.reciepeapp.features.common.domain.model.RecipeItem
import com.zhogin.reciepeapp.features.search.domain.SearchRecipeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchRecipeViewModel(
    private val  searchRecipeRepository: SearchRecipeRepository
): ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _searchScreenUiState = MutableStateFlow(SearchScreenState())
    val searchScreenUiState = _searchScreenUiState.asStateFlow()

    init {
        triggerFetchItems()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun triggerFetchItems() = viewModelScope.launch {
        _searchText
            .debounce(500)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                flow {
                    val result = fetchData(query)
                    emit(result)
                }
            }
            .catch { error ->
                _searchScreenUiState.update {
                    it.copy(
                        idle = false,
                        error = error.message
                    )
                }
            }
            .collect { result ->
                _searchScreenUiState.update {
                    it.copy(
                        idle = false,
                        success = true,
                        results = result
                    )
                }
            }
    }

    private suspend fun fetchData(query: String): List<RecipeItem> {
        if (query.isEmpty()) return emptyList()
        val result = searchRecipeRepository.searchRecipeByText(query)
        return result.getOrNull() ?: emptyList()
    }

    fun onSearchQueryChange(query: String) {
        _searchText.value = query
    }
}
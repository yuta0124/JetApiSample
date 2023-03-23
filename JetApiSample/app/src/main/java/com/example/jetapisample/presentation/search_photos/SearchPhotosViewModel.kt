package com.example.jetapisample.presentation.search_photos

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetapisample.common.NetworkResponse
import com.example.jetapisample.domain.use_case.SearchPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchPhotosViewModel @Inject constructor(
    private val searchPhotosUseCase: SearchPhotosUseCase,
): ViewModel() {

    private val _state = mutableStateOf(SearchPhotosState())
    val state: State<SearchPhotosState> = _state
    var query by mutableStateOf("programming")

    init {
        searchPhotos()
    }

    fun searchPhotos() {
        searchPhotosUseCase(query).onEach { result ->
            when (result) {
                is NetworkResponse.Loading -> {
                    _state.value = SearchPhotosState(isLoading = true)
                }
                is NetworkResponse.Success -> {
                    _state.value = SearchPhotosState(photos = result.data ?: emptyList(), isLoading = false)
                }
                is NetworkResponse.Failure -> {
                    _state.value = SearchPhotosState(isLoading = false, error = result.error)
                }
            }
        }.launchIn(viewModelScope)
    }
}
package com.example.jetapisample.presentation.photo_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetapisample.common.NetworkResponse
import com.example.jetapisample.domain.use_case.GetPhotoDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val getPhotoDetailUseCase: GetPhotoDetailUseCase,
    savedStateHandle: SavedStateHandle  //遷移時の引数データを受け取るために必要
): ViewModel() {

    private val _state = mutableStateOf(PhotoDetailState())
    val state: State<PhotoDetailState> = _state

    init {
        savedStateHandle.get<String>("photoId")?.let {photoId ->
            getPhotoDetail(photoId = photoId)
        }
    }

    private fun getPhotoDetail(photoId: String) {
        //note: //flowのemitが呼ばれると毎回onEachが呼ばれる
        getPhotoDetailUseCase(photoId = photoId).onEach {result ->
            when(result) {
                is NetworkResponse.Loading -> {
                    _state.value = PhotoDetailState(isLoading = true)
                }
                is NetworkResponse.Success -> {
                    _state.value = PhotoDetailState(photoDetail = result.data)
                }
                is NetworkResponse.Failure -> {
                    _state.value = PhotoDetailState(error = result.error)
                }
            }
        }.launchIn(viewModelScope)
    }
}
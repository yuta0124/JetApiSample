package com.example.jetapisample.presentation.photo_detail

import com.example.jetapisample.domain.model.PhotoDetail

data class PhotoDetailState(
    val isLoading: Boolean = false,
    val photoDetail: PhotoDetail? = null,
    val error: String? = null
)

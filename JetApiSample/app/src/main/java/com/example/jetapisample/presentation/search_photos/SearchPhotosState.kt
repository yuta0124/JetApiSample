package com.example.jetapisample.presentation.search_photos

import com.example.jetapisample.domain.model.Photo

data class SearchPhotosState(
    val isLoading: Boolean = false,
    val photos: List<Photo> = emptyList(),
    val error: String? = null
)

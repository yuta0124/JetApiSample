package com.example.jetapisample.domain.repository

import com.example.jetapisample.data.remote.PhotoDetailDto
import com.example.jetapisample.data.remote.SearchPhotosResultDto

interface PhotoRepository {

    suspend fun searchPhotos(query: String): SearchPhotosResultDto

    suspend fun getPhotoById(id: String): PhotoDetailDto

}
package com.example.jetapisample.data.repository

import com.example.jetapisample.data.remote.PhotoDetailDto
import com.example.jetapisample.data.remote.SearchPhotosResultDto
import com.example.jetapisample.data.remote.UnsplashApi
import com.example.jetapisample.domain.repository.PhotoRepository
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val api: UnsplashApi
): PhotoRepository {

    override suspend fun searchPhotos(query: String): SearchPhotosResultDto {
        return api.searchPhotos(query)
    }

    override suspend fun getPhotoById(id: String): PhotoDetailDto {
        return api.getPhotoById(id)
    }

}
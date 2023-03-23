package com.example.jetapisample.domain.use_case

import com.example.jetapisample.common.NetworkResponse
import com.example.jetapisample.data.remote.toPhotoDetail
import com.example.jetapisample.domain.model.PhotoDetail
import com.example.jetapisample.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotoDetailUseCase @Inject constructor(private val repository: PhotoRepository){

    //画像ID　→　通信　→　画像詳細情報を返す
    operator fun invoke(photoId: String): Flow<NetworkResponse<PhotoDetail>> = flow {
        emit(NetworkResponse.Loading<PhotoDetail>())
        try {
            val photoDetail = repository.getPhotoById(photoId).toPhotoDetail()
            emit(NetworkResponse.Success<PhotoDetail>(photoDetail))
        } catch (e: Exception) {
            emit(NetworkResponse.Failure<PhotoDetail>(e.message.toString()))
        }
    }
}
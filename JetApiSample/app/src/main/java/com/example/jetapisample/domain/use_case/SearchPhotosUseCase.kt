package com.example.jetapisample.domain.use_case

import com.example.jetapisample.common.NetworkResponse
import com.example.jetapisample.data.remote.toPhotos
import com.example.jetapisample.domain.model.Photo
import com.example.jetapisample.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchPhotosUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    //note: operator →　invokeのオーバーロード(オーバーロードメソッド名一覧　→　https://atmarkit.itmedia.co.jp/ait/articles/1805/14/news012_3.html)
    operator fun invoke(query: String): Flow<NetworkResponse<List<Photo>>> = flow {
        emit(NetworkResponse.Loading<List<Photo>>())
        try {
            val photos = repository.searchPhotos(query).toPhotos()
            emit(NetworkResponse.Success<List<Photo>>(data = photos))
        } catch (e: Exception) {
            emit(NetworkResponse.Failure<List<Photo>>(e.message.toString()))
        }
    }
}
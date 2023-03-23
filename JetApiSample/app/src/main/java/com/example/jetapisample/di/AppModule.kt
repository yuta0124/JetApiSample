package com.example.jetapisample.di

import com.example.jetapisample.common.Constants.BASE_URL
import com.example.jetapisample.data.remote.UnsplashApi
import com.example.jetapisample.data.repository.PhotoRepositoryImpl
import com.example.jetapisample.domain.repository.PhotoRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)   //各モジュールが使用またはインストールされる Android クラスを知らせる
object AppModule {

    @Provides
    @Singleton  //application単位でインスタンスを提供する
    fun provideUnsplashApi(): UnsplashApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(
                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            )).build().create(UnsplashApi::class.java)
    }

    @Provides
    @Singleton
    fun providePhotoRepository(api: UnsplashApi): PhotoRepository {
        return PhotoRepositoryImpl(api)
    }
}
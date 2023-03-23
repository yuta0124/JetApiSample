package com.example.jetapisample.presentation

sealed class ScreenRoute(val route: String) {
    object SearchPhotoScreen: ScreenRoute("search_photos_screen")
    object PhotoDetailScreen: ScreenRoute("photo_detail_screen")
}

package com.example.jetapisample.presentation.search_photos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetapisample.presentation.ScreenRoute
import com.example.jetapisample.presentation.search_photos.components.PhotoThumbnail
import com.example.jetapisample.presentation.search_photos.components.SearchBar

@Composable
fun SearchPhotosScreen(
    viewModel: SearchPhotosViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value

    Scaffold(
        topBar = {
            SearchBar(
                searchText = viewModel.query,
                onDone = { viewModel.searchPhotos() },
                onSearchTextChanged = {
                    viewModel.query = it
                }
            )
        }
    ) { paddingValue ->
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.isLoading -> {
                    //ローディング
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                !state.error.isNullOrBlank() -> {
                    //エラー表示
                    Text(
                        text = state.error,
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colors.error
                    )
                }
                else -> {
                    LazyColumn(modifier = Modifier.padding(paddingValue)) {
                        items(state.photos) { photo ->
                            PhotoThumbnail(photo = photo, onClick = {
                                //note: navControllerを利用してクリック時に引数ありで画面遷移する
                                navController.navigate(ScreenRoute.PhotoDetailScreen.route + "/${photo.photoId}")
                            })
                        }
                    }
                }
            }
        }
    }
}
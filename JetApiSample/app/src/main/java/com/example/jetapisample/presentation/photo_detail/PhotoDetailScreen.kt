package com.example.jetapisample.presentation.photo_detail

import android.widget.Space
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.jetapisample.domain.model.PhotoDetail
import com.example.jetapisample.presentation.components.CountLabel
import kotlin.math.min

@Composable
fun PhotoDetailScreen(
    viewModel: PhotoDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            !state.error.isNullOrBlank() -> {
                Text(
                    text = state.error,
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colors.error
                )
            }
            else -> {   //成功時
                state.photoDetail?.let { photoDetail ->
                    PhotoDetailContent(photoDetail = photoDetail)
                }
            }
        }
    }
}

@Composable
fun PhotoDetailContent(photoDetail: PhotoDetail) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Box(modifier = Modifier.heightIn(min = 200.dp)) {
            //インディケータ表示フラグ
            var isLoadingImage by remember { mutableStateOf(true) }

            if (isLoadingImage) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            AsyncImage(
                model = photoDetail.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(   //画像のボトムを丸める
                            topStartPercent = 0,
                            topEndPercent = 0,
                            bottomEndPercent = 5,
                            bottomStartPercent = 5
                        )
                    ),
                onSuccess = {//画像表示成功時、インディケー表示フラグをfalseにする
                    isLoadingImage = false
                }
            )
        }
        Column(modifier = Modifier.padding(10.dp)) {
            //Description
            Text(
                text = photoDetail.description ?: "no description",
                style = MaterialTheme.typography.h6,
            )

            Spacer(modifier = Modifier.height(20.dp))
            
            //photographer
            Text(
                text = photoDetail.photographer ?: "Unknown photographer",
                style = MaterialTheme.typography.body2,
            )
            
            Spacer(modifier = Modifier.height(20.dp))

            //likes
            CountLabel(
                imageVector = Icons.Default.Favorite,
                count = photoDetail.likes ?: 0,
                iconTint = Color.Magenta
            )
            //downloads
            CountLabel(
                imageVector = Icons.Default.Share,
                count = photoDetail.downloads ?: 0,
                iconTint = Color.Green
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            //camera
            Text(
                text = "Camera: ${photoDetail.camera}"
            )
            //location
            Text(
                text = "Location: ${photoDetail.location}"
            )
        }
    }
}
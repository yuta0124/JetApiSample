package com.example.jetapisample.presentation.search_photos.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.jetapisample.domain.model.Photo
import com.example.jetapisample.presentation.components.CountLabel
import com.example.jetapisample.presentation.ui.theme.JetApiSampleTheme

@Composable
fun PhotoThumbnail(
    photo: Photo,
     onClick: (Photo) -> Unit
    ) {
    Box(
        modifier = Modifier
            .background(Color.Black)
            .heightIn(min = 200.dp)  //最低限の高さ指定
            .clickable { onClick(photo) },
        contentAlignment = Alignment.BottomCenter,
    ) {
        //note: Indicatorが先に表示されるため、AsyncImageが表示されたらIndicatorは隠れる
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        AsyncImage(
            model = photo.imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
            )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.8f),
            ) {
                Text(
                    text = photo.description ?: "No description",
                    color = Color.White,
                    style = MaterialTheme.typography.h6,
                    )
                Text(
                    text = photo.photographer ?: "Unknown photographer",
                    color = Color.White,
                    style = MaterialTheme.typography.body2
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            CountLabel(
                imageVector = Icons.Default.Favorite,
                count = photo.likes ?: 0,
                iconTint = Color.Magenta,
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
fun PreviewPhotoThumbnail(){
    JetApiSampleTheme {
        PhotoThumbnail(photo = Photo("1", "説明文", 5, "https://images.unsplash.com/photo-1679433636526-1809d43049e8?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwzfHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=60", "撮影者"), onClick = {})
    }
}
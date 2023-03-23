package com.example.jetapisample.presentation.search_photos.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    searchText: String,
    placeholder: String = "Search...",
    onDone: () -> Unit,
    onSearchTextChanged: (String) -> Unit
) {
    var showClearButton by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    TopAppBar(
        backgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White
    ) {
        OutlinedTextField(value = searchText,
            onValueChange = onSearchTextChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp)
                .onFocusChanged { focusState ->     //フォーカスによりクリアボタンを表示・非表示
                    showClearButton = focusState.isFocused  //フォーカスが当たっていればtrue
                }
                .focusRequester(focusRequester),   //外部からフォーカスを操作する
                placeholder = {
                    Text(text = placeholder)
                },
            trailingIcon = {
                IconButton(onClick = { onSearchTextChanged("") }) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = null)
                }
            },
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),  //キーボードの上書き
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()  //Doneボタンが押されたらキーボードを非表示にする
                onDone
            })
        )
    }
    LaunchedEffect(Unit) {
        //Composable が表示されるタイミングだけfocusRequesterを利用して、テキストフィールドにフォーカスを当てる
        focusRequester.requestFocus()
    }
}
package com.example.infoappcompose.ui_components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.infoappcompose.MainViewModel
import kotlinx.coroutines.launch


/**
 * Создаём шапку с кнопками меню и остальными
 */
@Composable
fun MainTopBar(
    title: String,
    scaffoldState: ScaffoldState,
    onFavClick: () -> Unit    //для изменения заголовка при просмотре списка Избранные продукты
) {
    val coroutine = rememberCoroutineScope()
    TopAppBar(
        title = {
            Text(text = title)
        },
        backgroundColor = Color.White,
        navigationIcon = {
            IconButton(
                onClick = {
                    coroutine.launch {
                        scaffoldState.drawerState.open()   //чтобы открывалось меню, необходимо использовать Coroutine
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu"
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    onFavClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Menu"
                )
            }
        }
    )

}
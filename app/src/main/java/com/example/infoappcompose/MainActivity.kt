package com.example.infoappcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.example.infoappcompose.ui.theme.InfoAppComposeTheme
import com.example.infoappcompose.ui_components.DrawerMenu
import com.example.infoappcompose.ui_components.MainTopBar
import com.example.infoappcompose.utils.DrawerEvents
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scaffoldState = rememberScaffoldState()

            // для автоматического закрытия меню, после нажатия, используем Коррутину
            val coroutineScope = rememberCoroutineScope()

            val topBarTitle = remember {
                mutableStateOf("Продукты")  //пока хардкодим наименование загаловка шапки
            }
            InfoAppComposeTheme {
                Scaffold(      //создаём контейнер для создания шапки и кнопок приложения
                    scaffoldState = scaffoldState,
                    topBar = {
                        MainTopBar(
                            title = topBarTitle.value,
                            scaffoldState
                        )
                    },
                    drawerContent = {
                        DrawerMenu() {event ->
                            when(event) {
                                is DrawerEvents.OnItemClick -> {
                                    topBarTitle.value = event.title
                                }
                            }
                            coroutineScope.launch {
                                scaffoldState.drawerState.close() // автозакрытие меню
                            }
                        }
                    }
                ) {
                    it
                }
            }
        }
    }
}

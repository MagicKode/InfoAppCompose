package com.example.infoappcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.infoappcompose.ui.theme.InfoAppComposeTheme
import com.example.infoappcompose.ui_components.DriverMenu
import com.example.infoappcompose.ui_components.MainTopBar

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scaffoldState = rememberScaffoldState()
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
                        DriverMenu()
                    }
                ) {
                    it
                }
            }
        }
    }
}

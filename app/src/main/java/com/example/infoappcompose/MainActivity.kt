package com.example.infoappcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.infoappcompose.ui.theme.InfoAppComposeTheme
import com.example.infoappcompose.ui_components.InfoScreen
import com.example.infoappcompose.ui_components.MainScreen
import com.example.infoappcompose.utils.Routes
import com.example.infoappcompose.utils.ListItem
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()  // ДОБАВЛЯЕМ НАВИГАЦИЮ! Для переключения экранов
            var item: ListItem? = null
            InfoAppComposeTheme {
                NavHost(     //происходит переключение между экранами MainScreen / InfoScreen
                    navController = navController,
                    startDestination = Routes.MAIN_SCREEN
                ) {
                    composable(Routes.MAIN_SCREEN) {
                        MainScreen(context = this@MainActivity) { listItem ->
                            item = listItem
                            navController.navigate(Routes.INFO_SCREEN)
                        }
                    }
                    composable(Routes.INFO_SCREEN) {
                        InfoScreen(item = item!!)
                    }
                }
            }
        }
    }
}

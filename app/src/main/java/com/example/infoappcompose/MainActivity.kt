package com.example.infoappcompose

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.infoappcompose.ui.theme.InfoAppComposeTheme
import com.example.infoappcompose.ui_components.DrawerMenu
import com.example.infoappcompose.ui_components.MainTopBar
import com.example.infoappcompose.utils.DrawerEvents
import com.example.infoappcompose.utils.IdArraylist
import com.example.infoappcompose.utils.ListItem
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scaffoldState = rememberScaffoldState()

            // для автоматического закрытия меню, после нажатия, используем Коррутину
            val coroutineScope = rememberCoroutineScope()

            val mainList = remember {
                mutableStateOf(getListItemsByIndex(0, this))  //первая категория по уморлчанию
            }
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
                        DrawerMenu() { event ->
                            when (event) {
                                is DrawerEvents.OnItemClick -> {
                                    topBarTitle.value = event.title
                                    mainList.value = getListItemsByIndex(
                                        event.index, this@MainActivity
                                    )
                                }
                            }
                            coroutineScope.launch {
                                scaffoldState.drawerState.close() // автозакрытие меню
                            }
                        }
                    }
                ) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(mainList.value) { item ->
                            MainListItem(item = item)
                        }
                    }
                }
            }
        }
    }
}

/**
 * нужна функция чтобы превращать массив.
 * создаём список, который передаётся в mainList
 * при нажатии на элемент меню, мы получаем список и обновляем mainList
 */
private fun getListItemsByIndex(index: Int, context: Context): List<ListItem> {
    val list = ArrayList<ListItem>()               //сможем добавлять сюда элементы
    val arrayList =
        context.resources.getStringArray(          //массив для обработки  нескольких растений одного типа
            IdArraylist.listId[index]
        )

    arrayList.forEach { item ->  // в item мы циклично передаём массив объектов одного типа (продуктов одного вида)
        val itemArray =
            item.split("|")   //применяем для создания массива из 2х эклементок Идентификатор (название) и сама картинка
        list.add(
            ListItem(   // создаём список из двух частей элемента
                itemArray[0],  //title
                itemArray[1]   // навзвание картинки
            )
        )
    }
    return list
}

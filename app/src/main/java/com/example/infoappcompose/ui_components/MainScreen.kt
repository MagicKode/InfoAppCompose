package com.example.infoappcompose.ui_components

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.infoappcompose.MainListItem
import com.example.infoappcompose.utils.DrawerEvents
import com.example.infoappcompose.utils.IdArraylist
import com.example.infoappcompose.utils.ListItem
import kotlinx.coroutines.launch

/**
 *переносим в отдельный класс MainScreen  функционал из MainActivity, чтобы всё находилось по отдельным классам
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(context: Context,  onClick: (ListItem) -> Unit) {
    val scaffoldState = rememberScaffoldState()

    // для автоматического закрытия меню, после нажатия, используем Коррутину
    val coroutineScope = rememberCoroutineScope()

    val mainList = remember {
        mutableStateOf(getListItemsByIndex(0, context))  //первая категория по уморлчанию
    }
    val topBarTitle = remember {
        mutableStateOf("Продукты")  //пока хардкодим наименование загаловка шапки
    }
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
                            event.index, context
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
                MainListItem(item = item) { listItem ->
                    onClick(listItem)
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
                itemArray[1],   // навзвание картинки
                itemArray[2]   // навзвание файла
            )
        )
    }
    return list
}
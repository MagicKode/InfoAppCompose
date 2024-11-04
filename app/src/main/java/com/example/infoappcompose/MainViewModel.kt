package com.example.infoappcompose

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infoappcompose.db.MainDb
import com.example.infoappcompose.utils.ListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * для взаимодействия с БД вживляем, с помощью аннотации Hilt конструктор в, котором и инициализируем БД
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    val mainDb: MainDb
) : ViewModel() {   //т.к. мы работаем с состояниями, то используем  mutableStateOf
    val mainList = mutableStateOf(emptyList<ListItem>())

    fun getAllItemsByCategory(cat: String) = viewModelScope.launch {
        mainList.value = mainDb.dao.getAllItemsByCategory(cat)
    }

    fun insertItem(item: ListItem) = viewModelScope.launch {
        mainDb.dao.insertItem(item)
    }

    fun deleteItem(item: ListItem) = viewModelScope.launch {
        mainDb.dao.deleteItem(item)
    }

}
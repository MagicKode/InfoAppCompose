package com.example.infoappcompose

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infoappcompose.db.MainDb
import com.example.infoappcompose.utils.ListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
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
    private var job: Job? = null  //

    /**
     * при запуске, проверяем ,если Job не null, то делаем cancel и запускаем работу, следим за изменениями.
     */
    fun getAllItemsByCategory(cat: String) {
        job?.cancel() //заканчиваем одну работу и далее запускаем новую, чтобы могло обновляться
        job = viewModelScope.launch {
            mainDb.dao.getAllItemsByCategory(cat).collect{ list ->
                mainList.value = list
            }
        }
    }

    fun getFavorites() = viewModelScope.launch {
        job?.cancel()
        job = viewModelScope.launch {
            mainDb.dao.getFavorites().collect{ list ->
                mainList.value = list
            }
        }
    }

    fun insertItem(item: ListItem) = viewModelScope.launch {
        mainDb.dao.insertItem(item)
    }

    fun deleteItem(item: ListItem) = viewModelScope.launch {
        mainDb.dao.deleteItem(item)
    }

}
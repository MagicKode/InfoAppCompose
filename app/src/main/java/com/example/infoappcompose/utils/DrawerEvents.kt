package com.example.infoappcompose.utils

/**
 *  Универсальный класс DrawerEvents позволяет сгруппировать другие дата классы для создания событий при нажатии
 *  Позволяет передавать все классы события и не важно сколько переменных в событии
 *  это некая Абстракция
 */
sealed class DrawerEvents {
    data class OnItemClick(val title: String, val index: Int): DrawerEvents()

}

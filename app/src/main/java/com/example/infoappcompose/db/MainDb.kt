package com.example.infoappcompose.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.infoappcompose.utils.ListItem

/**
 * класс для настройки базы данных и создания в неё сущностей
 */
@Database(
    entities = [ListItem::class],
    version = 1
)
abstract class MainDb: RoomDatabase() {
    abstract val dao: Dao
}

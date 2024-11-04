package com.example.infoappcompose.di

import android.app.Application
import androidx.room.Room
import com.example.infoappcompose.db.MainDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton  //чтобы была создана только одна Инстанция, а не каждый раз новая
    fun provideMainDb(app: Application): MainDb {
        return Room.databaseBuilder(
            app,
            MainDb::class.java,
            "info.db"
        ).createFromAsset("db/info.db").build() // будет база браться из assets
    }
}
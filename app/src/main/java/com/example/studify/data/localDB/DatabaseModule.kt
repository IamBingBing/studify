package com.example.studify.data.localDB

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "studify.db"
    ).build()
    @Provides
    @Singleton
    fun provideMessageDao(appDatabase: AppDatabase): MessageDao = appDatabase.messageDao()
}
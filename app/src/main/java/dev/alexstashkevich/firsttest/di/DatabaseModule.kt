package dev.alexstashkevich.firsttest.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.alexstashkevich.firsttest.db.AppDataBase
import dev.alexstashkevich.firsttest.db.RequestDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            "request_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRequestDao(
        db: AppDataBase
    ): RequestDao = db.requestDao()
}
package dev.alexstashkevich.firsttest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.alexstashkevich.firsttest.db.RequestDao
import dev.alexstashkevich.firsttest.repository.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideLocalRepository(
        dao: RequestDao
    ): ILocalRepository = LocalRepositoryImp(dao)

    @Provides
    @Singleton
    fun provideRemoteRepository(
    ): IRemoteRepository = RemoteRepositoryImp()

    @Provides
    @Singleton
    fun provideRepository(
        localRepository: ILocalRepository,
        remoteRepository: IRemoteRepository
    ): IRepository = RepositoryImp(localRepository, remoteRepository)
}
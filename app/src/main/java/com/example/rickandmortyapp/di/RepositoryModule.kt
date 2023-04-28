package com.example.rickandmortyapp.di

import com.example.rickandmortyapp.model.datasource.localdatasource.PersonLocalDataSource
import com.example.rickandmortyapp.model.datasource.remotedatasource.PersonsRemoteDataSource
import com.example.rickandmortyapp.model.repository.Repository
import com.example.rickandmortyapp.model.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(
        remoteDataSource: PersonsRemoteDataSource,
        localDataSource: PersonLocalDataSource
    ): Repository {
        return RepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource
        )
    }
}
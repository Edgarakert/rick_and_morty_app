package com.example.rickandmortyapp.di

import android.content.Context
import android.content.SharedPreferences
import com.example.rickandmortyapp.model.datasource.localdatasource.PersonLocalDataSource
import com.example.rickandmortyapp.model.datasource.localdatasource.PersonLocalDataSourceImpl
import com.example.rickandmortyapp.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class LocalDatasourceModule {
    @Provides
    @Singleton
    fun provideLocalDatasource(sharedPreferences: SharedPreferences, gson: Gson): PersonLocalDataSource =
        PersonLocalDataSourceImpl(
            sharedPreferences = sharedPreferences,
            gson = gson
        )

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()
}
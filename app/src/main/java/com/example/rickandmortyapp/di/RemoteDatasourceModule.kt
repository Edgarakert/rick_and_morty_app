package com.example.rickandmortyapp.di

import com.example.rickandmortyapp.model.datasource.remotedatasource.PersonsRemoteDataSource
import com.example.rickandmortyapp.model.datasource.remotedatasource.PersonsRemoteDataSourceImpl
import com.example.rickandmortyapp.model.datasource.remotedatasource.RetrofitService
import com.example.rickandmortyapp.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RemoteDatasourceModule {

    @Provides
    @Singleton
    fun provideRemoteDatasource(retrofitService: RetrofitService): PersonsRemoteDataSource =
        PersonsRemoteDataSourceImpl(
            retrofitService = retrofitService
        )

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Provides
    @Singleton
    fun provideRetrofitService(okHttpClient: OkHttpClient): RetrofitService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RetrofitService::class.java)
    }
}
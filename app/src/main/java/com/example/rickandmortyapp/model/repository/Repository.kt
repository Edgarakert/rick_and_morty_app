package com.example.rickandmortyapp.model.repository

import com.example.rickandmortyapp.model.datasource.localdatasource.PersonLocalDataSource
import com.example.rickandmortyapp.model.datasource.remotedatasource.PersonsRemoteDataSource
import com.example.rickandmortyapp.model.entity.PersonsModelList
import com.example.rickandmortyapp.utils.error.CacheException
import com.example.rickandmortyapp.utils.error.ServerException
import retrofit2.Response
import javax.inject.Inject

interface Repository {
    suspend fun getPersons(page: Int = 1, query: String? = null): Response<PersonsModelList>
    suspend fun getPersonsFromCache(): PersonsModelList
    fun personsToCache(persons: PersonsModelList)
}

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: PersonsRemoteDataSource,
    private val localDataSource: PersonLocalDataSource
) : Repository {
    override suspend fun getPersons(page: Int, query: String?): Response<PersonsModelList> {
        try {
            return remoteDataSource.getAllPersons(page, query)
        } catch (e: ServerException) {
            throw ServerException()
        }
    }

    override suspend fun getPersonsFromCache(): PersonsModelList {
        try {
            return localDataSource.getLastPersonsFromCache()
        } catch (_: CacheException) {
            throw CacheException()
        }
    }

    override fun personsToCache(persons: PersonsModelList) {
        try {
            localDataSource.personsToCache(persons)
        } catch (e: CacheException) {
            throw CacheException()
        }
    }
}
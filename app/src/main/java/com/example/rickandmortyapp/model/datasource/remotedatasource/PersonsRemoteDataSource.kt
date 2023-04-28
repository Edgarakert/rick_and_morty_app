package com.example.rickandmortyapp.model.datasource.remotedatasource

import com.example.rickandmortyapp.model.entity.PersonsModelList
import com.example.rickandmortyapp.utils.error.ServerException
import retrofit2.Response

interface PersonsRemoteDataSource {
    suspend fun getAllPersons(page: Int = 1, query: String? = null): Response<PersonsModelList>
}

class PersonsRemoteDataSourceImpl(private val retrofitService: RetrofitService) :
    PersonsRemoteDataSource {
    override suspend fun getAllPersons(page: Int, query: String?): Response<PersonsModelList> {
        try {
            return retrofitService.request(page, query)
        } catch (e: ServerException) {
            throw ServerException()
        }
    }
}
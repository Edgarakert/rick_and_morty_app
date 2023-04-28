package com.example.rickandmortyapp.model.datasource.remotedatasource

import com.example.rickandmortyapp.model.entity.PersonsModelList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("character/?")
    suspend fun request(
        @Query("page") page: Int = 1,
        @Query("name") name: String? = null
    ): Response<PersonsModelList>
}
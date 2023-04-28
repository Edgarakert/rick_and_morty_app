package com.example.rickandmortyapp.model.datasource.localdatasource

import android.content.SharedPreferences
import com.example.rickandmortyapp.model.entity.PersonsModelList
import com.example.rickandmortyapp.utils.Constants
import com.example.rickandmortyapp.utils.error.CacheException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface PersonLocalDataSource {
    suspend fun getLastPersonsFromCache(): PersonsModelList
    fun personsToCache(persons: PersonsModelList)
}

class PersonLocalDataSourceImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : PersonLocalDataSource {

    override suspend fun getLastPersonsFromCache(): PersonsModelList {
        val stringPersons: String =
            sharedPreferences.getString(Constants.CASHED_PERSONS_LIST, "") ?: ""

        if (stringPersons.isNotEmpty()) {
            val type = object : TypeToken<PersonsModelList>() {}.type

            return gson.fromJson(stringPersons, type)
        } else {
            throw CacheException()
        }
    }

    override fun personsToCache(persons: PersonsModelList) {
        try {
            val json: String = gson.toJson(persons)

            sharedPreferences.edit().putString(Constants.CASHED_PERSONS_LIST, json).apply()

        } catch (e: CacheException) {
            throw CacheException()
        }
    }
}
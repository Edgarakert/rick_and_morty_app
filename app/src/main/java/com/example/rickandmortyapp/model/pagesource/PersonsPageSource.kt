package com.example.rickandmortyapp.model.pagesource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortyapp.model.entity.PersonModel
import com.example.rickandmortyapp.model.repository.Repository
import com.example.rickandmortyapp.utils.error.CacheException
import com.example.rickandmortyapp.utils.error.ServerException

class PersonsPageSource(
    private val repository: Repository,
    private val query: String? = null,
    private val isConnected: Boolean
) :
    PagingSource<Int, PersonModel>() {
    override fun getRefreshKey(state: PagingState<Int, PersonModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PersonModel> {
        val page: Int = params.key ?: 1
        val pageSize: Int = 20

        if (isConnected) {
            val response = repository.getPersons(page, query)
            if (response.isSuccessful) {
                val personsResponse = checkNotNull(response.body())

                val persons = personsResponse.result
                val nextKey = if (persons.size < pageSize) null else page + 1
                val prevKey = if (page == 1) null else page - 1

                if (page == 1)
                    repository.personsToCache(personsResponse)
                return LoadResult.Page(persons, prevKey, nextKey)
            } else {
                return LoadResult.Error(ServerException())
            }
        } else {
            try {
                if (query != null)
                    throw CacheException()

                val personsCache = repository.getPersonsFromCache().result

                val nextKey = null
                val prevKey = null

                return LoadResult.Page(personsCache, prevKey, nextKey)
            } catch (e: CacheException) {
                return LoadResult.Error(CacheException())
            }
        }
    }
}
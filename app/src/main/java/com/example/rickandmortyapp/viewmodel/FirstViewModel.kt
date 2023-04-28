package com.example.rickandmortyapp.viewmodel

import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.rickandmortyapp.model.pagesource.PersonsPageSource
import com.example.rickandmortyapp.model.repository.Repository
import com.example.rickandmortyapp.utils.error.CacheException
import com.example.rickandmortyapp.utils.error.ServerException
import com.example.rickandmortyapp.view.recyclerview.PersonsPagingAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("DEPRECATION")
class FirstViewModel @Inject constructor(
    private val repository: Repository,
    private val connectivityManager: ConnectivityManager
) : ViewModel() {

    fun getPersonsList(query: String?, pagingAdapter: PersonsPagingAdapter) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    Pager(
                        PagingConfig(pageSize = 20, enablePlaceholders = true)
                    ) {
                        PersonsPageSource(repository, query, isConnected())
                    }.flow.cachedIn(viewModelScope).collectLatest { pagingData ->
                        pagingAdapter.submitData(pagingData)
                    }
                } catch (e: ServerException) {
                    throw ServerException()
                } catch (e: CacheException) {
                    throw CacheException()
                }
            }

        } catch (e: ServerException) {
            throw ServerException()
        } catch (e: CacheException) {
            throw CacheException()
        }
    }

    private fun isConnected(): Boolean {
        return connectivityManager.activeNetworkInfo?.isConnected ?: false
    }
}
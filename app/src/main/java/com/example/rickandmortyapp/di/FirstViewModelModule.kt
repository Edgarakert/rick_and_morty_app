package com.example.rickandmortyapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortyapp.viewmodel.FirstViewModel
import com.example.rickandmortyapp.viewmodel.FirstViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("DEPRECATION")
@Module
abstract class FirstViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(FirstViewModel::class)
    internal abstract fun bindFirstViewModel(firstViewModel: FirstViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: FirstViewModelFactory): ViewModelProvider.Factory
}
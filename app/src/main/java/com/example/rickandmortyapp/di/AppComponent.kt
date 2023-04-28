package com.example.rickandmortyapp.di

import android.content.Context
import com.example.rickandmortyapp.view.fragments.FirstFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [FirstViewModelModule::class, LocalDatasourceModule::class, RemoteDatasourceModule::class, RepositoryModule::class, NetworkModule::class])
@Singleton
interface AppComponent {
    fun injectFirstFragment(fragment: FirstFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}

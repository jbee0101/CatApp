package com.example.catapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Custom annotation to distinguish the Main Dispatcher.
 * This helps in providing different types of dispatchers in the app without confusion.
 */
@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher

/**
 * Hilt module that provides Coroutine Dispatchers.
 * This module ensures that the correct dispatcher is injected wherever needed.
 */
@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    /**
     * Provides the Main Coroutine Dispatcher.
     * The Main dispatcher is used for UI-related tasks and should be injected into ViewModels.
     *
     * @return Dispatchers.Main - The main thread dispatcher for UI operations.
     */
    @MainDispatcher
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}

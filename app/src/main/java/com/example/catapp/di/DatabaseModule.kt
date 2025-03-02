package com.example.catapp.di

import android.content.Context
import androidx.room.Room
import com.example.catapp.data.local.CatDao
import com.example.catapp.data.local.CatDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger module to provide instances related to the database
 * The module will be installed in the SingletonComponent for app-wide singletons
 */

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provides an instance of [CatDatabase].
     *
     * @param context The application context needed to initialize the database.
     * @return An instance of [CatDatabase] representing the local Room database.
     */
    @Provides
    @Singleton
    fun provideCatDatabase(@ApplicationContext context: Context): CatDatabase {
        return Room.databaseBuilder(
            context,
            CatDatabase::class.java,
            "cat_database"
        ).build()
    }

    /**
     * Provides an instance of [CatDao].
     *
     * @param catDatabase The database instance from which the DAO will be provided.
     * @return An instance of [CatDao], which is used to interact with the cat-related data in the database.
     */
    @Provides
    @Singleton
    fun provideCatDao(catDatabase: CatDatabase): CatDao {
        return catDatabase.catDao()
    }
}

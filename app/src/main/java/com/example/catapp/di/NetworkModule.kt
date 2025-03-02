package com.example.catapp.di

import com.example.catapp.data.remote.CatApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dagger module that provides the network-related dependencies (OkHttpClient, Retrofit, and API service)
 * The module will be installed in the SingletonComponent for app-wide singletons
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.thecatapi.com/v1/"
    private const val API_KEY = "live_RYp7ByEsJmRUaoRacMQ0WxYILtpopdfMBrKS1bkIhl0ABx8bIo87vkW9Pifa7xnb"

    /**
     * Provides a singleton instance of OkHttpClient.
     * Adds an interceptor to include the API key in the request headers.
     *
     * @return A configured [OkHttpClient] instance.
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .addHeader("x-api-key", API_KEY)
                .build()
            chain.proceed(newRequest)
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    /**
     * Provides a singleton instance of Retrofit.
     * Configures Retrofit to use the provided OkHttpClient and GsonConverterFactory for parsing JSON.
     *
     * @param okHttpClient The OkHttpClient instance to be used by Retrofit.
     * @return A configured [Retrofit] instance.
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    /**
     * Provides a singleton instance of the CatApiService.
     * Creates the API service using the Retrofit instance.
     *
     * @param retrofit The Retrofit instance to create the API service.
     * @return A [CatApiService] instance for making API calls.
     */
    @Provides
    @Singleton
    fun provideCatApiService(retrofit: Retrofit): CatApiService {
        return retrofit.create(CatApiService::class.java)
    }
}
package com.example.starwars.data.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo.cache.normalized.normalizedCache
import com.example.starwars.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(BuildConfig.BASE_URL)
            .normalizedCache(MemoryCacheFactory(maxSizeBytes = 5 * 1024 * 1024))
            .build()
    }
}
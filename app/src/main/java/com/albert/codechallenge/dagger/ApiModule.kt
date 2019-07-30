package com.albert.codechallenge.dagger

import com.albert.codechallenge.api.SearchService
import javax.inject.Singleton
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class ApiModule {
    @Provides
    @Singleton
    fun providesBasicInterface(retrofit: Retrofit): SearchService {
        return retrofit.create(SearchService::class.java)
    }
}

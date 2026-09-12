package br.com.furlaneto.murilo.shoplite.di

import br.com.furlaneto.murilo.shoplite.core.api.ApiClient
import br.com.furlaneto.murilo.shoplite.data.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {

    private const val BASE_URL = "https://fakestoreapi.com/"

    @Provides
    @Singleton
    fun provideApiClient(): ApiClient {
        return ApiClient(BASE_URL)
    }

    @Provides
    @Singleton
    fun provideApiService(apiClient: ApiClient): ApiService {
        return apiClient.create(ApiService::class.java)
    }
}

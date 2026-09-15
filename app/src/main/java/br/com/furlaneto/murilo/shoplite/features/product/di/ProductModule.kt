package br.com.furlaneto.murilo.shoplite.features.product.di

import br.com.furlaneto.murilo.shoplite.features.product.data.repository.ProductsRepository
import br.com.furlaneto.murilo.shoplite.features.product.data.repository.ProductsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductModule {

    @Binds
    @Singleton
    abstract fun bindProductsRepository(
        productsRepositoryImpl: ProductsRepositoryImpl
    ): ProductsRepository
}

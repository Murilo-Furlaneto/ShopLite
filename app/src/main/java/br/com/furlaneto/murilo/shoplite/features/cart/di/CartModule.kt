package br.com.furlaneto.murilo.shoplite.features.cart.di

import br.com.furlaneto.murilo.shoplite.features.cart.data.repository.CartRepositoryImpl
import br.com.furlaneto.murilo.shoplite.features.cart.domain.repository.CartRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CartModule {

    @Binds
    @Singleton
    abstract fun bindCartRepository(
        cartRepositoryImpl: CartRepositoryImpl
    ): CartRepository
}

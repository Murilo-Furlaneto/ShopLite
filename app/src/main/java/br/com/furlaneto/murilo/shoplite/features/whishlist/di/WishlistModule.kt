package br.com.furlaneto.murilo.shoplite.features.whishlist.di

import br.com.furlaneto.murilo.shoplite.features.whishlist.data.repository.WishlistRepositoryImpl
import br.com.furlaneto.murilo.shoplite.features.whishlist.domain.repository.WishlistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WishlistModule {

    @Binds
    @Singleton
    abstract fun bindWishlistRepository(
        wishlistRepositoryImpl: WishlistRepositoryImpl
    ): WishlistRepository
}

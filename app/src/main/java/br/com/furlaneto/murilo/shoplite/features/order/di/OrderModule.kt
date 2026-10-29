package br.com.furlaneto.murilo.shoplite.features.order.di

import br.com.furlaneto.murilo.shoplite.features.order.data.repository.OrderRepositoryImpl
import br.com.furlaneto.murilo.shoplite.features.order.domain.repository.OrderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OrderModule {

    @Binds
    @Singleton
    abstract fun bindOrderRepository(
        orderRepositoryImpl: OrderRepositoryImpl
    ): OrderRepository
}

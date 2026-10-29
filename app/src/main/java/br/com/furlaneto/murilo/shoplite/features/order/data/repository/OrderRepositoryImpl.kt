package br.com.furlaneto.murilo.shoplite.features.order.data.repository

import br.com.furlaneto.murilo.shoplite.features.order.data.local.dao.OrderDao
import br.com.furlaneto.murilo.shoplite.features.order.data.local.entity.toDomain
import br.com.furlaneto.murilo.shoplite.features.order.data.local.entity.toEntity
import br.com.furlaneto.murilo.shoplite.features.order.domain.entity.OrderSummary
import br.com.furlaneto.murilo.shoplite.features.order.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao
) : OrderRepository {

    override suspend fun saveOrder(order: OrderSummary) {
        orderDao.insertOrder(order.toEntity())
    }

    override fun observeOrders(): Flow<List<OrderSummary>> {
        return orderDao.getAllOrders().map { entities ->
            entities.map { it.toDomain() }
        }
    }
}

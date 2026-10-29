package br.com.furlaneto.murilo.shoplite.features.order.domain.repository

import br.com.furlaneto.murilo.shoplite.features.order.domain.entity.OrderSummary
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun saveOrder(order: OrderSummary)
    fun observeOrders(): Flow<List<OrderSummary>>
}

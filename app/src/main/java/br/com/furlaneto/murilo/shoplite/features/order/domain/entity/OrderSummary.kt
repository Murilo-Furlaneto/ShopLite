package br.com.furlaneto.murilo.shoplite.features.order.domain.entity

import br.com.furlaneto.murilo.shoplite.features.checkout.domain.entity.PaymentMethod

data class OrderSummary(
    val orderId: String = "",
    val totalAmount: Double,
    val paymentMethod: PaymentMethod,
    val itemsCount: Int = 1,
    val createdAtTimestamp: Long = System.currentTimeMillis()
)

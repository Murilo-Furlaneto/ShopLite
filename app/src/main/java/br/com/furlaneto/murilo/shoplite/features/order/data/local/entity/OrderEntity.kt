package br.com.furlaneto.murilo.shoplite.features.order.data.local.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import br.com.furlaneto.murilo.shoplite.features.checkout.domain.entity.PaymentMethod
import br.com.furlaneto.murilo.shoplite.features.order.domain.entity.OrderSummary

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey val orderId: String,
    val totalAmount: Double,
    val paymentMethod: String,
    val itemsCount: Int,
    val createdAtTimestamp: Long = System.currentTimeMillis()
)

fun OrderSummary.toEntity() = OrderEntity(
    orderId = orderId,
    totalAmount = totalAmount,
    paymentMethod = paymentMethod.name,
    itemsCount = itemsCount,
    createdAtTimestamp = createdAtTimestamp
)

fun OrderEntity.toDomain() = OrderSummary(
    orderId = orderId,
    totalAmount = totalAmount,
    paymentMethod = PaymentMethod.entries.find { it.name == paymentMethod } ?: PaymentMethod.PIX,
    itemsCount = itemsCount,
    createdAtTimestamp = createdAtTimestamp
)

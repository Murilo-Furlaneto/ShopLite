package br.com.furlaneto.murilo.shoplite.features.checkout.domain.entity

import br.com.furlaneto.murilo.shoplite.features.cart.domain.entity.CartItem

enum class PaymentMethod {
    PIX, CREDIT_CARD, BOLETO
}

data class ShippingAddress(
    val zipCode: String = "",
    val street: String = "",
    val number: String = "",
    val neighborhood: String = "",
    val city: String = "",
    val state: String = ""
)


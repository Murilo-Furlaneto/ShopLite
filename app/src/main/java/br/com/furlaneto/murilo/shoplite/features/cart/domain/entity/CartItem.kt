package br.com.furlaneto.murilo.shoplite.features.cart.domain.entity

import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.Product

data class CartItem(
    val product: Product,
    val quantity: Int = 1
) {
    val subtotal: Double get() = product.price * quantity
}

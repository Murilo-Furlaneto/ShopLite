package br.com.furlaneto.murilo.shoplite.features.cart.domain.repository

import br.com.furlaneto.murilo.shoplite.features.cart.domain.entity.CartItem
import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.Product
import kotlinx.coroutines.flow.StateFlow

interface CartRepository {
    val cartItems: StateFlow<List<CartItem>>
    fun addToCart(product: Product)
    fun removeFromCart(product: Product)
    fun incrementQuantity(product: Product)
    fun decrementQuantity(product: Product)
    fun clearCart()
}

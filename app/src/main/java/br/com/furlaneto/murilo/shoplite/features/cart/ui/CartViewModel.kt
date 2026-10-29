package br.com.furlaneto.murilo.shoplite.features.cart.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.furlaneto.murilo.shoplite.features.cart.domain.entity.CartItem
import br.com.furlaneto.murilo.shoplite.features.cart.domain.repository.CartRepository
import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface CartUiState {
    data object Loading : CartUiState
    data object Empty : CartUiState
    data class Success(
        val items: List<CartItem>,
        val totalPrice: Double,
        val totalQuantity: Int
    ) : CartUiState
}

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    val uiState: StateFlow<CartUiState> = cartRepository.cartItems
        .map { items ->
            if (items.isEmpty()) {
                CartUiState.Empty
            } else {
                val totalPrice = items.sumOf { it.subtotal }
                val totalQuantity = items.sumOf { it.quantity }
                CartUiState.Success(
                    items = items,
                    totalPrice = totalPrice,
                    totalQuantity = totalQuantity
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CartUiState.Loading
        )

    fun onIncrementQuantity(product: Product) {
        cartRepository.incrementQuantity(product)
    }

    fun onDecrementQuantity(product: Product) {
        cartRepository.decrementQuantity(product)
    }

    fun onRemoveItem(product: Product) {
        cartRepository.removeFromCart(product)
    }

    fun onClearCart() {
        cartRepository.clearCart()
    }
}

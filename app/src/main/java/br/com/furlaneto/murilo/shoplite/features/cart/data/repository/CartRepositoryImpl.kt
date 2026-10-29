package br.com.furlaneto.murilo.shoplite.features.cart.data.repository

import android.content.Context
import br.com.furlaneto.murilo.shoplite.core.worker.CartReminderScheduler
import br.com.furlaneto.murilo.shoplite.features.cart.data.local.dao.CartDao
import br.com.furlaneto.murilo.shoplite.features.cart.data.local.entity.toEntity
import br.com.furlaneto.murilo.shoplite.features.cart.domain.entity.CartItem
import br.com.furlaneto.murilo.shoplite.features.cart.domain.repository.CartRepository
import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.Product
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
    @ApplicationContext private val context: Context
) : CartRepository {

    private val repositoryScope = CoroutineScope(Dispatchers.IO)

    override val cartItems: StateFlow<List<CartItem>> = cartDao.getAllCartItems()
        .map { entities -> entities.map { it.toDomain() } }
        .stateIn(
            scope = repositoryScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    override fun addToCart(product: Product) {
        repositoryScope.launch {
            val existingItem = cartDao.getCartItemById(product.id)
            if (existingItem != null) {
                cartDao.insertOrUpdate(existingItem.copy(quantity = existingItem.quantity + 1))
            } else {
                cartDao.insertOrUpdate(product.toEntity(quantity = 1))
            }
            CartReminderScheduler.scheduleCartReminder(context)
        }
    }

    override fun removeFromCart(product: Product) {
        repositoryScope.launch {
            cartDao.deleteByProductId(product.id)
            val updatedItems = cartDao.getCartItemById(product.id)
            if (updatedItems == null) {
                CartReminderScheduler.cancelCartReminder(context)
            }
        }
    }

    override fun incrementQuantity(product: Product) {
        addToCart(product)
    }

    override fun decrementQuantity(product: Product) {
        repositoryScope.launch {
            val existingItem = cartDao.getCartItemById(product.id)
            if (existingItem != null) {
                if (existingItem.quantity > 1) {
                    cartDao.insertOrUpdate(existingItem.copy(quantity = existingItem.quantity - 1))
                } else {
                    cartDao.deleteByProductId(product.id)
                }
            }
        }
    }

    override fun clearCart() {
        repositoryScope.launch {
            cartDao.clearCart()
            CartReminderScheduler.cancelCartReminder(context)
        }
    }
}

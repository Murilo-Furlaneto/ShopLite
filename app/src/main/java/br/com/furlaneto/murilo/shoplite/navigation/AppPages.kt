package br.com.furlaneto.murilo.shoplite.navigation

import androidx.navigation3.runtime.NavKey
import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.Product
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute : NavKey

@Serializable
data object HomeRoute : AppRoute

@Serializable
data object CartRoute : AppRoute

@Serializable
data object WishlistRoute : AppRoute

@Serializable
data object ProfileRoute : AppRoute

@Serializable
data class DetailsRoute(
    val product: Product
) : AppRoute

@Serializable
data object CheckoutRoute : AppRoute

@Serializable
data class OrderSuccessRoute(
    val orderId: String,
    val totalAmount: Double
) : AppRoute

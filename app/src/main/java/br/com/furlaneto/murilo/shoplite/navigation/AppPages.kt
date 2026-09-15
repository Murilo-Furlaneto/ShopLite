package br.com.furlaneto.murilo.shoplite.navigation

import androidx.navigation3.runtime.NavKey
import br.com.furlaneto.murilo.shoplite.features.product.domain.Product
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute : NavKey

@Serializable
object HomeRoute : AppRoute

@Serializable
object CartRoute : AppRoute

@Serializable
object ProfileRoute : AppRoute

@Serializable
data class DetailsRoute(
    val product: Product
) : AppRoute

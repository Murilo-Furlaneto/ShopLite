package br.com.furlaneto.murilo.shoplite.route

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import androidx.navigation3.runtime.NavKey
import br.com.furlaneto.murilo.shoplite.domain.entity.Product
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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


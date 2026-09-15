package br.com.furlaneto.murilo.shoplite.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import br.com.furlaneto.murilo.shoplite.features.product.ui.details.DetailScreen
import br.com.furlaneto.murilo.shoplite.features.product.ui.home.HomeScreen
import br.com.furlaneto.murilo.shoplite.features.product.ui.home.ProductViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Routes() {
    val backStack = rememberNavBackStack(HomeRoute)

    SharedTransitionLayout {
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            sharedTransitionScope = this,
            transitionSpec = {
                (fadeIn(animationSpec = tween(400)) + slideInHorizontally(
                    initialOffsetX = { 400 },
                    animationSpec = tween(400)
                )) togetherWith (fadeOut(animationSpec = tween(400)) + slideOutHorizontally(
                    targetOffsetX = { -400 },
                    animationSpec = tween(400)
                ))
            },
            popTransitionSpec = {
                (fadeIn(animationSpec = tween(400)) + slideInHorizontally(
                    initialOffsetX = { -400 },
                    animationSpec = tween(400)
                )) togetherWith (fadeOut(animationSpec = tween(400)) + slideOutHorizontally(
                    targetOffsetX = { 400 },
                    animationSpec = tween(400)
                ))
            },
            entryProvider = entryProvider {
                entry<HomeRoute> {
                    val viewModel: ProductViewModel = hiltViewModel()
                    HomeScreen(
                        productViewModel = viewModel,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        onProductClick = { product ->
                            backStack.add(DetailsRoute(product))
                        }
                    )
                }

                entry<CartRoute> {
                    // ainda não existe
                }

                entry<DetailsRoute> { route ->
                    DetailScreen(
                        product = route.product,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        onBackClick = { backStack.removeLastOrNull() },
                        onAddToCartClick = {}
                    )
                }
            }
        )
    }
}

package br.com.furlaneto.murilo.shoplite.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import br.com.furlaneto.murilo.shoplite.features.cart.ui.CartScreen
import br.com.furlaneto.murilo.shoplite.features.cart.ui.CartViewModel
import br.com.furlaneto.murilo.shoplite.features.checkout.ui.CheckoutScreen
import br.com.furlaneto.murilo.shoplite.features.checkout.ui.OrderSuccessScreen
import br.com.furlaneto.murilo.shoplite.features.order.ui.OrderViewModel
import br.com.furlaneto.murilo.shoplite.features.product.ui.details.DetailScreen
import br.com.furlaneto.murilo.shoplite.features.product.ui.home.HomeScreen
import br.com.furlaneto.murilo.shoplite.features.user.ui.ProfileScreen
import br.com.furlaneto.murilo.shoplite.features.whishlist.ui.WishlistScreen
import br.com.furlaneto.murilo.shoplite.features.whishlist.ui.WishlistViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Routes() {
    val backStack = rememberNavBackStack(HomeRoute)

    SharedTransitionLayout {
        val sharedTransitionScope = this
        AnimatedContent(
            targetState = backStack.lastOrNull(),
            label = "shared_element_anim"
        ) { _ ->
            val animatedVisibilityScope = this
            NavDisplay(
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                modifier = Modifier.fillMaxSize(),
                entryProvider = entryProvider {
                    entry<HomeRoute> {
                        HomeScreen(
                            sharedTransitionScope = sharedTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope,
                            onProductClick = { product ->
                                backStack.add(DetailsRoute(product))
                            },
                            onCartClick = {
                                backStack.add(CartRoute)
                            },
                            onWishlistClick = {
                                backStack.add(WishlistRoute)
                            },
                            onProfileClick = {
                                backStack.add(ProfileRoute)
                            }
                        )
                    }

                    entry<ProfileRoute> {
                        ProfileScreen(
                            onBackClick = { backStack.removeLastOrNull() },
                            onCartClick = { backStack.add(CartRoute) },
                            onWishlistClick = { backStack.add(WishlistRoute) }
                        )
                    }

                    entry<CartRoute> {
                        CartScreen(
                            onBackClick = { backStack.removeLastOrNull() },
                            onExploreProductsClick = { backStack.removeLastOrNull() },
                            onCheckoutClick = { backStack.add(CheckoutRoute) }
                        )
                    }

                    entry<WishlistRoute> {
                        WishlistScreen(
                            onBackClick = { backStack.removeLastOrNull() },
                            onProductClick = { product ->
                                backStack.add(DetailsRoute(product))
                            },
                            onExploreProductsClick = { backStack.removeLastOrNull() }
                        )
                    }

                    entry<DetailsRoute> { route ->
                        val cartViewModel: CartViewModel = hiltViewModel()
                        val wishlistViewModel: WishlistViewModel = hiltViewModel()
                        val favoriteIds by wishlistViewModel.favoriteIds.collectAsState()

                        DetailScreen(
                            product = route.product,
                            isFavorite = favoriteIds.contains(route.product.id),
                            sharedTransitionScope = sharedTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope,
                            onBackClick = { backStack.removeLastOrNull() },
                            onAddToCartClick = { product ->
                                cartViewModel.onIncrementQuantity(product)
                            },
                            onNavigateToCart = {
                                backStack.add(CartRoute)
                            },
                            onToggleFavorite = { product ->
                                wishlistViewModel.onToggleFavorite(product.id)
                            }
                        )
                    }

                    entry<CheckoutRoute> {
                        val orderViewModel: OrderViewModel = hiltViewModel()
                        CheckoutScreen(
                            onBackClick = { backStack.removeLastOrNull() },
                            onOrderConfirmedClick = { orderSummary ->
                                orderViewModel.saveOrder(orderSummary)
                                backStack.add(OrderSuccessRoute(orderSummary.orderId, orderSummary.totalAmount))
                            }
                        )
                    }

                    entry<OrderSuccessRoute> { route ->
                        OrderSuccessScreen(
                            orderId = route.orderId,
                            totalAmount = route.totalAmount,
                            onBackToHomeClick = {
                                while (backStack.size > 1) {
                                    backStack.removeLastOrNull()
                                }
                            }
                        )
                    }
                }
            )
        }
    }
}

package br.com.furlaneto.murilo.shoplite.features.whishlist.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface WishlistRepository {
    val favoriteProductIds: StateFlow<Set<Int>>
    fun toggleFavorite(productId: Int)
    fun isFavorite(productId: Int): Boolean
}
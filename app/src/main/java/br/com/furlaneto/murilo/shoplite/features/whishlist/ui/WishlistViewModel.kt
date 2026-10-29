package br.com.furlaneto.murilo.shoplite.features.whishlist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.Product
import br.com.furlaneto.murilo.shoplite.features.product.domain.repository.ProductsRepository
import br.com.furlaneto.murilo.shoplite.features.whishlist.domain.repository.WishlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface WishlistUiState {
    data object Loading : WishlistUiState
    data object Empty : WishlistUiState
    data class Success(
        val favoriteProducts: List<Product>
    ) : WishlistUiState
}

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val wishlistRepository: WishlistRepository,
    private val productsRepository: ProductsRepository
) : ViewModel() {

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())

    val uiState: StateFlow<WishlistUiState> = combine(
        _allProducts,
        wishlistRepository.favoriteProductIds
    ) { products, favoriteIds ->
        val favorites = products.filter { favoriteIds.contains(it.id) }
        if (favorites.isEmpty()) {
            WishlistUiState.Empty
        } else {
            WishlistUiState.Success(favoriteProducts = favorites)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = WishlistUiState.Loading
    )

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            val products = productsRepository.fetchProducts()
            _allProducts.value = products
        }
    }

    fun onToggleFavorite(productId: Int) {
        wishlistRepository.toggleFavorite(productId)
    }

    val favoriteIds: StateFlow<Set<Int>> = wishlistRepository.favoriteProductIds
}

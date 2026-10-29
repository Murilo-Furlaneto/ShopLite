package br.com.furlaneto.murilo.shoplite.features.product.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.Product
import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.ProductCategory
import br.com.furlaneto.murilo.shoplite.features.product.domain.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

sealed class ProductUiState {
    data object Initial : ProductUiState()
    data object Loading : ProductUiState()
    data class Success(
        val products: List<Product>,
        val selectedCategory: ProductCategory? = null
    ) : ProductUiState()

    data class Error(val message: String) : ProductUiState()
}

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductsRepository,
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    private val _selectedCategory = MutableStateFlow<ProductCategory?>(null)
    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)

    private  val _filter = MutableStateFlow<ProductFilter>(ProductFilter())
    val filter: StateFlow<ProductFilter> = _filter.asStateFlow()

    val currentFilter: StateFlow<ProductFilter?> = _filter.asStateFlow()

    val searchQuery: StateFlow<String> = _filter.map { it.searchQuery }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ""
    )

    val uiState: StateFlow<ProductUiState> = combine(
        _products,
        _filter,
        _isLoading,
        _errorMessage
    ) { products, filter, isLoading, error ->
        when {
            error != null -> ProductUiState.Error(error)
            isLoading -> ProductUiState.Loading
            else -> {
                val filteredList = filterAndSortProducts(products, filter)
                ProductUiState.Success(filteredList, filter.category)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProductUiState.Loading
    )
    init {
        Timber.d("ViewModel inicializado - iniciando busca de produtos")
        fetchProducts()
    }

    fun selectCategory(category: ProductCategory?) {
        _filter.value = _filter.value.copy(category = category)
    }

    fun onSearchQueryChanged(query: String) {
        _filter.value = _filter.value.copy(searchQuery = query)
    }

    fun applyFilter(newFilter: ProductFilter) {
        _filter.value = newFilter
    }

    fun clearFilter() {
        _filter.value = ProductFilter()
    }

    fun fetchProducts() {
        if (_isLoading.value) return
        
        Timber.d("Iniciando busca de produtos")
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            try {
                val result = repository.fetchProducts()
                _products.value = result
            } catch (e: Exception) {
                Timber.e(e, "Erro ao buscar produtos")
                _errorMessage.value = "Erro ao buscar os produtos: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun filterAndSortProducts(products: List<Product>, filter: ProductFilter): List<Product> {
        var result = products
            .filter { filter.category == null || it.category == filter.category }
            .filter { it.title.contains(filter.searchQuery, ignoreCase = true) }
            .filter { filter.maxPrice == 0.0 || it.price in filter.minPrice..filter.maxPrice }

        result = when (filter.sortBy) {
            SortOrder.PRICE_ASC -> result.sortedBy { it.price }
            SortOrder.PRICE_DESC -> result.sortedByDescending { it.price }
            SortOrder.RATING_DESC -> result.sortedByDescending { it.rating.rate }
            SortOrder.NONE -> result
        }

        return result
    }
}

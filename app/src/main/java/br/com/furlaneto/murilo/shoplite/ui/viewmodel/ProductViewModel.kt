package br.com.furlaneto.murilo.shoplite.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.furlaneto.murilo.shoplite.data.repository.ProductsRepository
import br.com.furlaneto.murilo.shoplite.domain.entity.Product
import br.com.furlaneto.murilo.shoplite.domain.entity.ProductCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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

    val selectedCategory: StateFlow<ProductCategory?> = _selectedCategory.asStateFlow()

    val uiState: StateFlow<ProductUiState> = combine(
        _products,
        _selectedCategory,
        _isLoading,
        _errorMessage
    ) { products, category, isLoading, error ->
        when {
            error != null -> ProductUiState.Error(error)
            isLoading -> ProductUiState.Loading
            products.isEmpty() -> ProductUiState.Initial
            else -> {
                val filteredProducts = if (category == null) {
                    products
                } else {
                    products.filter { it.category == category }
                }
                ProductUiState.Success(filteredProducts, category)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProductUiState.Initial
    )

    init {
        fetchProducts()
    }

    fun selectCategory(category: ProductCategory?) {
        _selectedCategory.value = category
    }

    fun fetchProducts() {
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
}

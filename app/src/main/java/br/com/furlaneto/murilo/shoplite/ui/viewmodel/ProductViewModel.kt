package br.com.furlaneto.murilo.shoplite.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.furlaneto.murilo.shoplite.data.repository.ProductsRepository
import br.com.furlaneto.murilo.shoplite.domain.entity.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

sealed class ProductUiState {
    data object Initial: ProductUiState()
    data object Loading: ProductUiState()
    data class Success(val products: List<Product>) : ProductUiState()
    data class Error(val message: String) : ProductUiState()
}

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductsRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Initial)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()
    init {
        fetchProducts()
    }

    fun fetchProducts() {
        Timber.d("Iniciando busca de produtos")
        _uiState.value = ProductUiState.Loading
        viewModelScope.launch {
            try {
                val result = repository.fetchProducts()
                _products.value = result
                _uiState.value = ProductUiState.Success(result)
            } catch (e: Exception) {
                Timber.e(e, "Erro ao buscar produtos")
                _uiState.value = ProductUiState.Error("Erro ao buscar os produtos: ${e.message}")
            }
        }
    }
}
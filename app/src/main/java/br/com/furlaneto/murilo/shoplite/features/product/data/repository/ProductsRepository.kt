package br.com.furlaneto.murilo.shoplite.features.product.data.repository

import br.com.furlaneto.murilo.shoplite.features.product.domain.Product

interface ProductsRepository {
    suspend fun fetchProducts(): List<Product>
}

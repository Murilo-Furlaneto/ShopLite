package br.com.furlaneto.murilo.shoplite.features.product.domain.repository

import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.Product

interface ProductsRepository {
    suspend fun fetchProducts(): List<Product>
}

package br.com.furlaneto.murilo.shoplite.data.repository

import br.com.furlaneto.murilo.shoplite.domain.entity.Product

interface ProductsRepository
 {
    suspend fun fetchProducts(): List<Product>
}
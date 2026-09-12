package br.com.furlaneto.murilo.shoplite.data.service

import br.com.furlaneto.murilo.shoplite.domain.entity.Product
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun fetchProducts() : List<Product>
}
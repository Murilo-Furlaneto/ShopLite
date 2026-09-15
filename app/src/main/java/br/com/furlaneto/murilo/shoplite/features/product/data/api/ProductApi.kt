package br.com.furlaneto.murilo.shoplite.features.product.data.api

import br.com.furlaneto.murilo.shoplite.features.product.domain.Product
import retrofit2.http.GET

interface ProductApi {
    @GET("products")
    suspend fun fetchProducts() : List<Product>
}

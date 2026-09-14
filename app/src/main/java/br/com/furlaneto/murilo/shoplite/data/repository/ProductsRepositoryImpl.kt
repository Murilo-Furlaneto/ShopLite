package br.com.furlaneto.murilo.shoplite.data.repository

import br.com.furlaneto.murilo.shoplite.data.service.ApiService
import br.com.furlaneto.murilo.shoplite.domain.entity.Product
import timber.log.Timber
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProductsRepository {

    override suspend fun fetchProducts(): List<Product> {
        Timber.d("Iniciando busca de produtos na API")
        return try {
            val products = apiService.fetchProducts()
            Timber.i("Busca finalizada com sucesso. %d produtos encontrados", products.size)
            products
        } catch (e: Exception) {
            Timber.e(e, "Erro ao buscar produtos no repositório")
            throw e
        }
    }
}
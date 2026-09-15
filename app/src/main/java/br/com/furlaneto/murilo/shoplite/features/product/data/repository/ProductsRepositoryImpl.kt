package br.com.furlaneto.murilo.shoplite.features.product.data.repository

import br.com.furlaneto.murilo.shoplite.features.product.data.api.ProductApi
import br.com.furlaneto.murilo.shoplite.features.product.domain.Product
import timber.log.Timber
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val productApi: ProductApi
) : ProductsRepository {

    override suspend fun fetchProducts(): List<Product> {
        Timber.d("Iniciando busca de produtos na API")
        return try {
            val products = productApi.fetchProducts()
            Timber.i("Busca finalizada com sucesso. %d produtos encontrados", products.size)
            products
        } catch (e: Exception) {
            Timber.e(e, "Erro ao buscar produtos no repositório")
            throw e
        }
    }
}

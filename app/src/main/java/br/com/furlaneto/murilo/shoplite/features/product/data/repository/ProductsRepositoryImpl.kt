package br.com.furlaneto.murilo.shoplite.features.product.data.repository

import br.com.furlaneto.murilo.shoplite.features.product.data.api.ProductApi
import br.com.furlaneto.murilo.shoplite.features.product.data.local.dao.ProductDao
import br.com.furlaneto.murilo.shoplite.features.product.data.local.entity.toEntity
import br.com.furlaneto.murilo.shoplite.features.product.data.mock.MockProducts
import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.Product
import br.com.furlaneto.murilo.shoplite.features.product.domain.repository.ProductsRepository
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsRepositoryImpl @Inject constructor(
    private val productApi: ProductApi,
    private val productDao: ProductDao
) : ProductsRepository {

    override suspend fun fetchProducts(): List<Product> {
        // 1. Busca produtos existentes no cache do banco local Room
        val localEntities = productDao.getProductsList()
        val localProducts = localEntities.map { it.toDomain() }

        return try {
            // 2. Faz a chamada na API em paralelo/background
            Timber.d("Buscando produtos atualizados na API")
            val remoteProducts = productApi.fetchProducts()

            // 3. Compara o resultado da API com o cache local
            if (remoteProducts != localProducts) {
                Timber.i("Dados da API divergentes do cache. Atualizando o banco local.")
                val remoteEntities = remoteProducts.map { it.toEntity() }
                productDao.deleteAllProducts()
                productDao.insertAll(remoteEntities)
                remoteProducts
            } else {
                Timber.i("Dados da API são idênticos ao cache local. Nenhuma alteração necessária.")
                localProducts
            }
        } catch (e: Exception) {
            Timber.w(e, "Falha na requisição da API. Utilizando dados do banco local/fallback.")
                localProducts
            }

    }
}

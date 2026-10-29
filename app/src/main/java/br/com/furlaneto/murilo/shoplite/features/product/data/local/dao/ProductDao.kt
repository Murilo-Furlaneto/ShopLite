package br.com.furlaneto.murilo.shoplite.features.product.data.local.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import br.com.furlaneto.murilo.shoplite.features.product.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM product")
    suspend fun getProductsList(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Query("DELETE FROM product")
    suspend fun deleteAllProducts()

    @Query("SELECT * FROM product WHERE id = :id")
    suspend fun getProductById(id: Int): ProductEntity?

    @Query("SELECT * FROM product WHERE category = :category")
    suspend fun getProductsByCategory(category: String): List<ProductEntity>

    @Query("SELECT * FROM product WHERE title LIKE '%' || :query || '%'")
    suspend fun searchProducts(query: String): List<ProductEntity>
}

package br.com.furlaneto.murilo.shoplite.features.whishlist.data.repository.local.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import br.com.furlaneto.murilo.shoplite.features.whishlist.data.repository.local.entity.WishlistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistDao {

    @Query("SELECT productId FROM wishlist_items")
    fun getAllFavoriteProductIds(): Flow<List<Int>>

    @Query("SELECT EXISTS(SELECT 1 FROM wishlist_items WHERE productId = :productId)")
    suspend fun isFavorite(productId: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(entity: WishlistEntity)

    @Query("DELETE FROM wishlist_items WHERE productId = :productId")
    suspend fun removeFavorite(productId: Int)
}

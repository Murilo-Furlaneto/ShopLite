package br.com.furlaneto.murilo.shoplite.core.database

import androidx.room3.Database
import androidx.room3.RoomDatabase
import br.com.furlaneto.murilo.shoplite.features.cart.data.local.dao.CartDao
import br.com.furlaneto.murilo.shoplite.features.cart.data.local.entity.CartItemEntity
import br.com.furlaneto.murilo.shoplite.features.order.data.local.dao.OrderDao
import br.com.furlaneto.murilo.shoplite.features.order.data.local.entity.OrderEntity
import br.com.furlaneto.murilo.shoplite.features.product.data.local.dao.ProductDao
import br.com.furlaneto.murilo.shoplite.features.product.data.local.entity.ProductEntity
import br.com.furlaneto.murilo.shoplite.features.whishlist.data.repository.local.dao.WishlistDao
import br.com.furlaneto.murilo.shoplite.features.whishlist.data.repository.local.entity.WishlistEntity

@Database(
    entities = [CartItemEntity::class, ProductEntity::class, WishlistEntity::class, OrderEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun productDao(): ProductDao
    abstract fun wishlistDao(): WishlistDao
    abstract  fun orderDao(): OrderDao
}

package br.com.furlaneto.murilo.shoplite.core.di

import android.content.Context
import androidx.room3.Room
import br.com.furlaneto.murilo.shoplite.core.database.AppDatabase
import br.com.furlaneto.murilo.shoplite.features.cart.data.local.dao.CartDao
import br.com.furlaneto.murilo.shoplite.features.order.data.local.dao.OrderDao
import br.com.furlaneto.murilo.shoplite.features.product.data.local.dao.ProductDao
import br.com.furlaneto.murilo.shoplite.features.whishlist.data.repository.local.dao.WishlistDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "shoplite_database.db"
        )
        .fallbackToDestructiveMigration(dropAllTables = true)
        .build()
    }

    @Provides
    @Singleton
    fun provideCartDao(database: AppDatabase): CartDao {
        return database.cartDao()
    }

    @Provides
    @Singleton
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    @Singleton
    fun provideWishlistDao(database: AppDatabase): WishlistDao {
        return database.wishlistDao()
    }

    @Provides
    @Singleton
    fun provideOrderDao(database: AppDatabase): OrderDao {
        return database.orderDao()
    }
}

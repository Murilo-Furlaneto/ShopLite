package br.com.furlaneto.murilo.shoplite.features.whishlist.data.repository.local.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "wishlist_items")
data class WishlistEntity(
    @PrimaryKey val productId: Int
)

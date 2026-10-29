package br.com.furlaneto.murilo.shoplite.features.cart.data.local.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import br.com.furlaneto.murilo.shoplite.features.cart.domain.entity.CartItem
import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.Product
import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.ProductCategory
import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.Rating

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey val productId: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val ratingRate: Double,
    val ratingCount: Int,
    val quantity: Int
) {
    fun toDomain(): CartItem {
        return CartItem(
            product = Product(
                id = productId,
                title = title,
                price = price,
                description = description,
                category = ProductCategory.entries.find { it.name == category } ?: ProductCategory.MENS_CLOTHING,
                image = image,
                rating = Rating(rate = ratingRate, count = ratingCount)
            ),
            quantity = quantity
        )
    }
}

fun Product.toEntity(quantity: Int = 1): CartItemEntity {
    return CartItemEntity(
        productId = id,
        title = title,
        price = price,
        description = description,
        category = category.name,
        image = image,
        ratingRate = rating.rate,
        ratingCount = rating.count,
        quantity = quantity
    )
}

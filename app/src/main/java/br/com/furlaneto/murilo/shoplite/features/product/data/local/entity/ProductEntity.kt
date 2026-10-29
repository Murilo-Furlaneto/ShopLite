package br.com.furlaneto.murilo.shoplite.features.product.data.local.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.Product
import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.ProductCategory
import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.Rating

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val ratingRate: Double,
    val ratingCount: Int
) {
    fun toDomain(): Product {
        val parsedCategory = ProductCategory.entries.find { 
            it.name.equals(category, ignoreCase = true) || it.name == category 
        } ?: ProductCategory.MENS_CLOTHING

        return Product(
            id = id,
            title = title,
            price = price,
            description = description,
            category = parsedCategory,
            image = image,
            rating = Rating(rate = ratingRate, count = ratingCount)
        )
    }
}

fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category.name,
        image = image,
        ratingRate = rating.rate,
        ratingCount = rating.count
    )
}

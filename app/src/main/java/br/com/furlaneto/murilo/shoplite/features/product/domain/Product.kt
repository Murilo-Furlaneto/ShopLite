package br.com.furlaneto.murilo.shoplite.features.product.domain

import br.com.furlaneto.murilo.shoplite.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ProductCategory {
    @SerialName("men's clothing")
    MENS_CLOTHING,

    @SerialName("jewelery")
    JEWELERY,

    @SerialName("electronics")
    ELECTRONICS,

    @SerialName("women's clothing")
    WOMENS_CLOTHING;

    fun getDisplayNameResId(): Int {
        return when (this) {
            MENS_CLOTHING -> R.string.category_mens_clothing
            JEWELERY -> R.string.category_jewelery
            ELECTRONICS -> R.string.category_electronics
            WOMENS_CLOTHING -> R.string.category_womens_clothing
        }
    }
}

@Serializable
data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: ProductCategory,
    val image: String,
    val rating: Rating
)

@Serializable
data class Rating(
    val rate: Double,
    val count: Int,
)

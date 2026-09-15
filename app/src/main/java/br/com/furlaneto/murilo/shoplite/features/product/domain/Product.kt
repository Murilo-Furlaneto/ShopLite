package br.com.furlaneto.murilo.shoplite.features.product.domain

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

    fun toDisplayName(): String {
        return when (this) {
            MENS_CLOTHING -> "Moda Masculina"
            JEWELERY -> "Joias"
            ELECTRONICS -> "Eletrônicos"
            WOMENS_CLOTHING -> "Moda Feminina"
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

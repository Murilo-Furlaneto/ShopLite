package br.com.furlaneto.murilo.shoplite.features.product.ui.home

import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.ProductCategory

enum class SortOrder{
    NONE, PRICE_ASC, PRICE_DESC, RATING_DESC
}

data class ProductFilter(
    val minPrice: Double = 0.0,
    val maxPrice: Double = 0.0,
    val sortBy: SortOrder = SortOrder.NONE,
    val category: ProductCategory? = null,
    val searchQuery: String = ""
)

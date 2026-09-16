package br.com.furlaneto.murilo.shoplite.features.product.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.furlaneto.murilo.shoplite.R
import br.com.furlaneto.murilo.shoplite.core.colors.AppColors
import br.com.furlaneto.murilo.shoplite.features.product.domain.ProductCategory

@Composable
fun CategoryChips(
    selectedCategory: ProductCategory?,
    onCategoryClick: (ProductCategory?) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = ProductCategory.entries
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .padding(vertical = 8.dp)
            .horizontalScroll(scrollState)
            .padding(horizontal = 16.dp)
    ) {
        // Opção "Tudo"
        CategoryChip(
            label = stringResource(R.string.category_all),
            isSelected = selectedCategory == null,
            onClick = { onCategoryClick(null) }
        )

        categories.forEach { category ->
            CategoryChip(
                label = stringResource(category.getDisplayNameResId()),
                isSelected = selectedCategory == category,
                onClick = { onCategoryClick(category) }
            )
        }
    }
}

@Composable
private fun CategoryChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = { Text(label) },
        shape = RoundedCornerShape(50),
        modifier = Modifier.padding(end = 8.dp),
        colors = FilterChipDefaults.filterChipColors(
            containerColor = AppColors.BackgroundLight,
            labelColor = AppColors.UnselectedLabel,
            selectedContainerColor = AppColors.Primary,
            selectedLabelColor = AppColors.White
        ),
        border = null
    )
}

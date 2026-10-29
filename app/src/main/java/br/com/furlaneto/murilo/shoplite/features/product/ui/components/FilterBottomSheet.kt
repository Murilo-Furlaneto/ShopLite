package br.com.furlaneto.murilo.shoplite.features.product.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.furlaneto.murilo.shoplite.core.colors.AppColors
import br.com.furlaneto.murilo.shoplite.features.product.ui.home.ProductFilter
import br.com.furlaneto.murilo.shoplite.features.product.ui.home.SortOrder
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    currentFilter: ProductFilter,
    onDismissRequest: () -> Unit,
    onApplyFilter: (ProductFilter) -> Unit,
    onClearFilter: () -> Unit
) {
    var minPrice by remember { mutableFloatStateOf(currentFilter.minPrice.toFloat()) }
    var maxPrice by remember { mutableFloatStateOf(if (currentFilter.maxPrice > 0) currentFilter.maxPrice.toFloat() else 500f) }
    var selectedSortOrder by remember { mutableStateOf(currentFilter.sortBy) }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        containerColor = AppColors.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filtrar e Ordenar",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.PrimaryText
                )

                TextButton(
                    onClick = {
                        minPrice = 0f
                        maxPrice = 500f
                        selectedSortOrder = SortOrder.NONE
                        onClearFilter()
                    }
                ) {
                    Text(
                        text = "Limpar",
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            HorizontalDivider(color = AppColors.Divider)

            // Seção 1: Faixa de Preço
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Faixa de Preço",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.PrimaryText
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = String.format(Locale.getDefault(), "R$ %.0f", minPrice),
                        fontWeight = FontWeight.SemiBold,
                        color = AppColors.Primary
                    )
                    Text(
                        text = String.format(Locale.getDefault(), "R$ %.0f", maxPrice),
                        fontWeight = FontWeight.SemiBold,
                        color = AppColors.Primary
                    )
                }

                RangeSlider(
                    value = minPrice..maxPrice,
                    onValueChange = { range ->
                        minPrice = range.start
                        maxPrice = range.endInclusive
                    },
                    valueRange = 0f..500f,
                    colors = SliderDefaults.colors(
                        thumbColor = AppColors.Primary,
                        activeTrackColor = AppColors.Primary,
                        inactiveTrackColor = AppColors.BackgroundLight
                    )
                )
            }

            HorizontalDivider(color = AppColors.Divider)

            // Seção 2: Ordenar por
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Ordenar por",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.PrimaryText
                )

                SortOptionItem(
                    title = "Padrão",
                    isSelected = selectedSortOrder == SortOrder.NONE,
                    onClick = { selectedSortOrder = SortOrder.NONE }
                )

                SortOptionItem(
                    title = "Menor Preço",
                    isSelected = selectedSortOrder == SortOrder.PRICE_ASC,
                    onClick = { selectedSortOrder = SortOrder.PRICE_ASC }
                )

                SortOptionItem(
                    title = "Maior Preço",
                    isSelected = selectedSortOrder == SortOrder.PRICE_DESC,
                    onClick = { selectedSortOrder = SortOrder.PRICE_DESC }
                )

                SortOptionItem(
                    title = "Melhor Avaliação",
                    isSelected = selectedSortOrder == SortOrder.RATING_DESC,
                    onClick = { selectedSortOrder = SortOrder.RATING_DESC }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    onApplyFilter(
                        ProductFilter(
                            minPrice = minPrice.toDouble(),
                            maxPrice = maxPrice.toDouble(),
                            sortBy = selectedSortOrder
                        )
                    )
                    onDismissRequest()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.Primary)
            ) {
                Text(
                    text = "Aplicar Filtros",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun SortOptionItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = AppColors.Primary)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) AppColors.Primary else AppColors.PrimaryText
        )
    }
}

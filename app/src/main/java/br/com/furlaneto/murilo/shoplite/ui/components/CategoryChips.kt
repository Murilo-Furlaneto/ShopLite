package br.com.furlaneto.murilo.shoplite.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import br.com.furlaneto.murilo.shoplite.core.colors.AppColors

@Composable
fun CategoryChips() {
    var selectedCategory by remember { mutableStateOf("Tudo") }

    Row(modifier = Modifier.padding(horizontal = 40.dp, vertical = 8.dp)) {
        listOf("Tudo", "Eletrônicos", "Moda", "Casa").forEach { category ->
            val isSelected = selectedCategory == category

            FilterChip(
                selected = isSelected,
                onClick = { selectedCategory = category },
                label = { Text(category) },
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
    }
}
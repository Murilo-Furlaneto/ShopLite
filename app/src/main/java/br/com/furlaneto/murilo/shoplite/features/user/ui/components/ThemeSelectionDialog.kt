package br.com.furlaneto.murilo.shoplite.features.user.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.furlaneto.murilo.shoplite.core.colors.AppColors
import br.com.furlaneto.murilo.shoplite.enum.ThemeModeApp

@Composable
fun ThemeSelectionDialog(
    currentThemeValue: String,
    onDismissRequest: () -> Unit,
    onThemeSelected: (ThemeModeApp) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        shape = RoundedCornerShape(20.dp),
        containerColor = AppColors.White,
        title = {
            Text(
                text = "Escolha o Tema",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = AppColors.PrimaryText
            )
        },
        text = {
            Column {
                ThemeOptionItem(
                    title = "☀️ Claro",
                    isSelected = currentThemeValue == ThemeModeApp.LIGHT.value,
                    onClick = { onThemeSelected(ThemeModeApp.LIGHT) }
                )

                ThemeOptionItem(
                    title = "🌙 Escuro",
                    isSelected = currentThemeValue == ThemeModeApp.DARK.value,
                    onClick = { onThemeSelected(ThemeModeApp.DARK) }
                )

                ThemeOptionItem(
                    title = "📱 Seguir Sistema",
                    isSelected = currentThemeValue == ThemeModeApp.SYSTEM.value,
                    onClick = { onThemeSelected(ThemeModeApp.SYSTEM) }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text(
                    text = "Cancelar",
                    color = AppColors.Primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}

@Composable
private fun ThemeOptionItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = AppColors.Primary)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) AppColors.Primary else AppColors.PrimaryText
        )
    }
}

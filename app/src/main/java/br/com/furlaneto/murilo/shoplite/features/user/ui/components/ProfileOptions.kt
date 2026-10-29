package br.com.furlaneto.murilo.shoplite.features.user.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.furlaneto.murilo.shoplite.core.colors.AppColors

@Composable
fun ProfileOptions(
    onCartClick: () -> Unit = {},
    onWishlistClick: () -> Unit = {},
    onAddressClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {},
    onDeliveryClick: () -> Unit = {},
    onThemeClick: () -> Unit = {},
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            ProfileMenuItem(
                icon = Icons.Default.ShoppingCart,
                title = "Meu Carrinho",
                onClick = onCartClick
            )

            HorizontalDivider(color = AppColors.Divider)

            ProfileMenuItem(
                icon = Icons.Default.Favorite,
                title = "Meus Favoritos",
                onClick = onWishlistClick
            )

            HorizontalDivider(color = AppColors.Divider)

            ProfileMenuItem(
                icon = Icons.Default.DarkMode,
                title = "Aparência / Tema",
                onClick = onThemeClick
            )

            HorizontalDivider(color = AppColors.Divider)

            ProfileMenuItem(
                icon = Icons.Default.LocationOn,
                title = "Endereços Salvos",
                onClick = onAddressClick,
            )

            HorizontalDivider(color = AppColors.Divider)

            ProfileMenuItem(
                icon = Icons.Default.Lock,
                title = "Privacidade e Segurança",
                onClick = onPrivacyClick,
            )

            HorizontalDivider(color = AppColors.Divider)

            ProfileMenuItem(
                icon = Icons.Default.DeliveryDining,
                title = "Meus Pedidos",
                onClick = onDeliveryClick,
            )
        }
    }
}

@Composable
private fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = AppColors.Primary,
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = AppColors.PrimaryText,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = AppColors.SecondaryText,
            modifier = Modifier.size(20.dp)
        )
    }
}

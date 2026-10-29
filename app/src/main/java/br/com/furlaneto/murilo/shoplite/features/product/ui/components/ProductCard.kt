package br.com.furlaneto.murilo.shoplite.features.product.ui.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.furlaneto.murilo.shoplite.core.colors.AppColors
import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.Product
import coil3.compose.AsyncImage
import java.util.Locale

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
    onToggleFavorite: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .width(180.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(AppColors.BackgroundImage),
                contentAlignment = Alignment.Center
            ) {
                val imageModifier = if (sharedTransitionScope != null && animatedVisibilityScope != null) {
                    with(sharedTransitionScope) {
                        Modifier.sharedElement(
                            rememberSharedContentState(key = "product-image-${product.id}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    }
                } else {
                    Modifier
                }

                AsyncImage(
                    model = product.image,
                    contentDescription = product.title,
                    modifier = imageModifier.fillMaxSize(0.8f),
                    contentScale = ContentScale.Fit
                )

                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.8f))
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favoritar produto",
                        tint = if (isFavorite) Color.Red else AppColors.SecondaryText,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(4.dp),
                color = AppColors.TagBackground
            ) {
                Text(
                    text = stringResource(product.category.getDisplayNameResId()).uppercase(),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TagText
                )
            }

            Text(
                text = product.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = AppColors.PrimaryText,
                maxLines = 2,
                lineHeight = 20.sp,
                modifier = Modifier.heightIn(min = 40.dp)
            )

            val formattedPrice = remember(product.price) {
                String.format(Locale.getDefault(), "R$ %.2f", product.price)
            }
            
            Text(
                text = formattedPrice,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.Primary
            )
        }
    }
}

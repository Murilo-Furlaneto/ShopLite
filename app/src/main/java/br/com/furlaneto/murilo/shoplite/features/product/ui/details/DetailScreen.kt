package br.com.furlaneto.murilo.shoplite.features.product.ui.details

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.furlaneto.murilo.shoplite.R
import br.com.furlaneto.murilo.shoplite.core.colors.AppColors
import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.Product
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun DetailScreen(
    product: Product,
    isFavorite: Boolean = false,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
    onBackClick: () -> Unit = {},
    onAddToCartClick: (Product) -> Unit = {},
    onNavigateToCart: () -> Unit = {},
    onToggleFavorite: (Product) -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val productAddedMessage = stringResource(R.string.product_added_to_cart)
    val viewCartActionLabel = stringResource(R.string.view_cart)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.details_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_content_description)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onToggleFavorite(product) }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favoritar produto",
                            tint = if (isFavorite) Color.Red else AppColors.SecondaryText
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(16.dp)
            ) { data ->
                Snackbar(
                    containerColor = AppColors.White,
                    contentColor = AppColors.PrimaryText,
                    shape = RoundedCornerShape(12.dp),
                    action = {
                        data.visuals.actionLabel?.let { actionLabel ->
                            TextButton(
                                onClick = { data.performAction() }
                            ) {
                                Text(
                                    text = actionLabel,
                                    color = AppColors.Primary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                ) {
                    Text(
                        text = data.visuals.message,
                        color = AppColors.PrimaryText,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Button(
                    onClick = {
                        onAddToCartClick(product)
                        coroutineScope.launch {
                            snackbarHostState.currentSnackbarData?.dismiss()
                            val result = snackbarHostState.showSnackbar(
                                message = productAddedMessage,
                                actionLabel = viewCartActionLabel,
                                duration = SnackbarDuration.Short
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                onNavigateToCart()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(16.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.Primary
                    )
                ) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        stringResource(R.string.add_to_cart),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
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
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = AppColors.TagBackground
                ) {
                    Text(
                        text = stringResource(product.category.getDisplayNameResId()).uppercase(),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.TagText
                    )
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    text = product.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.PrimaryText
                )

                Spacer(Modifier.height(8.dp))

                val formattedPrice = remember(product.price) {
                    String.format(Locale.getDefault(), "R$ %.2f", product.price)
                }

                Text(
                    text = formattedPrice,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = AppColors.Primary
                )

                Spacer(Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = AppColors.StarRating,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = product.rating.rate.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.PrimaryText
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.reviews_count, product.rating.count),
                        fontSize = 14.sp,
                        color = AppColors.SecondaryText
                    )
                }

                Spacer(Modifier.height(16.dp))

                HorizontalDivider(color = AppColors.Divider, thickness = 1.dp)

                Spacer(Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.description_label),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.PrimaryText
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = product.description,
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = AppColors.SecondaryText
                )
                
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

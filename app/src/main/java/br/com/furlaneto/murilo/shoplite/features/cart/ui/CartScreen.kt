package br.com.furlaneto.murilo.shoplite.features.cart.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.furlaneto.murilo.shoplite.R
import br.com.furlaneto.murilo.shoplite.core.colors.AppColors
import br.com.furlaneto.murilo.shoplite.features.cart.domain.entity.CartItem
import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.Product
import coil3.compose.AsyncImage
import java.util.Locale

@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    onExploreProductsClick: () -> Unit = {},
    onCheckoutClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    CartContent(
        uiState = uiState,
        onBackClick = onBackClick,
        onExploreProductsClick = onExploreProductsClick,
        onCheckoutClick = onCheckoutClick,
        onIncrementQuantity = { viewModel.onIncrementQuantity(it) },
        onDecrementQuantity = { viewModel.onDecrementQuantity(it) },
        onRemoveItem = { viewModel.onRemoveItem(it) },
        onClearCart = { viewModel.onClearCart() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(
    uiState: CartUiState,
    onBackClick: () -> Unit,
    onExploreProductsClick: () -> Unit,
    onCheckoutClick: () -> Unit,
    onIncrementQuantity: (Product) -> Unit,
    onDecrementQuantity: (Product) -> Unit,
    onRemoveItem: (Product) -> Unit,
    onClearCart: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.cart_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_content_description)
                        )
                    }
                },
                actions = {
                    if (uiState is CartUiState.Success) {
                        TextButton(onClick = onClearCart) {
                            Text(
                                text = stringResource(R.string.cart_clear_all),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (uiState is CartUiState.Success) {
                CartSummaryBottomBar(
                    totalPrice = uiState.totalPrice,
                    onCheckoutClick = onCheckoutClick
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (uiState) {
                is CartUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = AppColors.Primary)
                    }
                }

                is CartUiState.Empty -> {
                    CartEmptyState(onExploreProductsClick = onExploreProductsClick)
                }

                is CartUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = uiState.items,
                            key = { it.product.id }
                        ) { item ->
                            CartItemCard(
                                item = item,
                                onIncrement = { onIncrementQuantity(item.product) },
                                onDecrement = { onDecrementQuantity(item.product) },
                                onRemove = { onRemoveItem(item.product) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CartEmptyState(
    onExploreProductsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(AppColors.TagBackground),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                tint = AppColors.Primary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.cart_empty_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.PrimaryText
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.cart_empty_subtitle),
            fontSize = 14.sp,
            color = AppColors.SecondaryText
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onExploreProductsClick,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AppColors.Primary)
        ) {
            Text(stringResource(R.string.cart_explore_products))
        }
    }
}

@Composable
private fun CartItemCard(
    item: CartItem,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(AppColors.BackgroundImage),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = item.product.image,
                    contentDescription = item.product.title,
                    modifier = Modifier.fillMaxSize(0.8f),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.product.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.PrimaryText,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                val formattedPrice = remember(item.subtotal) {
                    String.format(Locale.getDefault(), "R$ %.2f", item.subtotal)
                }

                Text(
                    text = formattedPrice,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilledIconButton(
                        onClick = onDecrement,
                        modifier = Modifier.size(28.dp),
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = AppColors.BackgroundLight,
                            contentColor = AppColors.PrimaryText
                        )
                    ) {
                        Text("-", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }

                    Text(
                        text = item.quantity.toString(),
                        modifier = Modifier.padding(horizontal = 12.dp),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    FilledIconButton(
                        onClick = onIncrement,
                        modifier = Modifier.size(28.dp),
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = AppColors.Primary,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }

            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun CartSummaryBottomBar(
    totalPrice: Double,
    onCheckoutClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .navigationBarsPadding()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.cart_total),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.SecondaryText
                )

                val formattedTotal = remember(totalPrice) {
                    String.format(Locale.getDefault(), "R$ %.2f", totalPrice)
                }

                Text(
                    text = formattedTotal,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = AppColors.Primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onCheckoutClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.Primary)
            ) {
                Text(
                    text = stringResource(R.string.cart_checkout),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

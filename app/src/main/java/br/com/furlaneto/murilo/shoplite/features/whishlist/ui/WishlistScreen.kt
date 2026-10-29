package br.com.furlaneto.murilo.shoplite.features.whishlist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.furlaneto.murilo.shoplite.R
import br.com.furlaneto.murilo.shoplite.core.colors.AppColors
import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.Product
import br.com.furlaneto.murilo.shoplite.features.product.ui.components.ProductCard

@Composable
fun WishlistScreen(
    viewModel: WishlistViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    onProductClick: (Product) -> Unit = {},
    onExploreProductsClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val favoriteIds by viewModel.favoriteIds.collectAsState()

    WishlistContent(
        uiState = uiState,
        favoriteIds = favoriteIds,
        onBackClick = onBackClick,
        onProductClick = onProductClick,
        onExploreProductsClick = onExploreProductsClick,
        onToggleFavorite = { viewModel.onToggleFavorite(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistContent(
    uiState: WishlistUiState,
    favoriteIds: Set<Int>,
    onBackClick: () -> Unit,
    onProductClick: (Product) -> Unit,
    onExploreProductsClick: () -> Unit,
    onToggleFavorite: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Meus Favoritos", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_content_description)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (uiState) {
                is WishlistUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = AppColors.Primary)
                    }
                }

                is WishlistUiState.Empty -> {
                    WishlistEmptyState(onExploreProductsClick = onExploreProductsClick)
                }

                is WishlistUiState.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(uiState.favoriteProducts) { product ->
                            ProductCard(
                                product = product,
                                isFavorite = favoriteIds.contains(product.id),
                                onToggleFavorite = { onToggleFavorite(product.id) },
                                modifier = Modifier.padding(8.dp),
                                onClick = { onProductClick(product) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WishlistEmptyState(
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
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                tint = AppColors.Primary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Sua lista de favoritos está vazia",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.PrimaryText
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Explore os produtos do catálogo e clique no ícone de coração para salvá-los aqui.",
            fontSize = 14.sp,
            color = AppColors.SecondaryText
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onExploreProductsClick,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AppColors.Primary)
        ) {
            Text("Explorar Produtos")
        }
    }
}

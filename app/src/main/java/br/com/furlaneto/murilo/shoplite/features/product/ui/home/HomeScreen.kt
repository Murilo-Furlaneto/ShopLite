package br.com.furlaneto.murilo.shoplite.features.product.ui.home

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import br.com.furlaneto.murilo.shoplite.core.colors.AppColors
import br.com.furlaneto.murilo.shoplite.features.product.domain.Product
import br.com.furlaneto.murilo.shoplite.features.product.domain.ProductCategory
import br.com.furlaneto.murilo.shoplite.features.product.ui.components.CategoryChips
import br.com.furlaneto.murilo.shoplite.features.product.ui.components.ProductCard

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    productViewModel: ProductViewModel,
    sharedTransitionScope: SharedTransitionScope,
    onProductClick: (Product) -> Unit = {}
) {
    val uiState by productViewModel.uiState.collectAsState()
    val searchQuery by productViewModel.searchQuery.collectAsState()
    val animatedVisibilityScope = LocalNavAnimatedContentScope.current

    HomeContent(
        uiState = uiState,
        searchQuery = searchQuery,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
        onProductClick = onProductClick,
        onCategoryClick = { productViewModel.selectCategory(it) },
        onSearchQueryChange = { productViewModel.onSearchQueryChanged(it) },
        onRetryClick = { productViewModel.fetchProducts() }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun HomeContent(
    uiState: ProductUiState,
    searchQuery: String,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: androidx.compose.animation.AnimatedVisibilityScope,
    onProductClick: (Product) -> Unit,
    onCategoryClick: (ProductCategory?) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onRetryClick: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = { Text(text = "Buscar produtos...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Ícone de busca"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(28.dp),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedContainerColor = AppColors.BackgroundLight,
                    unfocusedContainerColor = AppColors.BackgroundLight,
                    disabledContainerColor = AppColors.BackgroundLight
                )
            )

            val selectedCategory = if (uiState is ProductUiState.Success) {
                uiState.selectedCategory
            } else null

            CategoryChips(
                selectedCategory = selectedCategory,
                onCategoryClick = onCategoryClick
            )

            Spacer(modifier = Modifier.height(8.dp))

            when (uiState) {
                is ProductUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = AppColors.Primary)
                    }
                }

                is ProductUiState.Success -> {
                    if (uiState.products.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = "Nenhum produto encontrado.")
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            items(uiState.products) { product ->
                                with(sharedTransitionScope) {
                                    ProductCard(
                                        product = product,
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        modifier = Modifier.padding(8.dp),
                                        onClick = { onProductClick(product) }
                                    )
                                }
                            }
                        }
                    }
                }

                is ProductUiState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = uiState.message, color = Color.Red, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onRetryClick) {
                            Text("Tentar novamente")
                        }
                    }
                }

                else -> {}
            }
        }
    }
}

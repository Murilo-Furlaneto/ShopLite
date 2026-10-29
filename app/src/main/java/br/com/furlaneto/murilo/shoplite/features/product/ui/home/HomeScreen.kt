package br.com.furlaneto.murilo.shoplite.features.product.ui.home

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.furlaneto.murilo.shoplite.R
import br.com.furlaneto.murilo.shoplite.core.colors.AppColors
import br.com.furlaneto.murilo.shoplite.features.cart.ui.CartUiState
import br.com.furlaneto.murilo.shoplite.features.cart.ui.CartViewModel
import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.Product
import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.ProductCategory
import br.com.furlaneto.murilo.shoplite.features.product.ui.components.CategoryChips
import br.com.furlaneto.murilo.shoplite.features.product.ui.components.FilterBottomSheet
import br.com.furlaneto.murilo.shoplite.features.product.ui.components.ProductCard
import br.com.furlaneto.murilo.shoplite.features.whishlist.ui.WishlistViewModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    productViewModel: ProductViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    wishlistViewModel: WishlistViewModel = hiltViewModel(),
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
    onProductClick: (Product) -> Unit = {},
    onCartClick: () -> Unit = {},
    onWishlistClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val uiState by productViewModel.uiState.collectAsState()
    val searchQuery by productViewModel.searchQuery.collectAsState()
    val cartUiState by cartViewModel.uiState.collectAsState()
    val favoriteIds by wishlistViewModel.favoriteIds.collectAsState()

    val totalCartItems = (cartUiState as? CartUiState.Success)?.totalQuantity ?: 0

    HomeContent(
        uiState = uiState,
        searchQuery = searchQuery,
        totalCartItems = totalCartItems,
        favoriteIds = favoriteIds,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
        onProductClick = onProductClick,
        onCartClick = onCartClick,
        onWishlistClick = onWishlistClick,
        onProfileClick = onProfileClick,
        onToggleFavorite = { wishlistViewModel.onToggleFavorite(it) },
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
    totalCartItems: Int,
    favoriteIds: Set<Int>,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
    onProductClick: (Product) -> Unit,
    onCartClick: () -> Unit,
    onWishlistClick: () -> Unit,
    onProfileClick: () -> Unit,
    onToggleFavorite: (Int) -> Unit,
    onCategoryClick: (ProductCategory?) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onRetryClick: () -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onProfileClick) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Meu Perfil"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onWishlistClick) {
                        BadgedBox(
                            badge = {
                                if (favoriteIds.isNotEmpty()) {
                                    Badge { Text(favoriteIds.size.toString()) }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Meus Favoritos"
                            )
                        }
                    }

                    IconButton(onClick = onCartClick) {
                        BadgedBox(
                            badge = {
                                if (totalCartItems > 0) {
                                    Badge { Text(totalCartItems.toString()) }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = stringResource(R.string.cart_title)
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                TextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    placeholder = { Text(text = stringResource(R.string.search_placeholder)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.search_placeholder)
                        )
                    },
                    modifier = Modifier.weight(1f),
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

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = {
                        showBottomSheet = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = stringResource(R.string.filter_title)
                    )
                }
            }

            if (showBottomSheet) {
                FilterBottomSheet(
                    currentFilter = ProductFilter(),
                    onDismissRequest = { showBottomSheet = false },
                    onApplyFilter = { _ ->
                        showBottomSheet = false
                    },
                    onClearFilter = {
                        showBottomSheet = false
                    }
                )
            }

            val selectedCategory = if (uiState is ProductUiState.Success) {
                uiState.selectedCategory
            } else null

            CategoryChips(
                selectedCategory = selectedCategory,
                onCategoryClick = onCategoryClick
            )

            Spacer(modifier = Modifier.height(8.dp))

            when (uiState) {
                is ProductUiState.Initial,
                is ProductUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = AppColors.Primary)
                    }
                }

                is ProductUiState.Success -> {
                    if (uiState.products.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = stringResource(R.string.no_products_found))
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            items(uiState.products) { product ->
                                ProductCard(
                                    product = product,
                                    isFavorite = favoriteIds.contains(product.id),
                                    sharedTransitionScope = sharedTransitionScope,
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    onToggleFavorite = { onToggleFavorite(product.id) },
                                    modifier = Modifier.padding(8.dp),
                                    onClick = { onProductClick(product) }
                                )
                            }
                        }
                    }
                }

                is ProductUiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = uiState.message, color = Color.Red, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onRetryClick) {
                            Text(stringResource(R.string.retry_button))
                        }
                    }
                }
            }
        }
    }
}

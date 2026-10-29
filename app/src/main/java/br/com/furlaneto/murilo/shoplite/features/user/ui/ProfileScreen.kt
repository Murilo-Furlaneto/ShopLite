package br.com.furlaneto.murilo.shoplite.features.user.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.furlaneto.murilo.shoplite.R
import br.com.furlaneto.murilo.shoplite.core.colors.AppColors
import br.com.furlaneto.murilo.shoplite.core.preferences.AppPreferences
import br.com.furlaneto.murilo.shoplite.enum.ThemeModeApp
import br.com.furlaneto.murilo.shoplite.features.order.domain.entity.OrderSummary
import br.com.furlaneto.murilo.shoplite.features.user.ui.components.ProfileOptions
import br.com.furlaneto.murilo.shoplite.features.user.ui.components.ThemeSelectionDialog
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    onWishlistClick: () -> Unit = {},
    onAddressClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {},
    onDeliveryClick: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val appPreferences = remember { AppPreferences(context) }
    val coroutineScope = rememberCoroutineScope()
    val currentThemeValue by appPreferences.themeFlow().collectAsState(initial = ThemeModeApp.SYSTEM.value)
    var showThemeDialog by remember { mutableStateOf(false) }

    ProfileContent(
        uiState = uiState,
        currentThemeValue = currentThemeValue,
        showThemeDialog = showThemeDialog,
        onBackClick = onBackClick,
        onCartClick = onCartClick,
        onWishlistClick = onWishlistClick,
        onAddressClick = onAddressClick,
        onPrivacyClick = onPrivacyClick,
        onDeliveryClick = onDeliveryClick,
        onThemeClick = { showThemeDialog = true },
        onDismissThemeDialog = { showThemeDialog = false },
        onSelectTheme = { selectedTheme ->
            coroutineScope.launch {
                appPreferences.saveTheme(selectedTheme)
            }
            showThemeDialog = false
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    uiState: ProfileUiState,
    currentThemeValue: String,
    showThemeDialog: Boolean,
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
    onWishlistClick: () -> Unit,
    onAddressClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    onDeliveryClick: () -> Unit,
    onThemeClick: () -> Unit,
    onDismissThemeDialog: () -> Unit,
    onSelectTheme: (ThemeModeApp) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(R.string.profile_title),
                        fontWeight = FontWeight.Bold
                    )
                },
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 24.dp)
        ) {
            // Seção 1: Cabeçalho do Usuário
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(AppColors.TagBackground),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "MF",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.Primary
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Murilo Furlaneto",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.PrimaryText
                )

                Text(
                    text = "murilo@email.com",
                    fontSize = 14.sp,
                    color = AppColors.SecondaryText
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Seção 2: Meus Pedidos
            Text(
                text = "Meus Pedidos",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.PrimaryText,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            when (uiState) {
                is ProfileUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = AppColors.Primary)
                    }
                }

                is ProfileUiState.Success -> {
                    if (uiState.orders.isEmpty()) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = AppColors.White)
                        ) {
                            Text(
                                text = "Você ainda não realizou nenhum pedido.",
                                modifier = Modifier.padding(16.dp),
                                color = AppColors.SecondaryText,
                                fontSize = 14.sp
                            )
                        }
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            uiState.orders.forEach { order ->
                                OrderCardItem(order = order)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Seção 3: Configurações e Atalhos
            Text(
                text = "Configurações",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.PrimaryText,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            ProfileOptions(
                onCartClick = onCartClick,
                onWishlistClick = onWishlistClick,
                onAddressClick = onAddressClick,
                onPrivacyClick = onPrivacyClick,
                onDeliveryClick = onDeliveryClick,
                onThemeClick = onThemeClick
            )
        }

        if (showThemeDialog) {
            ThemeSelectionDialog(
                currentThemeValue = currentThemeValue,
                onDismissRequest = onDismissThemeDialog,
                onThemeSelected = onSelectTheme
            )
        }
    }
}

@Composable
private fun OrderCardItem(order: OrderSummary) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Pedido #${order.orderId}",
                    fontWeight = FontWeight.Bold,
                    color = AppColors.PrimaryText
                )
                Spacer(modifier = Modifier.height(4.dp))
                val formattedTotal = remember(order.totalAmount) {
                    String.format(Locale.getDefault(), "R$ %.2f", order.totalAmount)
                }
                Text(
                    text = formattedTotal,
                    color = AppColors.Primary,
                    fontWeight = FontWeight.Bold
                )
            }

            Surface(
                shape = RoundedCornerShape(4.dp),
                color = Color(0xFFE8F5E9)
            ) {
                Text(
                    text = "Aprovado",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    fontSize = 12.sp,
                    color = Color(0xFF2E7D32),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

package br.com.furlaneto.murilo.shoplite.features.checkout.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import br.com.furlaneto.murilo.shoplite.features.checkout.domain.entity.PaymentMethod
import br.com.furlaneto.murilo.shoplite.features.checkout.domain.entity.ShippingAddress
import br.com.furlaneto.murilo.shoplite.features.order.domain.entity.OrderSummary
import java.util.Locale

@Composable
fun CheckoutScreen(
    cartViewModel: CartViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    onOrderConfirmedClick: (orderSummary: OrderSummary) -> Unit = {}
) {
    val cartUiState by cartViewModel.uiState.collectAsState()
    val totalAmount = (cartUiState as? CartUiState.Success)?.totalPrice ?: 0.0
    val itemsCount = (cartUiState as? CartUiState.Success)?.totalQuantity ?: 1

    var address by remember {
        mutableStateOf(
            ShippingAddress(
                zipCode = "01001-000",
                street = "Praça da Sé",
                number = "100",
                neighborhood = "Sé",
                city = "São Paulo",
                state = "SP"
            )
        )
    }

    var selectedPaymentMethod by remember { mutableStateOf(PaymentMethod.PIX) }

    CheckoutContent(
        totalAmount = totalAmount,
        address = address,
        selectedPaymentMethod = selectedPaymentMethod,
        onAddressChange = { address = it },
        onPaymentMethodChange = { selectedPaymentMethod = it },
        onBackClick = onBackClick,
        onConfirmOrderClick = {
            val generatedOrderId = (10000..99999).random().toString()
            val orderSummary = OrderSummary(
                orderId = "SL-$generatedOrderId",
                totalAmount = totalAmount,
                paymentMethod = selectedPaymentMethod,
                itemsCount = itemsCount
            )
            cartViewModel.onClearCart()
            onOrderConfirmedClick(orderSummary)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutContent(
    totalAmount: Double,
    address: ShippingAddress,
    selectedPaymentMethod: PaymentMethod,
    onAddressChange: (ShippingAddress) -> Unit,
    onPaymentMethodChange: (PaymentMethod) -> Unit,
    onBackClick: () -> Unit,
    onConfirmOrderClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Finalizar Compra", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_content_description)
                        )
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Button(
                    onClick = onConfirmOrderClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(16.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.Primary)
                ) {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Confirmar Pedido", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Endereço de Entrega
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = AppColors.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = AppColors.Primary)
                        Spacer(Modifier.width(8.dp))
                        Text("Endereço de Entrega", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedTextField(
                        value = address.street,
                        onValueChange = { onAddressChange(address.copy(street = it)) },
                        label = { Text("Rua/Avenida") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = address.number,
                            onValueChange = { onAddressChange(address.copy(number = it)) },
                            label = { Text("Número") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = address.zipCode,
                            onValueChange = { onAddressChange(address.copy(zipCode = it)) },
                            label = { Text("CEP") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }
                }
            }

            // Forma de Pagamento
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = AppColors.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Forma de Pagamento", fontSize = 16.sp, fontWeight = FontWeight.Bold)

                    PaymentOption(
                        title = "Pix (Aprovação Instantânea)",
                        icon = Icons.Default.CheckCircle,
                        isSelected = selectedPaymentMethod == PaymentMethod.PIX,
                        onClick = { onPaymentMethodChange(PaymentMethod.PIX) }
                    )

                    PaymentOption(
                        title = "Cartão de Crédito",
                        icon = Icons.Default.ShoppingCart,
                        isSelected = selectedPaymentMethod == PaymentMethod.CREDIT_CARD,
                        onClick = { onPaymentMethodChange(PaymentMethod.CREDIT_CARD) }
                    )

                    PaymentOption(
                        title = "Boleto Bancário",
                        icon = Icons.Default.LocationOn,
                        isSelected = selectedPaymentMethod == PaymentMethod.BOLETO,
                        onClick = { onPaymentMethodChange(PaymentMethod.BOLETO) }
                    )
                }
            }

            // Resumo do Pedido
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = AppColors.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Resumo do Pedido", fontSize = 16.sp, fontWeight = FontWeight.Bold)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Frete", color = AppColors.SecondaryText)
                        Text("Grátis", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                    }

                    HorizontalDivider(color = AppColors.Divider, thickness = 1.dp)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total a pagar", fontSize = 16.sp, fontWeight = FontWeight.Bold)

                        val formattedTotal = remember(totalAmount) {
                            String.format(Locale.getDefault(), "R$ %.2f", totalAmount)
                        }

                        Text(
                            text = formattedTotal,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = AppColors.Primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PaymentOption(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) AppColors.TagBackground else AppColors.BackgroundLight,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(selectedColor = AppColors.Primary)
            )
            Spacer(Modifier.width(8.dp))
            Icon(icon, contentDescription = null, tint = if (isSelected) AppColors.Primary else AppColors.SecondaryText)
            Spacer(Modifier.width(12.dp))
            Text(
                title,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) AppColors.Primary else AppColors.PrimaryText
            )
        }
    }
}

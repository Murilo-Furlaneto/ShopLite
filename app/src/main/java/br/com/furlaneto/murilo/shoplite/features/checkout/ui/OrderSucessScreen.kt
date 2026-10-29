package br.com.furlaneto.murilo.shoplite.features.checkout.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.furlaneto.murilo.shoplite.core.colors.AppColors
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderSuccessScreen(
    orderId: String = "SL-84920",
    totalAmount: Double = 0.0,
    onBackToHomeClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Pedido Confirmado",
                        fontWeight = FontWeight.Bold
                    )
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
                    onClick = onBackToHomeClick,
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
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Voltar para a Página Inicial",
                        fontSize = 16.sp,
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF4CAF50)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(56.dp),
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Pedido Realizado com Sucesso!",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.PrimaryText,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Agradecemos por comprar na ShopLite. Seu pedido já está sendo processado.",
                fontSize = 14.sp,
                color = AppColors.SecondaryText,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = AppColors.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Número do Pedido",
                            fontSize = 14.sp,
                            color = AppColors.SecondaryText
                        )
                        Text(
                            text = "#$orderId",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = AppColors.PrimaryText
                        )
                    }

                    HorizontalDivider(color = AppColors.Divider, thickness = 1.dp)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Status",
                            fontSize = 14.sp,
                            color = AppColors.SecondaryText
                        )
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFFE8F5E9)
                        ) {
                            Text(
                                text = "Aprovado",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32)
                            )
                        }
                    }

                    HorizontalDivider(color = AppColors.Divider, thickness = 1.dp)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Valor Total",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = AppColors.PrimaryText
                        )

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

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

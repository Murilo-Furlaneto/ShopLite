package br.com.furlaneto.murilo.shoplite.ui.screen

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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.com.furlaneto.murilo.shoplite.domain.entity.Product
import br.com.furlaneto.murilo.shoplite.ui.components.CategoryChips
import br.com.furlaneto.murilo.shoplite.ui.components.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var search by remember { mutableStateOf("") }

    val dummyProduct = Product(
        id = 1,
        title = "Tênis Speed Pro Max Carbon",
        price = 859.90,
        description = "Alta performance e conforto.",
        category = "Esportes",
        image = "https://images.tcdn.com.br/img/img_prod/1126744/tenis_adidas_coreracer_masculino_cor_vermelho_6135_1_2120db2077e6822295627685651c6e1e.jpg"
    )

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TextField(
                value = search,
                onValueChange = { search = it },
                placeholder = { Text(text = "Buscar produtos, marcas e mais...") },
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
                    focusedContainerColor = Color(0xFFF1F1F1),
                    unfocusedContainerColor = Color(0xFFF1F1F1),
                    disabledContainerColor = Color(0xFFF1F1F1)
                )
            )

            CategoryChips()

            Spacer(modifier = Modifier.height(8.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                val productList = List(10) { dummyProduct }
                
                items(productList) { product ->
                    ProductCard(
                        name = product.title,
                        category = product.category,
                        price = product.price,
                        imageUrl = product.image,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

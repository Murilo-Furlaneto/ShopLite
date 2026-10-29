package br.com.furlaneto.murilo.shoplite.features.product.data.mock

import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.Product
import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.ProductCategory
import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.Rating

object MockProducts {
    val list = listOf(
        Product(
            id = 1,
            title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
            price = 109.95,
            description = "Mochila perfeita para o uso diário e caminhadas na floresta. Bolso para laptop de 15 polegadas em compartimento acolchoado.",
            category = ProductCategory.MENS_CLOTHING,
            image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
            rating = Rating(rate = 3.9, count = 120)
        ),
        Product(
            id = 2,
            title = "Mens Casual Premium Slim Fit T-Shirts",
            price = 22.30,
            description = "Estilo ajustado, manga curta com contraste e gola henley de três botões. Tecido leve e macio para um conforto respirável.",
            category = ProductCategory.MENS_CLOTHING,
            image = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
            rating = Rating(rate = 4.1, count = 259)
        ),
        Product(
            id = 3,
            title = "Mens Cotton Jacket",
            price = 55.99,
            description = "Ótimas jaquetas de exterior para Primavera/Outono/Inverno, adequadas para muitas ocasiões, como trabalho, caminhada e acampamento.",
            category = ProductCategory.MENS_CLOTHING,
            image = "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg",
            rating = Rating(rate = 4.7, count = 500)
        ),
        Product(
            id = 4,
            title = "John Hardy Women's Legends Naga Gold & Silver Dragon Station Bracelet",
            price = 695.00,
            description = "Da nossa Coleção Legends, o Naga foi inspirado no mítico dragão d'água que protege a pérola do oceano.",
            category = ProductCategory.JEWELERY,
            image = "https://fakestoreapi.com/img/71pWzhdJNwL._AC_UL640_QL65_ML3_.jpg",
            rating = Rating(rate = 4.6, count = 400)
        ),
        Product(
            id = 5,
            title = "Solid Gold Petite Micropave",
            price = 168.00,
            description = "Projetado e vendido por Hafeez Center nos EUA. Satisfação garantida. Devolução ou troca de qualquer pedido dentro de 30 dias.",
            category = ProductCategory.JEWELERY,
            image = "https://fakestoreapi.com/img/61sbMiUnoGL._AC_UL640_QL65_ML3_.jpg",
            rating = Rating(rate = 3.9, count = 70)
        ),
        Product(
            id = 6,
            title = "WD 2TB Elements Portable External Hard Drive - USB 3.0",
            price = 64.00,
            description = "Transferências de dados de alta velocidade. Melhore o desempenho do PC com grande capacidade de armazenamento de 2TB.",
            category = ProductCategory.ELECTRONICS,
            image = "https://fakestoreapi.com/img/61IBBWJtd-L._AC_SX679_.jpg",
            rating = Rating(rate = 3.3, count = 203)
        ),
        Product(
            id = 7,
            title = "SanDisk SSD PLUS 1TB Internal SSD - SATA III 6 Gb/s",
            price = 109.00,
            description = "Atualização fácil para inicializações, desligamentos, tempos de resposta de aplicativos e velocidades de transferência de dados mais rápidos.",
            category = ProductCategory.ELECTRONICS,
            image = "https://fakestoreapi.com/img/61U7T1koQqL._AC_SX679_.jpg",
            rating = Rating(rate = 2.9, count = 470)
        ),
        Product(
            id = 8,
            title = "BIYLACLESEN Women's 3-in-1 Snowboard Jacket Winter Coats",
            price = 56.99,
            description = "Capa removível acolchoada ajustável, punhos ajustáveis com velcro, bainha com cordão ajustável e 2 bolsos com zíper para as mãos.",
            category = ProductCategory.WOMENS_CLOTHING,
            image = "https://fakestoreapi.com/img/51Y5NI-I5jL._AC_UX679_.jpg",
            rating = Rating(rate = 2.6, count = 235)
        )
    )
}

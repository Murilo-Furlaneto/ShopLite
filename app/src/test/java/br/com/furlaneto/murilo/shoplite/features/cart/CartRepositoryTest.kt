package br.com.furlaneto.murilo.shoplite.features.cart

import android.content.Context
import app.cash.turbine.test
import br.com.furlaneto.murilo.shoplite.features.cart.data.local.dao.CartDao
import br.com.furlaneto.murilo.shoplite.features.cart.data.local.entity.CartItemEntity
import br.com.furlaneto.murilo.shoplite.features.cart.data.repository.CartRepositoryImpl
import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.Product
import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.ProductCategory
import br.com.furlaneto.murilo.shoplite.features.product.domain.entity.Rating
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CartRepositoryTest {

    private val cartDao: CartDao = mockk(relaxed = true)
    private val context: Context = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: CartRepositoryImpl

    private val sampleProduct = Product(
        id = 1,
        title = "Mochila Fjallraven",
        price = 100.0,
        description = "Mochila para notebook",
        category = ProductCategory.MENS_CLOTHING,
        image = "https://example.com/image.jpg",
        rating = Rating(rate = 4.5, count = 10)
    )

    private val sampleCartItemEntity = CartItemEntity(
        productId = 1,
        title = "Mochila Fjallraven",
        price = 100.0,
        description = "Mochila para notebook",
        category = "MENS_CLOTHING",
        image = "https://example.com/image.jpg",
        ratingRate = 4.5,
        ratingCount = 10,
        quantity = 1
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { cartDao.getAllCartItems() } returns MutableStateFlow(listOf(sampleCartItemEntity))
        repository = CartRepositoryImpl(cartDao, context)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `cartItems deve emitir itens do banco de dados reativamente`() = runTest(testDispatcher) {
        repository.cartItems.test {
            val items = awaitItem()
            assertEquals(1, items.size)
            assertEquals("Mochila Fjallraven", items[0].product.title)
            assertEquals(100.0, items[0].subtotal, 0.01)
        }
    }

    @Test
    fun `addToCart com produto novo deve inserir no banco com quantidade 1`() = runTest(testDispatcher) {
        coEvery { cartDao.getCartItemById(1) } returns null

        repository.addToCart(sampleProduct)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { cartDao.insertOrUpdate(any()) }
    }

    @Test
    fun `clearCart deve chamar clearCart no DAO`() = runTest(testDispatcher) {
        repository.clearCart()
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { cartDao.clearCart() }
    }

    @Test
    fun `removeFromCart deve chamar deleteByProductId no DAO`() = runTest(testDispatcher) {
        repository.removeFromCart(sampleProduct)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { cartDao.deleteByProductId(1) }
    }
}

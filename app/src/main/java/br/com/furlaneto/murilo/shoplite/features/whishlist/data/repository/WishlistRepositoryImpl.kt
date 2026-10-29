package br.com.furlaneto.murilo.shoplite.features.whishlist.data.repository

import br.com.furlaneto.murilo.shoplite.features.whishlist.data.repository.local.dao.WishlistDao
import br.com.furlaneto.murilo.shoplite.features.whishlist.data.repository.local.entity.WishlistEntity
import br.com.furlaneto.murilo.shoplite.features.whishlist.domain.repository.WishlistRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WishlistRepositoryImpl @Inject constructor(
    private val wishlistDao: WishlistDao
) : WishlistRepository {

    private val repositoryScope = CoroutineScope(Dispatchers.IO)

    override val favoriteProductIds: StateFlow<Set<Int>> = wishlistDao.getAllFavoriteProductIds()
        .map { ids -> ids.toSet() }
        .stateIn(
            scope = repositoryScope,
            started = SharingStarted.Eagerly,
            initialValue = emptySet()
        )

    override fun toggleFavorite(productId: Int) {
        repositoryScope.launch {
            val isFav = wishlistDao.isFavorite(productId)
            if (isFav) {
                wishlistDao.removeFavorite(productId)
            } else {
                wishlistDao.addFavorite(WishlistEntity(productId))
            }
        }
    }

    override fun isFavorite(productId: Int): Boolean {
        return favoriteProductIds.value.contains(productId)
    }
}

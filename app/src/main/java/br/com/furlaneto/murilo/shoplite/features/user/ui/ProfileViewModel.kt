package br.com.furlaneto.murilo.shoplite.features.user.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.furlaneto.murilo.shoplite.features.order.domain.entity.OrderSummary
import br.com.furlaneto.murilo.shoplite.features.order.domain.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface ProfileUiState {
    data object Loading : ProfileUiState
    data class Success(
        val orders: List<OrderSummary>
    ) : ProfileUiState
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {

    val uiState: StateFlow<ProfileUiState> = orderRepository.observeOrders()
        .map { orders ->
            ProfileUiState.Success(orders = orders)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProfileUiState.Loading
        )
}

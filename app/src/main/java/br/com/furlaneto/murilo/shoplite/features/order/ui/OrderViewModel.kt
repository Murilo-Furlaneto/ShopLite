package br.com.furlaneto.murilo.shoplite.features.order.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.furlaneto.murilo.shoplite.features.order.domain.entity.OrderSummary
import br.com.furlaneto.murilo.shoplite.features.order.domain.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {

    fun saveOrder(order: OrderSummary) {
        viewModelScope.launch {
            orderRepository.saveOrder(order)
        }
    }
}

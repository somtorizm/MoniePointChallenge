package com.vectorinc.moniepointchallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vectorinc.moniepointchallenge.R
import com.vectorinc.moniepointchallenge.data.model.ShipmentCardItem
import com.vectorinc.moniepointchallenge.data.model.ShipmentStatus
import com.vectorinc.moniepointchallenge.data.repository.ShippingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShipmentHistoryViewModel @Inject constructor(
    private val shippingRepository: ShippingRepository
) : ViewModel() {

    private val _shipments = MutableStateFlow<List<ShipmentCardItem>>(emptyList())
    val shipments: StateFlow<List<ShipmentCardItem>> = _shipments.asStateFlow()

    init {
        loadShipments()
    }

    private fun loadShipments() {
        viewModelScope.launch {
            val raw = shippingRepository.loadShippingInfo().shipments
            _shipments.value = raw.mapIndexed { index, shipment ->
                val status = when (index % 4) {
                    0 -> ShipmentStatus.IN_PROGRESS
                    1 -> ShipmentStatus.PENDING
                    2 -> ShipmentStatus.COMPLETED
                    3 -> ShipmentStatus.CANCELLED
                    else -> ShipmentStatus.CANCELLED
                }

                ShipmentCardItem(
                    number = "#${shipment.number}",
                    status = status,
                    title = "Arriving today!",
                    subtitle = "Your delivery, #${shipment.number} from ${shipment.sender}, is arriving today!",
                    price = "$${(500..5000).random()} USD",
                    date = "Sep ${(15..30).random()}, 2023",
                    imageRes = R.drawable.package_received
                )
            }
        }
    }
}

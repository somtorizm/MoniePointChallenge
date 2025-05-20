package com.vectorinc.moniepointchallenge.viewmodel

import androidx.lifecycle.ViewModel
import com.vectorinc.moniepointchallenge.data.model.Shipment
import com.vectorinc.moniepointchallenge.data.model.ShipmentListItem
import com.vectorinc.moniepointchallenge.data.repository.ShippingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ShipmentTrackingViewModel @Inject constructor(
    private val shippingRepository: ShippingRepository
) : ViewModel() {

    private val _shipmentList = MutableStateFlow(shippingRepository.loadShipmentList())
    val shipment: StateFlow<List<ShipmentListItem>> = _shipmentList
}

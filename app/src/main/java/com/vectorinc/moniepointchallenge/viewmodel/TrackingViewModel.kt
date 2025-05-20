package com.vectorinc.moniepointchallenge.viewmodel

import androidx.lifecycle.ViewModel
import com.vectorinc.moniepointchallenge.data.model.Shipment
import com.vectorinc.moniepointchallenge.data.model.ShippingInfo
import com.vectorinc.moniepointchallenge.data.model.VehicleOption
import com.vectorinc.moniepointchallenge.data.repository.ShippingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TrackingViewModel @Inject constructor(
    private val repository: ShippingRepository
) : ViewModel() {

    private val _shipment = MutableStateFlow(repository.loadShippingInfo().shipment)
    val shipment: StateFlow<Shipment> = _shipment

    private val _vehicles = MutableStateFlow(repository.loadShippingInfo().vehicles)
    val vehicles: StateFlow<List<VehicleOption>> = _vehicles

}

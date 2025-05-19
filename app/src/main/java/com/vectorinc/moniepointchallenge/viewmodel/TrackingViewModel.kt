package com.vectorinc.moniepointchallenge.viewmodel

import androidx.lifecycle.ViewModel
import com.vectorinc.moniepointchallenge.model.ShippingInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrackingViewModel @Inject constructor(
    shippingInfo: ShippingInfo
) : ViewModel() {
    val shipment = shippingInfo.shipment
    val vehicles = shippingInfo.vehicles
}
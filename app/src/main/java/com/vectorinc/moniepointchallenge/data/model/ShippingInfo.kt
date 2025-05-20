package com.vectorinc.moniepointchallenge.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ShippingInfo(
    val shipment: Shipment,
    val vehicles: List<VehicleOption>
)
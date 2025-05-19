package com.vectorinc.moniepointchallenge.model

data class ShippingInfo(
    val shipment: Shipment,
    val vehicles: List<VehicleOption>
)
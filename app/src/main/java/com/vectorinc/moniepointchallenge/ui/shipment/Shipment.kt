package com.vectorinc.moniepointchallenge.ui.shipment


enum class ShipmentStatus {
    Completed, InProgress, Pending, Loading
}

data class Shipment(
    val id: String,
    val origin: String,
    val price: String,
    val date: String,
    val status: ShipmentStatus,
    val iconUrl: String
)
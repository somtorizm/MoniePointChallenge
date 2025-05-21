package com.vectorinc.moniepointchallenge.data.model


data class ShipmentCardItem(
    val number: String,
    val status: ShipmentStatus,
    val title: String,
    val subtitle: String,
    val price: String,
    val date: String,
    val imageRes: Int
)


enum class ShipmentStatus(val label: String) {
    ALL("All"),
    COMPLETED("Completed"),
    IN_PROGRESS("In progress"),
    PENDING("Pending"),
    CANCELLED("Cancelled"),
}
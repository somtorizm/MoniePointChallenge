package com.vectorinc.moniepointchallenge.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ShipmentListItem(
    val title: String,
    val trackingCode: String,
    val route: String
)
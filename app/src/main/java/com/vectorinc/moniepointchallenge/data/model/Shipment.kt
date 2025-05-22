package com.vectorinc.moniepointchallenge.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Shipment(
    val number: String,
    val item: String,
    val sender: String,
    val receiver: String,
    val eta: String,
    val status: String
)
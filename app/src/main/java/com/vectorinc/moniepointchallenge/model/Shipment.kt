package com.vectorinc.moniepointchallenge.model

data class Shipment(
    val number: String,
    val sender: String,
    val receiver: String,
    val eta: String,
    val status: String
)
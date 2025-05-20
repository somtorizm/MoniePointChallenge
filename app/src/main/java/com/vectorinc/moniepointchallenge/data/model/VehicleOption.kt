package com.vectorinc.moniepointchallenge.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VehicleOption(
    val name: String,
    val description: String,

    @SerialName("icon")
    val iconName: String,
    val iconRes: Int = 0
)
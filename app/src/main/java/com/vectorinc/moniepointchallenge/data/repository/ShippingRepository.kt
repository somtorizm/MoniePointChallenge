package com.vectorinc.moniepointchallenge.data.repository

import android.content.Context
import com.vectorinc.moniepointchallenge.data.model.ShipmentListItem
import com.vectorinc.moniepointchallenge.data.model.ShippingInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

interface ShippingRepository {
    fun loadShippingInfo(): ShippingInfo
    fun loadShipmentList(): List<ShipmentListItem>
}

class ShippingRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): ShippingRepository {
    override fun loadShippingInfo(): ShippingInfo {
        val json = context.assets.open("shipments.json").bufferedReader().use { it.readText() }

        val parsedInfo = Json {
            ignoreUnknownKeys = true
        }.decodeFromString<ShippingInfo>(json)

        val resolvedVehicles = parsedInfo.vehicles.map { vehicle ->
            val resId = context.resources.getIdentifier(vehicle.iconName, "drawable", context.packageName)
            vehicle.copy(iconRes = resId)
        }
        return parsedInfo.copy(vehicles = resolvedVehicles)
    }

    override fun loadShipmentList(): List<ShipmentListItem> {
        val info = loadShippingInfo()
        return if (info.shipments.isNotEmpty()) {
            info.shipments.map { shipment ->
                ShipmentListItem(
                    title = "Shipment from ${shipment.sender}",
                    trackingCode = "#${shipment.number}",
                    route = "${shipment.sender} \u2192 ${shipment.receiver}"
                )
            }
        } else {
            listOf(
                ShipmentListItem(
                    title = "Shipment from ${info.shipment.sender}",
                    trackingCode = "#${info.shipment.number}",
                    route = "${info.shipment.sender} \u2192 ${info.shipment.receiver}"
                )
            )
        }
    }
}

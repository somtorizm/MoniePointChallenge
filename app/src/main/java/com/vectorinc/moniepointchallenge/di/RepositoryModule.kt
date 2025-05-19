package com.vectorinc.moniepointchallenge.di

import android.content.Context
import com.vectorinc.moniepointchallenge.model.Shipment
import com.vectorinc.moniepointchallenge.model.ShippingInfo
import com.vectorinc.moniepointchallenge.model.VehicleOption
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.json.JSONObject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideShippingInfo(@ApplicationContext context: Context): ShippingInfo {
        val json = context.assets.open("shipments.json").bufferedReader().use { it.readText() }
        val obj = JSONObject(json)
        val shipmentObj = obj.getJSONObject("shipment")
        val shipment = Shipment(
            number = shipmentObj.getString("number"),
            sender = shipmentObj.getString("sender"),
            receiver = shipmentObj.getString("receiver"),
            eta = shipmentObj.getString("eta"),
            status = shipmentObj.getString("status")
        )
        val vehiclesArray = obj.getJSONArray("vehicles")
        val vehicles = mutableListOf<VehicleOption>()
        for (i in 0 until vehiclesArray.length()) {
            val v = vehiclesArray.getJSONObject(i)
            vehicles.add(
                VehicleOption(
                    name = v.getString("name"),
                    description = v.getString("description"),
                    iconName = v.getString("icon")
                )
            )
        }
        return ShippingInfo(shipment, vehicles)
    }
}
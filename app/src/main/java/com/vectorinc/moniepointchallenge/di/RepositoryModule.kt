package com.vectorinc.moniepointchallenge.di

import android.content.Context
import com.vectorinc.moniepointchallenge.data.model.Shipment
import com.vectorinc.moniepointchallenge.data.model.ShipmentListItem
import com.vectorinc.moniepointchallenge.data.model.ShippingInfo
import com.vectorinc.moniepointchallenge.data.model.VehicleOption
import com.vectorinc.moniepointchallenge.data.repository.ShippingRepository
import com.vectorinc.moniepointchallenge.data.repository.ShippingRepositoryImpl
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
    fun provideShippingRepository(
        @ApplicationContext context: Context
    ): ShippingRepository = ShippingRepositoryImpl(context)
}
package com.vectorinc.moniepointchallenge.viewmodel

import androidx.lifecycle.ViewModel
import com.vectorinc.moniepointchallenge.model.ShipmentListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShipmentTrackingViewModel @Inject constructor(
    val shipments: List<ShipmentListItem>
) : ViewModel()

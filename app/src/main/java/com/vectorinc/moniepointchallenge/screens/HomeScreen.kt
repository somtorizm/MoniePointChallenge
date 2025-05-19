package com.vectorinc.moniepointchallenge.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.vectorinc.moniepointchallenge.viewmodel.TrackingViewModel

@Composable
fun HomeScreen(viewModel: TrackingViewModel = hiltViewModel()) {
    TrackingDashboardScreen(
        shipment = viewModel.shipment,
        vehicles = viewModel.vehicles
    )
}
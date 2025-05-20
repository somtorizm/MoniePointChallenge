package com.vectorinc.moniepointchallenge.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vectorinc.moniepointchallenge.ui.Screen
import com.vectorinc.moniepointchallenge.viewmodel.TrackingViewModel


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: TrackingViewModel = hiltViewModel()
) {
    val shipment = viewModel.shipment.collectAsState().value
    val vehicles = viewModel.vehicles.collectAsState().value

    TrackingDashboardScreen(
        shipment = shipment,
        vehicles = vehicles,
        onSearchClick = { navController.navigate(Screen.Tracking.route) }
    )
}
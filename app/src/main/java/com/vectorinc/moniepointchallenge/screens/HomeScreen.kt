package com.vectorinc.moniepointchallenge.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vectorinc.moniepointchallenge.ui.Screen
import com.vectorinc.moniepointchallenge.viewmodel.TrackingViewModel


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: TrackingViewModel = hiltViewModel()
) {
    TrackingDashboardScreen(
        shipment = viewModel.shipment,
        vehicles = viewModel.vehicles,
        onSearchClick = { navController.navigate(Screen.Calculate.route) }
    )
}
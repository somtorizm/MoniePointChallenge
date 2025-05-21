package com.vectorinc.moniepointchallenge.screens

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vectorinc.moniepointchallenge.theme.TopSectionPurple
import com.vectorinc.moniepointchallenge.ui.Screen
import com.vectorinc.moniepointchallenge.viewmodel.TrackingViewModel


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: TrackingViewModel = hiltViewModel()
) {
    val shipment = viewModel.shipment.collectAsState().value
    val vehicles = viewModel.vehicles.collectAsState().value

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = TopSectionPurple.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars
        }
    }
        TrackingDashboardScreen(
        navController = navController,
        shipment = shipment,
        vehicles = vehicles,
        onSearchClick = {
            navController.navigate(Screen.Tracking.route) {
                launchSingleTop = true
            }
        }
    )
}
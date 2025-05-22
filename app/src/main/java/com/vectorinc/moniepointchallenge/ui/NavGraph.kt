package com.vectorinc.moniepointchallenge.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vectorinc.moniepointchallenge.screens.CalculateScreen
import com.vectorinc.moniepointchallenge.screens.HomeScreen
import com.vectorinc.moniepointchallenge.screens.ProfileScreen
import com.vectorinc.moniepointchallenge.screens.ResultScreen
import com.vectorinc.moniepointchallenge.screens.ShipmentHistoryScreen
import com.vectorinc.moniepointchallenge.screens.ShipmentMapScreen
import com.vectorinc.moniepointchallenge.screens.ShipmentTrackingScreen

sealed class Screen(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Calculate : Screen("calculate", "Calculate", Icons.Default.Calculate)
    object Tracking: Screen("tracking", "Tracking", Icons.Default.LocalShipping)
    object Shipping : Screen("shipping", "Shipment", Icons.Default.History)
    object TrackingMap: Screen("tracking_map", "Tracking Map", Icons.Default.LocalShipping)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
    object ShippingCalculateResult: Screen("shipping_calculate_result", "Shipping Calculate Result", Icons.Default.Calculate)
}


@Composable
fun MoniePointNavHost(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = Screen.Home.route, modifier = modifier) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Calculate.route) { CalculateScreen(navController) }
        composable(Screen.Shipping.route) { ShipmentHistoryScreen(navController) }
        composable(Screen.Profile.route) { ProfileScreen() }
        composable(Screen.Tracking.route) { ShipmentTrackingScreen(navController) }
        composable(Screen.ShippingCalculateResult.route) { ResultScreen(navController)  }
        composable(Screen.TrackingMap.route) { ShipmentMapScreen(navController) }
    }
}
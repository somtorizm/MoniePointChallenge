package com.vectorinc.moniepointchallenge.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
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
import com.vectorinc.moniepointchallenge.screens.ShippingScreen

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    data object Home : Screen("home", "Home", Icons.Default.Home)
    data object Calculate : Screen("calculate", "Calculate", Icons.Default.Calculate)
    data object Shipping : Screen("shipping", "Shipment", Icons.Default.LocalShipping)
    data object Profile : Screen("profile", "Profile", Icons.Default.Person)
}

@Composable
fun MoniePointNavHost(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = Screen.Home.route, modifier = modifier) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Calculate.route) { CalculateScreen() }
        composable(Screen.Shipping.route) { ShippingScreen() }
        composable(Screen.Profile.route) { ProfileScreen() }
    }
}
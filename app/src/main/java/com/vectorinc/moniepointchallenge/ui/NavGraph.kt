package com.vectorinc.moniepointchallenge.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vectorinc.moniepointchallenge.screens.CalculateScreen
import com.vectorinc.moniepointchallenge.screens.HomeScreen
import com.vectorinc.moniepointchallenge.screens.ProfileScreen
import com.vectorinc.moniepointchallenge.screens.ShippingScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Calculate : Screen("calculate")
    object Shipping : Screen("shipping")
    object Profile : Screen("profile")
}

@Composable
fun MoniePointNavHost(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = Screen.Home.route, modifier = modifier) {
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.Calculate.route) { CalculateScreen() }
        composable(Screen.Shipping.route) { ShippingScreen() }
        composable(Screen.Profile.route) { ProfileScreen() }
    }
}
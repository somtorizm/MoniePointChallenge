package com.vectorinc.moniepointchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.MapsInitializer
import com.vectorinc.moniepointchallenge.theme.MoniePointChallengeTheme
import com.vectorinc.moniepointchallenge.theme.TopSectionPurple
import com.vectorinc.moniepointchallenge.ui.BottomNavigationBar
import com.vectorinc.moniepointchallenge.ui.MoniePointNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapsInitializer.initialize(this)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                TopSectionPurple.toArgb(),
                TopSectionPurple.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.auto(
                TopSectionPurple.toArgb(),
                TopSectionPurple.toArgb()
            )
        )
        setContent {
            MoniePointChallengeTheme {
                val navController = rememberNavController()
                val showBottomBar = remember { mutableStateOf(true) }

                val navBackStackEntry = navController.currentBackStackEntryAsState().value
                val currentRoute = navBackStackEntry?.destination?.route
                val bottomBarRoutes = listOf("home", "profile")

                LaunchedEffect(currentRoute) {
                    showBottomBar.value = currentRoute in bottomBarRoutes
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        AnimatedVisibility(
                            visible = showBottomBar.value,
                            enter = slideInVertically(
                                initialOffsetY = { it },
                                animationSpec = tween(durationMillis = 350)
                            ) + fadeIn(animationSpec = tween(350)),
                            exit = slideOutVertically(
                                targetOffsetY = { it },
                                animationSpec = tween(durationMillis = 300)
                            ) + fadeOut(animationSpec = tween(300))
                        ) {
                            BottomNavigationBar(navController = navController)
                        }
                    }
                ) { padding ->
                    MoniePointNavHost(
                        navController = navController,
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

package com.vectorinc.moniepointchallenge.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        Screen.Home,
        Screen.Calculate,
        Screen.Shipping,
        Screen.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Surface(
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        tonalElevation = 4.dp,
        shadowElevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .height(84.dp),
        color = Color.White
    ) {
        BoxWithConstraints(Modifier.fillMaxSize()) {
            val itemWidth = maxWidth / items.size
            val sliderWidth = 82.dp
            val selectedIndex = items.indexOfFirst { it.route == currentRoute }.coerceAtLeast(0)
            val sliderOffset by animateDpAsState(
                targetValue = itemWidth * selectedIndex + (itemWidth - sliderWidth) / 2,
                animationSpec = tween(
                    durationMillis = 350,
                    easing = FastOutSlowInEasing
                ),
                label = "SliderOffset"
            )

            Box(
                modifier = Modifier
                    .offset(x = sliderOffset, y = 8.dp)
                    .width(sliderWidth)
                    .height(6.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF4B0082))
                    .align(Alignment.TopStart)
            )

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, screen ->
                    val selected = selectedIndex == index

                    val contentColor by animateColorAsState(
                        if (selected) Color(0xFF4B0082) else Color.Gray,
                        label = "TabContentColor"
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                if (!selected) {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                            .padding(vertical = 8.dp)
                    ) {
                        Spacer(modifier = Modifier.height(12.dp))

                        Icon(
                            imageVector = screen.icon,
                            contentDescription = screen.label,
                            tint = contentColor,
                            modifier = Modifier.size(26.dp)
                        )
                        Text(
                            text = screen.label,
                            color = contentColor,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}

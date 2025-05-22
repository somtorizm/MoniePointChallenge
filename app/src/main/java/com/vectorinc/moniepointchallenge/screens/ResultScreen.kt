package com.vectorinc.moniepointchallenge.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.navigation.NavController
import com.vectorinc.moniepointchallenge.R
import com.vectorinc.moniepointchallenge.theme.OrangePrimary
import com.vectorinc.moniepointchallenge.ui.Screen
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun ResultScreen(navController: NavController) {
    var visible by remember { mutableStateOf(false) }
    val estimatedAmount = remember { Random.nextInt(400, 2000) }

    val finalAmount = remember { Random.nextInt(1000, 2000) }
    var currentAmount by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (currentAmount < finalAmount) {
            delay(16L)
            currentAmount += (finalAmount / 60).coerceAtLeast(1)
            if (currentAmount > finalAmount) currentAmount = finalAmount
        }
    }


    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = Color.White.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars
        }

        BackHandler {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Home.route) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    LaunchedEffect(Unit) {
        delay(150)
        visible = true
    }

    val iconOffset by animateDpAsState(
        targetValue = if (visible) 0.dp else 60.dp,
        animationSpec = tween(600)
    )
    val iconAlpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(600)
    )

    val boxOffset by animateDpAsState(
        targetValue = if (visible) 0.dp else 60.dp,
        animationSpec = tween(700, delayMillis = 200)
    )
    val boxAlpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(700, delayMillis = 200)
    )

    val textAlpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(500, delayMillis = 400)
    )
    val costScale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.7f,
        animationSpec = tween(500, delayMillis = 500)
    )

    val buttonOffset by animateDpAsState(
        targetValue = if (visible) 0.dp else 40.dp,
        animationSpec = tween(600, delayMillis = 600)
    )
    val buttonAlpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(600, delayMillis = 600)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp, bottom = 48.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon),
                contentDescription = "Truck",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .offset(y = iconOffset)
                    .alpha(iconAlpha)
            )

            Image(
                painter = painterResource(id = R.drawable.box),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(280.dp)
                    .offset(y = boxOffset)
                    .alpha(boxAlpha)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.alpha(textAlpha)
            ) {
                Text(
                    "Total Estimated Amount",
                    fontSize = 20.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    "$$currentAmount USD",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50),
                    modifier = Modifier.scale(costScale)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "This amount is estimated. It may vary\nif you change your location or weight.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }


            Button(
                onClick = {
                    navController.navigate(Screen.Home.route) {
                        launchSingleTop = true
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .offset(y = buttonOffset)
                    .alpha(buttonAlpha)
            ) {
                Text("Back to home", color = Color.White)
            }
        }
    }
}

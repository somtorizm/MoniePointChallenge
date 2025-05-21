package com.vectorinc.moniepointchallenge.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.vectorinc.moniepointchallenge.theme.TopSectionPurple
import com.vectorinc.moniepointchallenge.ui.Screen
import kotlinx.coroutines.delay

@Composable
fun ResultScreen(navController: NavController) {
    var visible by remember { mutableStateOf(false) }

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


        LaunchedEffect(Unit) {
        delay(100)
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(600)) + slideInVertically(initialOffsetY = { it / 2 }),
            exit = fadeOut()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 64.dp, bottom = 48.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.icon),
                        modifier = Modifier.height(150.dp),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Truck",
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.box),
                    contentDescription = null,
                    modifier = Modifier
                        .size(300.dp)
                        .padding(vertical = 16.dp)
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Total Estimated Amount",
                        fontSize = 20.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        "$1460 USD",
                        fontSize = 28.sp,
                        color = Color(0xFF4CAF50) // Green
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

                Spacer(modifier = Modifier.weight(1f))

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
                ) {
                    Text("Back to home", color = Color.White)
                }
            }
        }
    }
    }
}

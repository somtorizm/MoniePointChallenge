package com.vectorinc.moniepointchallenge.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vectorinc.moniepointchallenge.R
import com.vectorinc.moniepointchallenge.theme.TopSectionPurple

@Composable
fun DefaultAppBar(
    onBackClick: () -> Unit,
    appBarTitle: String
) {
    val animateIn = remember { mutableStateOf(false) }

    val appBarHeight by animateFloatAsState(
        targetValue = if (animateIn.value) 58f else 108f,
        animationSpec = tween(durationMillis = 700),
        label = "AppBarHeight"
    )

    val appBarOffsetY by animateFloatAsState(
        targetValue = if (animateIn.value) 0f else 20f,
        animationSpec = tween(durationMillis = 500),
        label = "AppBarOffset"
    )

    val appBarAlpha by animateFloatAsState(
        targetValue = if (animateIn.value) 1f else 0.7f,
        animationSpec = tween(durationMillis = 500),
        label = "AppBarAlpha"
    )

    LaunchedEffect(Unit) {
        animateIn.value = true
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(TopSectionPurple)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    translationY = appBarOffsetY
                    alpha = appBarAlpha
                }
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(TopSectionPurple)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(appBarHeight.dp)
                    .padding(horizontal = 8.dp)
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Image(
                        painter = painterResource(R.drawable.outline_arrow_back_ios_24),
                        contentDescription = "Back",
                    )
                }

                Text(
                    text = appBarTitle,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
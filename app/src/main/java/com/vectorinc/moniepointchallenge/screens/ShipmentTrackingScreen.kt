package com.vectorinc.moniepointchallenge.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import com.vectorinc.moniepointchallenge.viewmodel.ShipmentTrackingViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Print
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vectorinc.moniepointchallenge.R
import com.vectorinc.moniepointchallenge.data.model.ShipmentListItem
import com.vectorinc.moniepointchallenge.theme.MoniePointChallengeTheme
import com.vectorinc.moniepointchallenge.theme.TopSectionPurple

@Composable
fun ShipmentTrackingScreen(
    navController: NavController,
    viewModel: ShipmentTrackingViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val (query, setQuery) = remember { mutableStateOf("") }
    val shipments = viewModel.shipment.collectAsState()

    val isBarVisible = remember { mutableStateOf(false) }
    val isBackVisible = remember { mutableStateOf(false) }

    val barOffsetY by animateFloatAsState(
        targetValue = if (isBarVisible.value) 0f else 80f,
        animationSpec = tween(durationMillis = 600),
        label = "BarSlideUp"
    )
    val barAlpha by animateFloatAsState(
        targetValue = if (isBarVisible.value) 1f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "BarFadeIn"
    )

    val backOffsetX by animateFloatAsState(
        targetValue = if (isBackVisible.value) 0f else -60f,
        animationSpec = tween(durationMillis = 500),
        label = "BackSlideRight"
    )
    val backAlpha by animateFloatAsState(
        targetValue = if (isBackVisible.value) 1f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "BackFadeIn"
    )

    LaunchedEffect(Unit) {
        isBarVisible.value = true
        isBackVisible.value = true
    }

    Column(modifier = Modifier.fillMaxWidth()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(TopSectionPurple)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        translationY = barOffsetY
                        this.alpha = barAlpha
                    }
                    .background(TopSectionPurple)
                    .padding(horizontal = 12.dp, vertical = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    navController.popBackStack()

                },
                    modifier =  Modifier.graphicsLayer {
                        translationX = backOffsetX
                        alpha = backAlpha
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_arrow_back_ios_24),
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                RoundedSearchBar(
                    query = query,
                    onQueryChange = setQuery,
                    onPrintClick = { /* Print handler */ }
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                items(
                    shipments.value
                        .filter {
                            it.title.contains(query, ignoreCase = true) ||
                                    it.trackingCode.contains(query, ignoreCase = true)
                        }
                        .take(20)
                ) { item ->
                    ShipmentCard(item)
                }
            }
        }
    }
}


@Composable
private fun ShipmentCard(item: ShipmentListItem) {
    val isVisible = remember { mutableStateOf(false) }

    val offsetY by animateFloatAsState(
        targetValue = if (isVisible.value) 0f else 50f,
        animationSpec = tween(durationMillis = 600),
        label = "SlideInY"
    )

    val alpha by animateFloatAsState(
        targetValue = if (isVisible.value) 1f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "FadeIn"
    )

    LaunchedEffect(Unit) {
        isVisible.value = true
    }

    Column (
        modifier = Modifier.fillMaxWidth()
            .graphicsLayer {
                translationY = offsetY
                this.alpha = alpha
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.Inventory,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(40.dp)
                    .background(TopSectionPurple, shape = CircleShape)
                    .padding(10.dp)
            )

            Spacer(modifier = Modifier.size(12.dp))
            Column {
                Text(item.title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                Text(item.trackingCode, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.outline)
                Text(item.route, style = MaterialTheme.typography.bodySmall)
            }
        }

        Divider(modifier = Modifier.fillMaxWidth(), thickness = 0.5.dp, color = Color.LightGray)
    }
}




package com.vectorinc.moniepointchallenge.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.vectorinc.moniepointchallenge.R
import com.vectorinc.moniepointchallenge.data.model.Shipment
import com.vectorinc.moniepointchallenge.data.model.VehicleOption
import com.vectorinc.moniepointchallenge.theme.MoniePointChallengeTheme
import com.vectorinc.moniepointchallenge.theme.OrangePrimary
import com.vectorinc.moniepointchallenge.theme.TopSectionPurple
import com.vectorinc.moniepointchallenge.ui.LightDivider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun TrackingDashboardScreen(
    navController: NavController,
    shipment: Shipment,
    vehicles: List<VehicleOption>,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val trackingVisible = remember { mutableStateOf(false) }
    val trackingOffsetY by animateFloatAsState(
        targetValue = if (trackingVisible.value) 0f else 50f,
        animationSpec = tween(700),
        label = "TrackingSlideIn"
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val animateOut = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(navBackStackEntry) {
        animateOut.value = false
    }

    val headerOffsetY by animateFloatAsState(
        targetValue = if (animateOut.value) -200f else 0f,
        animationSpec = tween(500),
        label = "TopHeaderExit"
    )

    val headerAlpha by animateFloatAsState(
        targetValue = if (animateOut.value) 0f else 1f,
        animationSpec = tween(500),
        label = "TopHeaderFade"
    )

    LaunchedEffect(Unit) {
        trackingVisible.value = true
    }
    Column(
        modifier
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        TopHeader(
            onSearchClick = {
                animateOut.value = true
                onSearchClick()
            },
            modifier = Modifier
                .graphicsLayer {
                    translationY = headerOffsetY
                    alpha = headerAlpha
                }
        )
        Box(modifier = Modifier.graphicsLayer {
            translationY = trackingOffsetY
            alpha = if (trackingVisible.value) 1f else 0f
        }) {
            TrackingSection(
                shipmentNumber = "NEJ20089934122231",
                sender = "Atlanta, 5243",
                receiver = "Chicago, 6342",
                eta = "2 day - 3 days",
                status = "Waiting to collect",
                onAddStop = { /* Handle action */ }
            )
        }

        AvailableVehiclesSection(vehicles)
    }
}

@Composable
private fun TopHeader(modifier: Modifier, onSearchClick: () -> Unit) {
    val (query, setQuery) = remember { mutableStateOf("") }
    val isVisible = remember { mutableStateOf(false) }

    val offsetY by animateFloatAsState(
        targetValue = if (isVisible.value) 0f else -100f,
        animationSpec = tween(durationMillis = 700),
        label = "SlideDownAnimation"
    )

    LaunchedEffect(Unit) {
        isVisible.value = true
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer { translationY = offsetY }
            .background(TopSectionPurple)
            .padding(16.dp)
            .clickable {
                onSearchClick()
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.user),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Row(horizontalArrangement = Arrangement.spacedBy(1.dp),
                        verticalAlignment = Alignment.CenterVertically ){
                        Image(
                            painter = painterResource(R.drawable.location),
                            contentDescription = "Location",
                            modifier = Modifier.size(20.dp).padding(5.dp)
                        )

                        Text("Your location", color = Color.White, style = MaterialTheme.typography.bodySmall)
                    }
                    Text("Wertheimer, Illinois", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
            Image(
                painter = painterResource(R.drawable.notification_bell),
                contentDescription = "Notifications",
                modifier = Modifier.size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .padding(5.dp)

            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        SearchPlaceholder(onClick = onSearchClick)

    }
}

@Composable
fun SearchPlaceholder(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = TopSectionPurple,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Enter the receipt number ...",
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(36.dp)
                .background(OrangePrimary, shape = CircleShape)
        ) {
            Image(
                painter = painterResource(R.drawable.barcode_scan),
                contentDescription = "Print Icon",
            )
        }
    }
}

@Composable
fun RoundedSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onPrintClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .background(Color.White, shape = RoundedCornerShape(24.dp))
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(8.dp))

        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp),
            decorationBox = { innerTextField ->
                if (query.isEmpty()) {
                    Text(
                        text = "Enter the receipt number ...",
                        color = Color.Gray
                    )
                }
                innerTextField()
            }
        )

        IconButton(
            onClick = onPrintClick,
            modifier = Modifier
                .size(36.dp)
                .background(OrangePrimary, shape = CircleShape)
        ) {
            Image(
                painter = painterResource(R.drawable.barcode_scan),
                contentDescription = "Print Icon",
            )
        }
    }
}

@Composable
fun TrackingSection(
    shipmentNumber: String,
    sender: String,
    receiver: String,
    eta: String,
    status: String,
    onAddStop: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Spacer(modifier = Modifier.height(10.dp))

        Text("Tracking", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(40.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Shipment Number", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                        Text(shipmentNumber, fontWeight = FontWeight.Bold)
                    }

                    Image(
                        painter = painterResource(R.drawable.forklift_img),
                        contentDescription = "Forklift",

                        modifier = Modifier
                            .size(40.dp)
                            .graphicsLayer {
                                this.scaleX = -1f
                            })
                }

                Spacer(modifier = Modifier.height(16.dp))

                LightDivider()

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        LabelWithValue(
                            label = "Sender",
                            value = sender,
                            iconColor = Color(0xFFFFE0B2),
                            iconRes = R.drawable.package_sent
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        LabelWithValue(
                            label = "Time",
                            value = eta,
                            iconColor = Color(0xFF81C784), // green
                            leadingDot = true,
                            iconRes = R.drawable.outline_timeline_24
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        LabelWithValue(
                            label = "Receiver",
                            value = receiver,
                            iconRes = R.drawable.package_received,
                            iconColor = Color(0xFFB2DFDB) // mint green
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        LabelWithValue(
                            label = "Status",
                            value = status,
                            iconRes = R.drawable.package_received,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                LightDivider()

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    "+ Add Stop",
                    color = OrangePrimary,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable { onAddStop() }
                )
            }
        }
    }
}


@Composable
private fun AvailableVehiclesSection(options: List<VehicleOption>) {
    Spacer(modifier = Modifier.height(30.dp))

    Text(
        "Available Vehicles",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    LazyRow(
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(options) { option ->
            VehicleCard(option)
        }
    }
}

@Composable
fun VehicleCard(vehicle: VehicleOption) {
    val slideIn = remember { mutableStateOf(false) }

    val imageOffsetX by animateFloatAsState(
        targetValue = if (slideIn.value) 0f else -30f,
        animationSpec = tween(durationMillis = 500),
        label = "ImageSlide"
    )

    val imageOffsetY by animateFloatAsState(
        targetValue = if (slideIn.value) 0f else -100f,
        animationSpec = tween(durationMillis = 500),
        label = "ImageSlide"
    )

    val textOffsetY by animateFloatAsState(
        targetValue = if (slideIn.value) 0f else 20f,
        animationSpec = tween(durationMillis = 500, delayMillis = 300),
        label = "TextSlide"
    )

    val textAlpha by animateFloatAsState(
        targetValue = if (slideIn.value) 1f else 0f,
        animationSpec = tween(durationMillis = 500, delayMillis = 300),
        label = "TextFade"
    )

    LaunchedEffect(Unit) {
        slideIn.value = true
    }

    Card(
        modifier = Modifier
            .width(250.dp)
            .height(210.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box {
            Image(
                painter = painterResource(id = vehicle.iconRes),
                contentDescription = vehicle.description,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(25.dp)
                    .graphicsLayer {
                        translationX = -imageOffsetY
                        translationY = imageOffsetX
                    }
            )

            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .graphicsLayer {
                        translationY = textOffsetY
                        alpha = textAlpha
                    },
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = vehicle.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = vehicle.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}


@Composable
fun LabelWithValue(
    label: String,
    value: String,
    iconColor: Color = Color.Transparent,
    iconRes: Int,
    leadingDot: Boolean = false
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (iconColor != Color.Transparent) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .background(iconColor, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }

            Spacer(Modifier.width(8.dp))
        } else if (leadingDot) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(Color.Green, shape = CircleShape)
            )
            Spacer(Modifier.width(8.dp))
        }

        Column {
            Text(label, style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray))
            Text(value, style = MaterialTheme.typography.bodyMedium)
        }
    }
}


private fun iconFromName(name: String): ImageVector = when (name) {
    "sailing" -> Icons.Default.Share
    "local_shipping" -> Icons.Default.Star
    "airplanemode_active" -> Icons.Default.Share
    else -> Icons.Default.Notifications
}

val sampleShipment = Shipment(
    number = "NEJ20089934122231",
    sender = "Jakarta, Indonesia",
    receiver = "Bandung, Indonesia",
    eta = "2 day - 3 days",
    status = "Waiting to collect"
)

val sampleVehicles = listOf(
    VehicleOption("Ocean Freight", "International", "sailing", R.drawable.ship),
    VehicleOption("Cargo Freight", "Reliable", "local_shipping", R.drawable.truck),
    VehicleOption("Air Freight", "Fast", "airplanemode_active", R.drawable.airplane)
)


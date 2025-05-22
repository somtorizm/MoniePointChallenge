package com.vectorinc.moniepointchallenge.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LinearScale
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.vectorinc.moniepointchallenge.R
import com.vectorinc.moniepointchallenge.theme.OrangePrimary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.core.graphics.scale
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.vectorinc.moniepointchallenge.data.model.ShipmentCardItem
import com.vectorinc.moniepointchallenge.theme.TopSectionPurple
import com.vectorinc.moniepointchallenge.viewmodel.ShipmentHistoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShipmentMapScreen(navController: NavController, viewModel: ShipmentHistoryViewModel = hiltViewModel(), modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberBottomSheetScaffoldState()
    val shipments by viewModel.shipments.collectAsState()
    val shipment = shipments.firstOrNull() ?: return

    LaunchedEffect(sheetState.bottomSheetState) {
        sheetState.bottomSheetState.expand()
    }

    val start = LatLng(37.7749, -122.4194)
    val end = LatLng(37.7849, -122.4094)

    val route = listOf(
        start,
        LatLng(37.7779, -122.4174),
        LatLng(37.7809, -122.4154),
        LatLng(37.7839, -122.4134),
        end
    )

    var index by remember { mutableIntStateOf(0) }
    val carPosition by remember { derivedStateOf { route[index] } }

    val context = LocalContext.current
    val density = LocalDensity.current

    val truckIcon = remember {
        val bmp    = BitmapFactory.decodeResource(context.resources, R.drawable.map_truck)
        val sizePx = with(density) { 32.dp.roundToPx() }
        val scaled = bmp.scale(sizePx, sizePx, false)
        BitmapDescriptorFactory.fromBitmap(scaled).apply {  }
    }
    val startIcon = remember {
        val bmp    = BitmapFactory.decodeResource(context.resources, R.drawable.map_pointer)
        val sizePx = with(density) { 35.dp.roundToPx() }
        val scaled = bmp.scale(sizePx, sizePx, false)
        BitmapDescriptorFactory.fromBitmap(scaled)
    }

    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(start, 14f)
    }

    LaunchedEffect(Unit) {
        while (index < route.lastIndex) {
            delay(1500)
            index++
        }
    }

    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetPeekHeight = 250.dp,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetContent = { TrackingSheetContent(shipment) }
    ) { padding ->
        Box(modifier = modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                Polyline(points = route, color = TopSectionPurple, width = 15f)


                Marker(
                    state = MarkerState(position = start),
                    icon  = startIcon,
                    anchor= Offset(0.5f, 0.5f)
                )

                Marker(
                    state = MarkerState(position = carPosition),
                    icon  = truckIcon,
                    anchor= Offset(0.5f, 0.5f)
                )
            }

            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(16.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .align(Alignment.TopStart)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }

            FloatingActionButton(
                onClick = {
                    scope.launch {
                        cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(carPosition, 14f))                  }
                },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .offset(y = (-120).dp)
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = "Recenter")
            }
        }
    }
}

@Composable
private fun TrackingSheetContent(shipment: ShipmentCardItem
) {
    Column(modifier = Modifier, verticalArrangement = Arrangement.Center) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        text = "Estimated Time",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = "09:50 PM",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(
                            color = TopSectionPurple,
                            shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.AccessTime,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "7 mins",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }
            }


        }

        Spacer(modifier = Modifier.height(24.dp))

        TimelineItem(
            icon = R.drawable.truck_drawer,
            title = "Arriving today!",
            detail = shipment.subtitle,
            date = "Feb 21 at 9:30pm",
            showTrails = true,
            highlight = true)


        TimelineItem(icon = R.drawable.shipped_image, title = "Has been Shipped", date = "Feb 20", showTrails = true,
            detail = "This parcel is waiting for collection")
        TimelineItem(
            icon = R.drawable.box,
            title = "Has been sent",
            date = "Feb 17",
            detail = "Your parcel is waiting for collection"
        )

        Spacer(modifier = Modifier.height(10.dp))

        DriverInfo()
    }
}

@Composable
private fun TimelineItem(
    icon: Int,
    title: String,
    date: String,
    showTrails: Boolean = false,
    highlight: Boolean = false,
    detail: String? = null
) {
    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 6.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(if (highlight) Color(0xFFE8E8E8) else Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 10.dp, vertical = if (highlight) 20.dp else 5.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(icon),
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                            modifier = Modifier.size(60.dp)
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = date,
                                modifier = Modifier.offset(y = (-5).dp),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))

                        detail?.let { Text(it, style = MaterialTheme.typography.bodySmall) }
                    }
                }
            }
        }
    }

    if (showTrails) {
        Icon(Icons.Filled.LinearScale, contentDescription = null, modifier = Modifier.offset(x = 30.dp, y = (10).dp).rotate(90f), tint = Color(0xFFD9D9D9))
    }
}

@Composable
private fun DriverInfo() {
    Column(modifier = Modifier.padding(horizontal = 20.dp)){
        Divider(thickness = 0.5.dp)
        Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.user),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp).clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("Ezinwa Victor", fontWeight = FontWeight.Bold)
                    Text("Driver", style = MaterialTheme.typography.bodySmall)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Icon(Icons.Default.Chat, contentDescription = "Chat")
                }

                Spacer(modifier = Modifier.width(10.dp))
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(OrangePrimary)
                ) {
                    Icon(Icons.Default.Call, contentDescription = "Call", tint = Color.White)
                }
            }
        }
    }

}
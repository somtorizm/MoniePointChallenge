package com.vectorinc.moniepointchallenge.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vectorinc.moniepointchallenge.model.Shipment
import com.vectorinc.moniepointchallenge.model.VehicleOption
import com.vectorinc.moniepointchallenge.theme.MoniePointChallengeTheme
import com.vectorinc.moniepointchallenge.theme.OrangePrimary
import com.vectorinc.moniepointchallenge.theme.TopSectionPurple


@Composable
fun TrackingDashboardScreen(
    shipment: Shipment,
    vehicles: List<VehicleOption>,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .verticalScroll(rememberScrollState())
    ) {
        TopHeader(onSearchClick)
        TrackingSection(shipment)
        AvailableVehiclesSection(vehicles)
    }
}

@Composable
private fun TopHeader(onSearchClick: () -> Unit) {
    val (query, setQuery) = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(TopSectionPurple)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Wertheimer, Illinois", color = Color.White)
            }
            FilledIconButton(
                onClick = onSearchClick,
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = OrangePrimary)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = query,
            onValueChange = setQuery,
            placeholder = { Text("Enter the receipt number...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun TrackingSection(shipment: Shipment) {
    Text(
        "Tracking",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
    Text(
        shipment.number,
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = OrangePrimary
                )
                Spacer(Modifier.width(8.dp))
                Text(shipment.sender)
            }
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AccountCircle, contentDescription = null, tint = OrangePrimary)
                Spacer(Modifier.width(8.dp))
                Text(shipment.receiver)
            }
            Spacer(Modifier.height(8.dp))
            Text(shipment.eta)
            Text(shipment.status)
            Spacer(Modifier.height(8.dp))
            Text(
                "+ Add Stop",
                color = OrangePrimary,
                modifier = Modifier.clickable { },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun AvailableVehiclesSection(options: List<VehicleOption>) {
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
private fun VehicleCard(option: VehicleOption) {
    Card {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                iconFromName(option.iconName),
                contentDescription = null,
                tint = OrangePrimary,
                modifier = Modifier.size(40.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(option.name, fontWeight = FontWeight.Bold)
            Text(option.description, style = MaterialTheme.typography.bodySmall)
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
    VehicleOption("Ocean Freight", "International", "sailing"),
    VehicleOption("Cargo Freight", "Reliable", "local_shipping"),
    VehicleOption("Air Freight", "Fast", "airplanemode_active")
)

@Preview(showBackground = true)
@Composable
private fun TrackingDashboardPreview() {
    MoniePointChallengeTheme  {
        TrackingDashboardScreen(sampleShipment, sampleVehicles, {})
    }
}
package com.vectorinc.moniepointchallenge.ui.shipment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShipmentHistoryScreen(shipments: List<Shipment>, modifier: Modifier = Modifier) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val completedCount = shipments.count { it.status == ShipmentStatus.Completed }
    val inProgressCount = shipments.count { it.status == ShipmentStatus.InProgress }

    Column(modifier = modifier) {
        TopAppBar(
            title = { Text("Shipment history", color = Color.White) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF6A1B9A)
            )
        )

        val tabs = listOf(
            "All (${shipments.size})",
            "Completed ($completedCount)",
            "In Progress ($inProgressCount)"
        )
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        val filtered = when (selectedTab) {
            1 -> shipments.filter { it.status == ShipmentStatus.Completed }
            2 -> shipments.filter { it.status == ShipmentStatus.InProgress }
            else -> shipments
        }

        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(filtered) { shipment ->
                ShipmentCard(shipment)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun ShipmentCard(shipment: Shipment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            StatusBadge(shipment.status)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Arriving today!", style = MaterialTheme.typography.titleMedium)
            Text(shipment.id, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(shipment.price, fontWeight = FontWeight.Bold)
                    Text(shipment.date, style = MaterialTheme.typography.bodySmall)
                }
                AsyncImage(
                    model = shipment.iconUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@Composable
private fun StatusBadge(status: ShipmentStatus) {
    val background = when (status) {
        ShipmentStatus.Completed -> Color(0xFF4CAF50)
        ShipmentStatus.InProgress -> Color(0xFFFFA000)
        ShipmentStatus.Pending -> Color(0xFF9E9E9E)
        ShipmentStatus.Loading -> Color(0xFF2196F3)
    }
    Text(
        text = status.name.lowercase().replaceFirstChar { it.titlecase() },
        color = Color.White,
        modifier = Modifier
            .background(background, shape = MaterialTheme.shapes.small)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    )
}
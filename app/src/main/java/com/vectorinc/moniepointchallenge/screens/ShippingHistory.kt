package com.vectorinc.moniepointchallenge.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vectorinc.moniepointchallenge.R
import com.vectorinc.moniepointchallenge.data.model.ShipmentCardItem
import com.vectorinc.moniepointchallenge.data.model.ShipmentStatus
import com.vectorinc.moniepointchallenge.theme.TopSectionPurple
import com.vectorinc.moniepointchallenge.viewmodel.ShipmentHistoryViewModel

@Composable
fun ShipmentHistoryScreen(
    navController: NavController,
    viewModel: ShipmentHistoryViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableStateOf(ShipmentStatus.ALL) }
    val shipments by viewModel.shipments.collectAsState()
    val filtered = shipments.filter { selectedTab == ShipmentStatus.ALL || it.status == selectedTab }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(TopSectionPurple)
                .padding(top = 40.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("Shipment history", style = MaterialTheme.typography.titleLarge.copy(color = Color.White))
            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .background(TopSectionPurple)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ShipmentStatus.values().forEach { status ->
                val isSelected = selectedTab == status
                val count = shipments.count { it.status == status || status == ShipmentStatus.ALL }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isSelected) Color.White else Color.Transparent)
                        .clickable { selectedTab = status }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "${status.label} ($count)",
                        color = if (isSelected) TopSectionPurple else Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Text(
            text = "Shipments",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filtered) { item ->
                ShipmentItemCard(item)
            }
        }
    }
}

@Composable
fun ShipmentItemCard(item: ShipmentCardItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                StatusLabel(item.status)
                Text(item.title, fontWeight = FontWeight.Bold)
                Text(item.subtitle, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Text(item.price, color = TopSectionPurple, fontWeight = FontWeight.Bold)
                Text(item.date, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }

            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                painter = painterResource(id = item.imageRes),
                contentDescription = "Shipment Image",
                tint = Color.Unspecified,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Composable
fun StatusLabel(status: ShipmentStatus) {
    val (bgColor, text) = when (status) {
        ShipmentStatus.IN_PROGRESS -> Color(0xFFD0F5E2) to "in-progress"
        ShipmentStatus.PENDING -> Color(0xFFFFF4D1) to "pending"
        ShipmentStatus.COMPLETED -> Color(0xFFE0F7FA) to "completed"
        ShipmentStatus.CANCELLED -> Color(0xFFFFE4E1) to "cancelled"
        else -> Color.LightGray to "unknown"
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text = text, color = Color.Black, style = MaterialTheme.typography.labelSmall)
    }
}

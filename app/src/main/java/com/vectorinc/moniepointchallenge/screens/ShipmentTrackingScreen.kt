package com.vectorinc.moniepointchallenge.screens

import com.vectorinc.moniepointchallenge.viewmodel.ShipmentTrackingViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Print
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vectorinc.moniepointchallenge.model.ShipmentListItem
import com.vectorinc.moniepointchallenge.theme.MoniePointChallengeTheme
import com.vectorinc.moniepointchallenge.theme.TopSectionPurple


@Composable
fun ShipmentTrackingScreen(
    viewModel: ShipmentTrackingViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val shipments = viewModel.shipments
    Column(modifier = modifier) {
        SearchBar()
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(shipments) { item ->
                ShipmentCard(item)
            }
        }
    }
}

@Composable
private fun SearchBar() {
    val (query, setQuery) = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(TopSectionPurple)
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = setQuery,
            placeholder = { Text("#NEJ200899") },
            trailingIcon = {
                IconButton(onClick = { }) {
                    Icon(Icons.Filled.Print, contentDescription = "Print", tint = Color.White)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent

            ),
            shape = MaterialTheme.shapes.small
        )
    }
}

@Composable
private fun ShipmentCard(item: ShipmentListItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.Inventory,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.size(12.dp))
            Column {
                Text(item.title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                Text(item.trackingCode, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.outline)
                Text(item.route, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShipmentTrackingScreenPreview() {
    val sample = listOf(
        ShipmentListItem("Summer linen jacket", "#NEJ20089934122231", "Barcelona \u2192 Paris"),
        ShipmentListItem("Winter coat", "#NEJ20089934122232", "London \u2192 Berlin"),
        ShipmentListItem("Sneakers", "#NEJ20089934122233", "Rome \u2192 Madrid")
    )
    MoniePointChallengeTheme {
        ShipmentTrackingScreen(
            viewModel = ShipmentTrackingViewModel(sample)
        )
    }
}



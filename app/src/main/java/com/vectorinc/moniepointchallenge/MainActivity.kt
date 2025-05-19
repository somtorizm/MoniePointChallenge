package com.vectorinc.moniepointchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vectorinc.moniepointchallenge.ui.shipment.ShipmentHistoryScreen
import com.vectorinc.moniepointchallenge.ui.shipment.sampleShipments
import com.vectorinc.moniepointchallenge.ui.theme.MoniePointChallengeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoniePointChallengeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ShipmentHistoryScreen(
                        shipments = sampleShipments,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

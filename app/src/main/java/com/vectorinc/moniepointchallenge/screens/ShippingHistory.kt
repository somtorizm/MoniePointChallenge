package com.vectorinc.moniepointchallenge.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cached
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vectorinc.moniepointchallenge.R
import com.vectorinc.moniepointchallenge.data.model.ShipmentCardItem
import com.vectorinc.moniepointchallenge.data.model.ShipmentStatus
import com.vectorinc.moniepointchallenge.theme.TopSectionPurple
import com.vectorinc.moniepointchallenge.viewmodel.ShipmentHistoryViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ShipmentHistoryScreen(
    navController: NavController,
    viewModel: ShipmentHistoryViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableStateOf(ShipmentStatus.ALL) }
    val shipments by viewModel.shipments.collectAsState()
    val filtered = shipments.filter { selectedTab == ShipmentStatus.ALL || it.status == selectedTab }
    val scope = rememberCoroutineScope()

    val headerContainerOffsetY = remember { Animatable(107f) }
    val topBarAlpha = remember { Animatable(0f) }
    val backButtonOffsetX = remember { Animatable(-100f) }
    val tabsOffsetY = remember { Animatable(70f) }
    val tabsAlpha = remember { Animatable(0f) }
    val tabsOffsetX = remember { Animatable(300f) }
    val shipmentOffsetY = remember { Animatable(70f) }
    val shipmentAlpha = remember { Animatable(0f) }
    val shipmentTitleOffsetX = remember { Animatable(300f) }
    val shipmentTitleAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        launch { headerContainerOffsetY.animateTo(0f, tween(durationMillis = 800, easing = LinearOutSlowInEasing)) }
        launch { topBarAlpha.animateTo(1f, tween(700)) }
        launch { backButtonOffsetX.animateTo(0f, tween(700, easing = LinearOutSlowInEasing)) }
        launch { tabsOffsetY.animateTo(0f, tween(700, easing = LinearOutSlowInEasing)) }
        launch { tabsOffsetX.animateTo(0f, tween(700, easing = LinearOutSlowInEasing)) }
        launch { tabsAlpha.animateTo(1f, tween(700)) }
        launch { shipmentTitleOffsetX.animateTo(0f, tween(700, easing = LinearOutSlowInEasing)) }
        launch { shipmentTitleAlpha.animateTo(1f, tween(700)) }
        launch { shipmentOffsetY.animateTo(0f, tween(700, easing = LinearOutSlowInEasing)) }
        launch { shipmentAlpha.animateTo(1f, tween(700)) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F7F7))
    ) {
        Box() {
            Box(modifier = Modifier.background(TopSectionPurple).matchParentSize())
            Column(
                modifier = Modifier
                    .background(TopSectionPurple)
                    .graphicsLayer {
                        translationY = headerContainerOffsetY.value
                    }
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp)
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.graphicsLayer {
                            translationX = backButtonOffsetX.value
                        }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.outline_arrow_back_ios_24),
                            contentDescription = "Back"
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Shipment History",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }

                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            translationY = tabsOffsetY.value
                            translationX = tabsOffsetX.value
                            alpha = tabsAlpha.value
                        }
                ) {
                    AnimatedShipmentTabs(
                        selectedTab = selectedTab,
                        onTabSelected = { selectedTab = it },
                        shipments = shipments
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .graphicsLayer {
                    translationY = shipmentOffsetY.value
                    alpha = shipmentAlpha.value
                }
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        translationY = shipmentOffsetY.value
                        alpha = shipmentAlpha.value
                    }
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Shipments",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .graphicsLayer {
                                    translationX = shipmentTitleOffsetX.value
                                    alpha = shipmentTitleAlpha.value
                                }
                                .padding(horizontal = 20.dp, vertical = 10.dp)
                        )
                    }

                    itemsIndexed(filtered) { index, item ->
                        ShipmentItemCard(item = item, index = index)
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0xFFF8F7F7))
                            )
                        )
                )
            }

        }
    }
}

@Composable
fun ShipmentItemCard(item: ShipmentCardItem, index: Int) {
    val offsetY = remember { Animatable(30f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        delay(index * 10L)
        launch {
            offsetY.animateTo(0f, animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy))
        }
        launch {
            alpha.animateTo(1f, animationSpec = tween(durationMillis = 200))
        }
    }

    Card(
        modifier = Modifier
            .graphicsLayer {
                translationY = offsetY.value
                this.alpha = alpha.value
            }
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(0.2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                StatusLabel(item.status)
                Spacer(modifier = Modifier.height(10.dp))
                Text(item.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(10.dp))
                Text(item.subtitle, style = MaterialTheme.typography.bodySmall, color = Color.Gray, fontSize = 15.sp)
                Spacer(modifier = Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    Text(item.price, color = TopSectionPurple, fontWeight = FontWeight.Bold)
                    Icon(imageVector = Icons.Filled.Circle, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(8.dp))
                    Text(item.date, style = MaterialTheme.typography.bodyMedium, color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.width(12.dp))
            Image(
                painter = painterResource(id = R.drawable.box),
                contentDescription = "Shipment Image",
                modifier = Modifier.size(100.dp)
            )
        }
    }
}


@Composable
fun AnimatedShipmentTabs(
    selectedTab: ShipmentStatus,
    onTabSelected: (ShipmentStatus) -> Unit,
    shipments: List<ShipmentCardItem>
) {
    val tabUnderlineColor = Color(0xFFFF9800)
    val activeBadgeColor = Color(0xFFFF9800)
    val inactiveBadgeColor = Color(0xFFF2EFFC).copy(alpha = 0.2f)

    val coroutineScope = rememberCoroutineScope()
    val tabWidths = remember { mutableMapOf<ShipmentStatus, Float>() }
    val tabOffsets = remember { mutableMapOf<ShipmentStatus, Float>() }

    val listState = rememberLazyListState()
    val tabList = ShipmentStatus.values().toList()

    val animatedTabOffset = remember { Animatable(0f) }
    val animatedTabWidth = remember { Animatable(0f) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(TopSectionPurple)
    ) {
        LazyRow(
            state = listState,
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            itemsIndexed(tabList) { index, status ->
                val count = shipments.count { it.status == status || status == ShipmentStatus.ALL }
                val isSelected = selectedTab == status
                val blinkAlpha = remember { Animatable(1f) }

                LaunchedEffect(isSelected) {
                    if (isSelected) {
                        blinkAlpha.snapTo(0.2f)
                        blinkAlpha.animateTo(1f, tween(700))
                    }
                }

                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .onGloballyPositioned { coordinates ->
                            val offset = coordinates.positionInParent().x
                            val width = coordinates.size.width.toFloat()

                            tabWidths[status] = width
                            tabOffsets[status] = offset

                            if (isSelected) {
                                coroutineScope.launch {
                                    animatedTabOffset.animateTo(offset, tween(durationMillis = 300))
                                    animatedTabWidth.animateTo(width, tween(durationMillis = 300))
                                }
                            }
                        }
                        .clickable {
                            coroutineScope.launch {
                                listState.animateScrollToItem(index)
                            }
                            onTabSelected(status)
                        }
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Text(
                                text = status.label,
                                color = if (isSelected) Color.White else Color.LightGray,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .graphicsLayer { alpha = blinkAlpha.value }
                                    .clip(CircleShape)
                                    .background(if (isSelected) activeBadgeColor else inactiveBadgeColor)
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = count.toString(),
                                    color = Color.White,
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(animatedTabOffset.value.toInt(), 0) }
                .padding(top = 4.dp)
                .height(3.dp)
                .width(with(LocalDensity.current) { animatedTabWidth.value.toDp() })
                .background(tabUnderlineColor)
                .align(Alignment.BottomStart)
        )
    }
}

@Composable
fun StatusLabel(status: ShipmentStatus) {
    val statusStyle = when (status) {
        ShipmentStatus.IN_PROGRESS -> ShipmentStatusStyle(
            backgroundColor = Color(0xFFEAFBF3),
            textColor = Color(0xFF12B76A),
            icon = Icons.Default.Cached,
            label = "in-progress"
        )
        ShipmentStatus.PENDING -> ShipmentStatusStyle(
            backgroundColor = Color(0xFFFFF4D1),
            textColor = Color(0xFFF79009),
            icon = Icons.Default.Schedule,
            label = "pending"
        )

        ShipmentStatus.COMPLETED -> ShipmentStatusStyle(
            backgroundColor = Color(0xFFEAFBF3),
            textColor = Color(0xFF12B76A),
            icon = Icons.Default.CheckCircle,
            label = "completed"
        )
        ShipmentStatus.CANCELLED -> ShipmentStatusStyle(
            backgroundColor = Color(0xFFFFE4E1),
            textColor = Color(0xFFF04438),
            icon = Icons.Default.Cancel,
            label = "cancelled"
        )
        else -> ShipmentStatusStyle(
            backgroundColor = Color(0xFFF2F4F7),
            textColor = Color(0xFF344054),
            icon = Icons.Default.HourglassEmpty,
            label = "loading"
        )
    }




    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0x127B7777))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(statusStyle.backgroundColor)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Icon(
                imageVector = statusStyle.icon,
                contentDescription = null,
                tint = statusStyle.textColor,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = statusStyle.label,
                color = statusStyle.textColor,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

data class ShipmentStatusStyle(
    val backgroundColor: Color,
    val textColor: Color,
    val icon: ImageVector,
    val label: String
)

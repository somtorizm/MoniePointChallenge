package com.vectorinc.moniepointchallenge.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.HorizontalRule
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.MoveToInbox
import androidx.compose.material.icons.filled.Outbox
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vectorinc.moniepointchallenge.R
import com.vectorinc.moniepointchallenge.theme.OrangePrimary
import com.vectorinc.moniepointchallenge.theme.SelectedPurple
import com.vectorinc.moniepointchallenge.theme.TopSectionPurple
import com.vectorinc.moniepointchallenge.ui.DefaultAppBar
import com.vectorinc.moniepointchallenge.ui.Screen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CalculateScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    var sender by remember { mutableStateOf("") }
    var receiver by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    val animateIn = remember { mutableStateOf(false) }

    var packagingExpanded by remember { mutableStateOf(false) }
    val packagingOptions = listOf("Box", "Envelope", "Pallet")
    var selectedPackaging by remember { mutableStateOf(packagingOptions.first()) }


    LaunchedEffect(Unit) {
        animateIn.value = true
    }

    val buttonOffsetY by animateFloatAsState(
        targetValue = if (animateIn.value) 0f else 100f,
        animationSpec = tween(500, delayMillis = 500),
        label = "ButtonOffsetY"
    )
    val buttonAlpha by animateFloatAsState(
        targetValue = if (animateIn.value) 1f else 0f,
        animationSpec = tween(500, delayMillis = 500),
        label = "ButtonAlpha"
    )

    LaunchedEffect(Unit) {
        animateIn.value = true
    }


    val categories = listOf(
        "Documents", "Glass", "Liquid", "Food", "Electronic", "Product", "Others"
    )

    val formOffsetY by animateFloatAsState(
        targetValue = if (animateIn.value) 0f else 40f,
        animationSpec = tween(500),
        label = "FormOffsetY"
    )

    val formAlpha by animateFloatAsState(
        targetValue = if (animateIn.value) 1f else 0f,
        animationSpec = tween(500),
        label = "FormAlpha"
    )

    val senderOffsetY by animateFloatAsState(
        targetValue = if (animateIn.value) 0f else 40f,
        animationSpec = tween(500, delayMillis = 200),
        label = "SenderOffsetY"
    )
    val senderAlpha by animateFloatAsState(
        targetValue = if (animateIn.value) 1f else 0f,
        animationSpec = tween(500, delayMillis = 200),
        label = "SenderAlpha"
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "scaleAnimation"
    )

    LaunchedEffect(Unit) {
        animateIn.value = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8F7F7))
    ) {
        DefaultAppBar( onBackClick = {
            navController.popBackStack()
        }, "Calculate")

        Column(
            modifier = Modifier
                .graphicsLayer {
                    translationY = formOffsetY
                    alpha = formAlpha
                }
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .weight(1f, fill = true)
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                "Destination",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(0.2.dp),
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    TextField(
                        value = sender,
                        onValueChange = { sender = it },
                        label = { Text("Sender location") },
                        leadingIcon = {
                            Row {
                                Spacer(modifier = Modifier.width(10.dp))

                                Icon(
                                    Icons.Default.Outbox,
                                    contentDescription = null,
                                    tint = Color.Gray
                                )

                                Spacer(modifier = Modifier.width(10.dp))

                                Icon(
                                    Icons.Default.HorizontalRule,
                                    contentDescription = null,
                                    modifier = Modifier.rotate(90f),
                                    tint = Color.Gray
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xFFF5F5F5),
                            focusedContainerColor = Color(0xFFF5F5F5),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                            .graphicsLayer {
                                translationY = senderOffsetY
                                alpha = senderAlpha
                            }
                    )


                    Spacer(modifier = Modifier.height(12.dp))

                    TextField(
                        value = sender,
                        onValueChange = { sender = it },
                        label = { Text("Receiver location") },
                        leadingIcon = {
                            Row {
                                Spacer(modifier = Modifier.width(10.dp))
                                Icon(
                                    Icons.Default.MoveToInbox,
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Icon(
                                    Icons.Default.HorizontalRule,
                                    contentDescription = null,
                                    modifier = Modifier.rotate(90f),
                                    tint = Color.Gray
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xFFF5F5F5),
                            focusedContainerColor = Color(0xFFF5F5F5),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    TextField(
                        value = weight,
                        onValueChange = { weight = it },
                        label = { Text("Approx Weight") },
                        leadingIcon = {
                            Row {
                                Spacer(modifier = Modifier.width(10.dp))
                                Icon(
                                    Icons.Default.Scale,
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Icon(
                                    Icons.Default.HorizontalRule,
                                    contentDescription = null,
                                    modifier = Modifier.rotate(90f),
                                    tint = Color.Gray
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xFFF5F5F5),
                            focusedContainerColor = Color(0xFFF5F5F5),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                "Packaging",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("What are you sending?", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                elevation = CardDefaults.cardElevation(0.2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                ExposedDropdownMenuBox(
                    expanded = packagingExpanded,
                    onExpandedChange = { packagingExpanded = !packagingExpanded }
                ) {
                    TextField(
                        value = selectedPackaging,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Packaging") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = packagingExpanded)
                        },
                        leadingIcon = {
                            Row {
                                Spacer(modifier = Modifier.width(20.dp))
                                Icon(
                                    Icons.Default.Inventory2,
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Icon(
                                    Icons.Default.HorizontalRule,
                                    contentDescription = null,
                                    modifier = Modifier.rotate(90f),
                                    tint = Color.Gray
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = packagingExpanded,
                        onDismissRequest = { packagingExpanded = false }
                    ) {
                        packagingOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectedPackaging = option
                                    packagingExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                "Categories",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("What are you sending?", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(20.dp))

            CategorySelector(categories = categories)

            Spacer(modifier = Modifier.height(20.dp))

            AnimatedCalculateButton(
                onClick = {
                    navController.navigate(Screen.ShippingCalculateResult.route) {
                        launchSingleTop = true
                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategorySelector(
    categories: List<String>,
    modifier: Modifier = Modifier
) {
    val (selectedCategory, setSelectedCategory) = remember { mutableStateOf<String?>(null) }

    val offsetX = remember { Animatable(300f) }

    LaunchedEffect(Unit) {
        offsetX.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 600, easing = LinearOutSlowInEasing)
        )
    }

    Column(modifier = modifier) {
        Box(
            modifier = Modifier.graphicsLayer {
                translationX = offsetX.value
            }
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                categories.forEach { category ->
                    val isSelected = category == selectedCategory

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                if (isSelected) SelectedPurple else Color.White
                            )
                            .border(
                                width = 1.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable { setSelectedCategory(category) }
                            .padding(horizontal = 12.dp)
                    ) {
                        if (isSelected) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selected",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                        }

                        Text(
                            text = category,
                            color = if (isSelected) Color.White else Color.Black,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Composable
fun AnimatedCalculateButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonOffsetY: Float = 0f,
    buttonAlpha: Float = 1f
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.35f else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "scaleAnimation"
    )

    Button(
        onClick = onClick,
        interactionSource = interactionSource,
        colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
        modifier = modifier
            .height(58.dp)
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                translationY = buttonOffsetY
                alpha = buttonAlpha
            }
    ) {
        Text("Calculate", color = Color.White)
    }
}




package com.rewire.app.ui.interventions

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Air
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Memory
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rewire.app.ui.theme.*
import kotlinx.coroutines.delay
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import kotlinx.coroutines.launch

// Enum to manage which screen we are currently viewing
enum class InterventionState {
    SELECTION,
    MEMORY_GAME,
    BREATHING
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun InterventionScreen(
    innerPadding: PaddingValues
) {
    var currentState by remember { mutableStateOf(InterventionState.SELECTION) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
            .padding(innerPadding)
    ) {
        AnimatedContent(
            targetState = currentState,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) with fadeOut(animationSpec = tween(300))
            },
            label = "InterventionTransition"
        ) { state ->
            when (state) {
                InterventionState.SELECTION -> {
                    InterventionSelection(
                        onSelect = { currentState = it }
                    )
                }
                InterventionState.MEMORY_GAME -> {
                    MemoryExercise(
                        onExit = { currentState = InterventionState.SELECTION }
                    )
                }
                InterventionState.BREATHING -> {
                    BreathingExercise(
                        onExit = { currentState = InterventionState.SELECTION }
                    )
                }
            }
        }
    }
}

@Composable
fun InterventionSelection(
    onSelect: (InterventionState) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Practice",
                color = TextPrimary,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Try the awareness exercises used during active sessions.",
                color = TextMuted,
                fontSize = 15.sp,
                lineHeight = 22.sp
            )
        }

        item {
            InterventionCard(
                title = "Level 1: Number Memory",
                description = "A quick cognitive puzzle designed to break autopilot scrolling behavior.",
                icon = Icons.Rounded.Memory,
                iconColor = Color(0xFF20D89B),
                onClick = { onSelect(InterventionState.MEMORY_GAME) }
            )
        }

        item {
            InterventionCard(
                title = "Level 2: Breathe & Reflect",
                description = "A guided breathing exercise to lower heart rate and restore conscious intention.",
                icon = Icons.Rounded.Air,
                iconColor = Color(0xFF5D9CFF),
                onClick = { onSelect(InterventionState.BREATHING) }
            )
        }
    }
}

@Composable
fun InterventionCard(
    title: String,
    description: String,
    icon: ImageVector,
    iconColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(iconColor.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(26.dp)
                )
            }
            Spacer(modifier = Modifier.width(18.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = TextPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    color = TextMuted,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

// --- LEVEL 1: MEMORY EXERCISE ---

@Composable
fun MemoryExercise(onExit: () -> Unit) {
    var sequence by remember { mutableStateOf(generateRandomSequence()) }
    var userInput by remember { mutableStateOf("") }
    var showSequence by remember { mutableStateOf(true) }
    var isSuccess by remember { mutableStateOf<Boolean?>(null) }

    // Timer to hide the sequence
    LaunchedEffect(sequence) {
        showSequence = true
        userInput = ""
        isSuccess = null
        delay(2500) // Show numbers for 2.5 seconds
        showSequence = false
    }

    // Check input completion
    LaunchedEffect(userInput) {
        if (!showSequence && userInput.length == 4) {
            if (userInput == sequence) {
                isSuccess = true
                delay(1000)
                onExit() // Return to menu on success
            } else {
                isSuccess = false
                delay(1000)
                sequence = generateRandomSequence() // Restart on fail
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBar(title = "Level 1: Focus", onExit = onExit)

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = when {
                showSequence -> "Memorize this sequence"
                isSuccess == true -> "Autopilot Broken."
                isSuccess == false -> "Incorrect. Let's try again."
                else -> "Enter the sequence"
            },
            color = if (isSuccess == false) Color(0xFFFF6B6B) else TextMuted,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Display the sequence or the masked input
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            for (i in 0 until 4) {
                val charToShow = when {
                    showSequence -> sequence[i].toString()
                    i < userInput.length -> userInput[i].toString()
                    else -> ""
                }
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(SurfaceVariant, RoundedCornerShape(16.dp))
                        .border(
                            width = 2.dp,
                            color = if (i == userInput.length && !showSequence) Primary else Color.Transparent,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = charToShow,
                        color = TextPrimary,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Custom Numpad
        if (!showSequence && isSuccess == null) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                items((1..9).toList() + listOf(null, 0, null)) { number ->
                    if (number != null) {
                        NumpadButton(number.toString()) {
                            if (userInput.length < 4) userInput += number.toString()
                        }
                    } else {
                        Spacer(modifier = Modifier.size(72.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
fun NumpadButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(Surface)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = TextPrimary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

private fun generateRandomSequence(): String {
    return (1..4).map { (0..9).random() }.joinToString("")
}

// --- LEVEL 2: BREATHING EXERCISE ---

enum class BreathingPhase {
    INHALE, EXHALE, PAUSED
}

@Composable
fun BreathingExercise(onExit: () -> Unit) {
    var isPaused by remember { mutableStateOf(false) }
    var phase by remember { mutableStateOf(BreathingPhase.INHALE) }

    // Timer state (mocked to start at 9:27 like the image)
    var timeRemaining by remember { mutableStateOf(9 * 60 + 27) }

    // Controls the breathing loop and timer
    LaunchedEffect(isPaused) {
        if (!isPaused) {
            // Timer countdown
            launch {
                while (timeRemaining > 0) {
                    delay(1000)
                    timeRemaining--
                }
            }

            // Breathing cycle
            while (true) {
                phase = BreathingPhase.INHALE
                delay(4000) // 4 seconds inhale
                phase = BreathingPhase.EXHALE
                delay(4000) // 4 seconds exhale
            }
        } else {
            phase = BreathingPhase.PAUSED
        }
    }

    // Smooth Background Color Transition
    val backgroundColor by animateColorAsState(
        targetValue = when (phase) {
            BreathingPhase.INHALE -> Color(0xFF86A573) // Sage Green
            BreathingPhase.EXHALE -> Color(0xFFF28B36) // Warm Orange
            BreathingPhase.PAUSED -> Color(0xFFF6F4F0) // Off-white / Cream
        },
        animationSpec = tween(durationMillis = 2000),
        label = "BackgroundColor"
    )

    // Text Color shifts based on background darkness
    val textColor = if (phase == BreathingPhase.PAUSED) Color(0xFF4A3E3D) else Color.White

    // Smooth animation for the concentric circles scaling
    val scale by animateFloatAsState(
        targetValue = when (phase) {
            BreathingPhase.INHALE -> 1f
            BreathingPhase.EXHALE -> 0.4f
            BreathingPhase.PAUSED -> 0.4f
        },
        animationSpec = tween(durationMillis = 4000, easing = LinearOutSlowInEasing),
        label = "BreathingScale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(24.dp)
    ) {
        // TOP BAR: Exit Button & Sound Pill
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onExit) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Exit",
                    tint = textColor
                )
            }

            // "Chirping Birds" Sound Selector Pill
            Row(
                modifier = Modifier
                    .border(1.dp, textColor.copy(alpha = 0.5f), RoundedCornerShape(50.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.MusicNote,
                    contentDescription = null,
                    tint = textColor,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Chirping Birds",
                    color = textColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowDown,
                    contentDescription = null,
                    tint = textColor,
                    modifier = Modifier.size(18.dp)
                )
            }

            // Invisible box for balancing the row layout
            Spacer(modifier = Modifier.size(48.dp))
        }

        // CENTER: Expanding/Contracting Rings & Text
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (phase != BreathingPhase.PAUSED) {
                // Outer Ring
                Box(
                    modifier = Modifier
                        .size(350.dp)
                        .scale(scale * 1.5f)
                        .background(Color.White.copy(alpha = 0.05f), CircleShape)
                )
                // Middle Ring
                Box(
                    modifier = Modifier
                        .size(250.dp)
                        .scale(scale * 1.2f)
                        .background(Color.White.copy(alpha = 0.1f), CircleShape)
                )
                // Inner Ring
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .scale(scale)
                        .background(Color.White.copy(alpha = 0.15f), CircleShape)
                )
            }

            // Phase Text
            Text(
                text = when (phase) {
                    BreathingPhase.INHALE -> "Breathe In..."
                    BreathingPhase.EXHALE -> "Breathe out..."
                    BreathingPhase.PAUSED -> "Paused"
                },
                color = textColor,
                fontSize = 32.sp,
                fontWeight = FontWeight.Medium
            )
        }

        // BOTTOM: Timer & Play/Pause Button
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Format time (e.g., 09:27)
            val minutes = timeRemaining / 60
            val seconds = timeRemaining % 60
            Text(
                text = String.format("%02d:%02d", minutes, seconds),
                color = textColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Play / Pause Circular Button
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .background(if (phase == BreathingPhase.PAUSED) Color(0xFF86A573) else Color.White, CircleShape)
                    .clickable { isPaused = !isPaused },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isPaused) Icons.Rounded.PlayArrow else Icons.Rounded.Pause,
                    contentDescription = if (isPaused) "Play" else "Pause",
                    tint = if (phase == BreathingPhase.PAUSED) Color.White else backgroundColor,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}

// --- UTILS ---

@Composable
fun TopBar(title: String, onExit: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onExit) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "Back",
                tint = TextPrimary
            )
        }
        Text(
            text = title,
            color = TextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
package com.rewire.app.ui.settings

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width // ADDED: Missing import for width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.AlertDialog // ADDED: Missing import for AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rewire.app.ui.theme.*
import com.rewire.app.utils.InstalledApps
import com.rewire.app.utils.ProtectedApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext // ADDED: For background thread execution

@Composable
fun SettingsScreen(
    innerPadding: PaddingValues
) {
    val context = LocalContext.current

    // IMPROVEMENT: Load apps asynchronously to prevent main thread blocking
    var apps by remember { mutableStateOf<MutableList<ProtectedApp>>(mutableListOf()) }

    LaunchedEffect(Unit) {
        apps = withContext(Dispatchers.IO) {
            InstalledApps.getSupportedApps(context)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
            .padding(innerPadding)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Settings",
                color = TextPrimary,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            ProtectedAppsCard(apps)
        }

        item {
            FeaturesCard()
        }

        item {
            PrivacyCard()
        }

        item {
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
fun ProtectedAppsCard(
    apps: MutableList<ProtectedApp>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(
            containerColor = Surface
        )
    ) {
        Column(modifier = Modifier.padding(22.dp)) {
            Text(
                text = "Protected Apps",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Choose which apps Rewire should monitor.",
                color = TextSecondary,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            if (apps.isEmpty()) {
                // ADDED: Simple fallback if apps are still loading
                Text("Loading apps...", color = TextSecondary, fontSize = 14.sp)
            } else {
                apps.forEachIndexed { index, app ->
                    ProtectedAppRow(app)
                    if (index != apps.lastIndex) {
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ProtectedAppRow(
    app: ProtectedApp
) {
    var enabled by remember { mutableStateOf(app.enabled) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(SurfaceVariant)
            .clickable {
                enabled = !enabled
                app.enabled = enabled
            }
            .padding(horizontal = 18.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            bitmap = drawableToBitmap(app.icon),
            contentDescription = app.appName,
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = app.appName,
            modifier = Modifier.weight(1f),
            color = TextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        if (enabled) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(Primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

fun drawableToBitmap(
    drawable: Drawable
): androidx.compose.ui.graphics.ImageBitmap {
    val bitmap = when (drawable) {
        is BitmapDrawable -> drawable.bitmap
        else -> {
            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }
    return bitmap.asImageBitmap()
}

@Composable
fun FeaturesCard() {
    var showIntentInfo by remember { mutableStateOf(false) }
    var showReminderInfo by remember { mutableStateOf(false) }
    var showTunnelInfo by remember { mutableStateOf(false) }

    var intentEnabled by remember { mutableStateOf(true) }
    var reminderEnabled by remember { mutableStateOf(true) }
    var tunnelEnabled by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(
            containerColor = Surface
        )
    ) {
        Column(modifier = Modifier.padding(22.dp)) {
            Text(
                text = "Features",
                color = TextPrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Configure Rewire's intervention features.",
                color = TextSecondary,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            FeatureRow(
                title = "Intent System",
                enabled = intentEnabled,
                onToggle = { intentEnabled = !intentEnabled },
                onInfoClick = { showIntentInfo = true }
            )

            Spacer(modifier = Modifier.height(12.dp))

            FeatureRow(
                title = "Reminder System",
                enabled = reminderEnabled,
                onToggle = { reminderEnabled = !reminderEnabled },
                onInfoClick = { showReminderInfo = true }
            )

            Spacer(modifier = Modifier.height(12.dp))

            FeatureRow(
                title = "Doomscroll Tunnel",
                enabled = tunnelEnabled,
                onToggle = { tunnelEnabled = !tunnelEnabled },
                onInfoClick = { showTunnelInfo = true }
            )
        }
    }

    if (showIntentInfo) {
        FeatureInfoDialog(
            title = "Intent System",
            description = "Before opening a distracting app, Rewire asks why you are opening it and for how long. This creates conscious intent before consumption.",
            onDismiss = { showIntentInfo = false }
        )
    }

    if (showReminderInfo) {
        FeatureInfoDialog(
            title = "Reminder System",
            description = "When your planned session ends, Rewire reminds you of your original intention before you continue scrolling.",
            onDismiss = { showReminderInfo = false }
        )
    }

    if (showTunnelInfo) {
        FeatureInfoDialog(
            title = "Doomscroll Tunnel",
            description = "Gradually darkens the screen during continuous scrolling, increasing awareness and helping break passive consumption.",
            onDismiss = { showTunnelInfo = false }
        )
    }
}

@Composable
fun FeatureRow(
    title: String,
    enabled: Boolean,
    onToggle: () -> Unit,
    onInfoClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(SurfaceVariant)
            .clickable { onToggle() }
            .padding(horizontal = 18.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            color = TextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = "ⓘ",
            color = Primary,
            fontSize = 20.sp,
            modifier = Modifier.clickable { onInfoClick() }
        )

        Spacer(modifier = Modifier.width(16.dp))

        Box(
            modifier = Modifier
                .background(
                    if (enabled) Primary else Divider,
                    RoundedCornerShape(50.dp)
                )
                .padding(horizontal = 14.dp, vertical = 7.dp)
        ) {
            Text(
                text = if (enabled) "ON" else "OFF",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun FeatureInfoDialog(
    title: String,
    description: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Surface,
        title = {
            Text(
                text = title,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = description,
                color = TextSecondary,
                fontSize = 15.sp
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Got it",
                    color = Primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}

@Composable
fun PrivacyCard() {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(
            containerColor = Surface
        )
    ) {
        Column(modifier = Modifier.padding(22.dp)) {
            Text(
                text = "Privacy & Data",
                color = TextPrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Manage your local data and privacy settings.",
                color = TextSecondary,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(SurfaceVariant)
                    .clickable {
                        // TODO: Export session history as JSON / CSV
                    }
                    .padding(horizontal = 18.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Export Session Data",
                    modifier = Modifier.weight(1f),
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "→",
                    color = Primary,
                    fontSize = 22.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF3B1D22))
                    .clickable { showDeleteDialog = true }
                    .padding(horizontal = 18.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Delete All Saved Data",
                    modifier = Modifier.weight(1f),
                    color = Color(0xFFFF7A7A),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "🗑",
                    fontSize = 18.sp
                )
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            containerColor = Surface,
            title = {
                Text(
                    text = "Delete All Data?",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "This will permanently delete all sessions, analytics, settings and locally stored data. This action cannot be undone.",
                    color = TextSecondary,
                    fontSize = 15.sp
                )
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text(text = "Cancel", color = Primary)
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        // TODO: Delete Room Database, Clear Preferences, Reset App
                    }
                ) {
                    Text(
                        text = "Delete",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        )
    }
}
package com.example.mobileapplumea.ui.screens.profile

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState // Import for rememberScrollState
import androidx.compose.foundation.verticalScroll // Import for verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapplumea.R
import com.example.mobileapplumea.ui.screens.home.HomeBottomNavBar
import com.example.mobileapplumea.ui.theme.LumeaBackgroundDark
import com.example.mobileapplumea.ui.theme.LumeaBackgroundLight
import com.example.mobileapplumea.ui.theme.LumeaPinkDark
import com.example.mobileapplumea.ui.theme.LumeaPinkLight
import com.example.mobileapplumea.ui.theme.MobileAppLumeaTheme
import com.example.mobileapplumea.ui.theme.LumeaPinkDarkPrimary
import androidx.compose.material.icons.automirrored.filled.ArrowForward

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBackClick: () -> Unit = { /* TODO: Navigate back */ },
    onEditProfileClick: () -> Unit = {},
    onPrivacySettingsClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onBeautyQuizClick: () -> Unit = {},
    onOrderHistoryClick: () -> Unit = {},
    onShippingAddressClick: () -> Unit = {},
    onSignOutClick: () -> Unit = {},
    onHomeTabClick: () -> Unit = {},
    onShopTabClick: () -> Unit = {},
    onFavoritesTabClick: () -> Unit = {},
    onProfileTabClick: () -> Unit = {}
) {
    val darkTheme = isSystemInDarkTheme()
    val backgroundColor = if (darkTheme) LumeaBackgroundDark else LumeaBackgroundLight
    val contentColor = if (darkTheme) Color.White else Color.Black
    val iconTint = if (darkTheme) LumeaPinkLight else LumeaPinkDarkPrimary
    val cardBackgroundColor = if (darkTheme) Color.DarkGray.copy(alpha = 0.3f) else Color.White
    val signOutButtonColor = if (darkTheme) LumeaPinkLight else LumeaPinkDark

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        color = contentColor
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = iconTint
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = onSignOutClick,
                        colors = ButtonDefaults.textButtonColors(contentColor = signOutButtonColor)
                    ) {
                        Text("Sign Out", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {
            HomeBottomNavBar(
                darkTheme = darkTheme,
                selectedTab = "Profile",
                onHomeTabClick = onHomeTabClick,
                onShopTabClick = onShopTabClick,
                onFavoritesTabClick = onFavoritesTabClick,
                onProfileTabClick = onProfileTabClick
            )
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        // Use rememberScrollState and verticalScroll for the main content Column
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .verticalScroll(scrollState), // Apply verticalScroll here
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(if (darkTheme) Color.Gray.copy(alpha = 0.2f) else LumeaPinkLight.copy(alpha = 0.3f))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                IconButton(
                    onClick = onEditProfileClick,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = 4.dp, y = 4.dp)
                        .background(color = iconTint, shape = CircleShape)
                        .size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Profile",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Jelly Grande",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = contentColor
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = "Beauty Profile",
                    tint = iconTint,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Personalized Recommendations",
                    style = MaterialTheme.typography.bodyMedium,
                    color = contentColor.copy(alpha = 0.7f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProfileMenuItem(
                    icon = Icons.Default.Lock,
                    text = "Privacy & Setting",
                    darkTheme = darkTheme,
                    onClick = onPrivacySettingsClick
                )
                ProfileMenuItem(
                    icon = Icons.Default.Notifications,
                    text = "Notifications",
                    darkTheme = darkTheme,
                    onClick = onNotificationsClick
                )
                ProfileMenuItem(
                    icon = Icons.Default.MenuBook,
                    text = "Beauty Product Quiz",
                    darkTheme = darkTheme,
                    onClick = onBeautyQuizClick
                )
                ProfileMenuItem(
                    icon = Icons.Default.History,
                    text = "Order History",
                    darkTheme = darkTheme,
                    onClick = onOrderHistoryClick
                )
                ProfileMenuItem(
                    icon = Icons.Default.LocationOn,
                    text = "Shipping Address",
                    darkTheme = darkTheme,
                    onClick = onShippingAddressClick
                )
            }
        }
    }
}

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    text: String,
    darkTheme: Boolean,
    onClick: () -> Unit
) {
    val textColor = if (darkTheme) Color.White else Color.Black.copy(alpha = 0.8f)
    val iconColor = if (darkTheme) LumeaPinkLight else LumeaPinkDarkPrimary
    val arrowColor = if (darkTheme) Color.LightGray else Color.Gray
    val containerColor = if (darkTheme) Color.DarkGray.copy(alpha = 0.3f) else Color.White

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = text,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    color = textColor
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Navigate",
                tint = arrowColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// Previews
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ProfileScreenPreviewLight() {
    MobileAppLumeaTheme {
        ProfileScreen()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProfileScreenPreviewDark() {
    MobileAppLumeaTheme(darkTheme = true) {
        ProfileScreen()
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 360, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ProfileScreenLandscapePreviewLight() {
    MobileAppLumeaTheme {
        ProfileScreen()
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 360, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProfileScreenLandscapePreviewDark() {
    MobileAppLumeaTheme(darkTheme = true) {
        ProfileScreen()
    }
}
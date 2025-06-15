package com.example.mobileapplumea.ui.screens.getstarted

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapplumea.R
import com.example.mobileapplumea.ui.theme.MobileAppLumeaTheme
import com.example.mobileapplumea.ui.theme.LumeaPinkLight
import com.example.mobileapplumea.ui.theme.LumeaPinkDark
import com.example.mobileapplumea.ui.theme.LumeaPinkDarkPrimary
import androidx.compose.ui.draw.clipToBounds // To ensure content doesn't overflow when offset

@Composable
fun GetStartedScreen(
    onGetStartedClick: () -> Unit // This lambda will be called by NavGraph to navigate
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val darkTheme = isSystemInDarkTheme()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // Background Image Layer (ensure R.drawable.background_get_started exists)
        Image(
            painter = painterResource(id = R.drawable.background_get_started),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


        if (isLandscape) {
            LandscapeGetStartedContent(onGetStartedClick = onGetStartedClick)
        } else {
            PortraitGetStartedContent(onGetStartedClick = onGetStartedClick)
        }
    }
}

@Composable
private fun PortraitGetStartedContent(onGetStartedClick: () -> Unit) {
    val darkTheme = isSystemInDarkTheme()

    val buttonContainerColor = if (darkTheme) LumeaPinkDarkPrimary else LumeaPinkDark
    val buttonContentColor = if (darkTheme) Color.Black else MaterialTheme.colorScheme.onPrimary

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        // App Logo (Text version with shadow effect)
        Box(
            modifier = Modifier.wrapContentSize(align = Alignment.Center)
        ) {
            Text(
                text = "LUMEA",
                style = MaterialTheme.typography.displayLarge.copy(fontSize = 72.sp), // Adjust size
                color = LumeaPinkLight.copy(alpha = 0.5f), // Shadow color
                textAlign = TextAlign.Center,
                modifier = Modifier.offset(x = 2.dp, y = 2.dp) // Shadow offset
            )
            Text(
                text = "LUMEA",
                style = MaterialTheme.typography.displayLarge.copy(fontSize = 72.sp), // Adjust size
                color = Color.White, // Main text color
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Discover your perfect glow with personalized cosmetic recommendations and beauty insights.",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
            color = Color.White.copy(alpha = 0.9f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onGetStartedClick, // This calls the lambda from NavGraph
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonContainerColor,
                contentColor = buttonContentColor
            ),
            modifier = Modifier
                .fillMaxWidth(0.7f) // Make button 70% width
                .height(56.dp)
        ) {
            Text(
                text = "Get Started",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
private fun LandscapeGetStartedContent(onGetStartedClick: () -> Unit) {
    val darkTheme = isSystemInDarkTheme()

    val buttonContainerColor = if (darkTheme) LumeaPinkDarkPrimary else LumeaPinkDark
    val buttonContentColor = if (darkTheme) Color.Black else MaterialTheme.colorScheme.onPrimary

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Center content vertically
    ) {
        // App Logo (Text version for Landscape)
        Box(
            modifier = Modifier.wrapContentSize(align = Alignment.Center)
        ) {
            Text(
                text = "LUMEA",
                style = MaterialTheme.typography.displayLarge.copy(fontSize = 50.sp), // Smaller font size for landscape
                color = LumeaPinkLight.copy(alpha = 0.5f), // Shadow color
                textAlign = TextAlign.Center,
                modifier = Modifier.offset(x = 1.dp, y = 1.dp) // Shadow offset
            )
            Text(
                text = "LUMEA",
                style = MaterialTheme.typography.displayLarge.copy(fontSize = 50.sp), // Smaller font size for landscape
                color = Color.White, // Main text color
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Discover your perfect glow with personalized cosmetic recommendations and beauty insights.",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp), // Smaller body text for landscape
            color = Color.White.copy(alpha = 0.9f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(32.dp)) // Reduced spacer for landscape

        Button(
            onClick = onGetStartedClick, // This calls the lambda from NavGraph
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonContainerColor,
                contentColor = buttonContentColor
            ),
            modifier = Modifier
                .fillMaxWidth(0.6f) // Make button 60% width for landscape
                .height(50.dp) // Slightly smaller height for landscape
        ) {
            Text(
                text = "Get Started",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun GetStartedScreenPreviewLight() {
    MobileAppLumeaTheme {
        GetStartedScreen(onGetStartedClick = {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun GetStartedScreenPreviewDark() {
    MobileAppLumeaTheme(darkTheme = true) {
        GetStartedScreen(onGetStartedClick = {})
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 360, uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun GetStartedScreenPreviewLandscapeLight() {
    MobileAppLumeaTheme {
        GetStartedScreen(onGetStartedClick = {})
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 360, uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun GetStartedScreenPreviewLandscapeDark() {
    MobileAppLumeaTheme(darkTheme = true) {
        GetStartedScreen(onGetStartedClick = {})
    }
}
package com.example.mobileapplumea.ui.screens.payment


import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState // Import for scrolling
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll // Import for vertical scrolling
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mobileapplumea.R // Ensure this import points to your R file
import com.example.mobileapplumea.ui.theme.LumeaBackgroundDark
import com.example.mobileapplumea.ui.theme.LumeaBackgroundLight
import com.example.mobileapplumea.ui.theme.LumeaPinkDark
import com.example.mobileapplumea.ui.theme.LumeaPinkLight
import com.example.mobileapplumea.ui.theme.MobileAppLumeaTheme
import com.example.mobileapplumea.ui.theme.LumeaOnBackgroundDark // Import necessary for dark theme text
import com.example.mobileapplumea.ui.theme.LumeaOnBackgroundLight // Import necessary for light theme text
import com.example.mobileapplumea.ui.theme.LumeaSurfaceDark // Import necessary for dark theme surface
import com.example.mobileapplumea.ui.theme.LumeaSurfaceLight // Import necessary for light theme surface
import androidx.compose.ui.graphics.SolidColor // NEW IMPORT for BorderStroke color


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    onBackClick: () -> Unit,
    onPaymentSuccess: () -> Unit,
    onContinueShoppingClick: () -> Unit, // Callback for "Continue Shopping"
    onGoToOrdersClick: () -> Unit // Callback for "Go to orders"
) {
    val darkTheme = isSystemInDarkTheme()
    val backgroundColor = if (darkTheme) LumeaBackgroundDark else LumeaBackgroundLight
    val surfaceColor = if (darkTheme) LumeaSurfaceDark else LumeaSurfaceLight // Use theme surface color
    val textColor = if (darkTheme) LumeaOnBackgroundDark else LumeaOnBackgroundLight // Use theme onBackground color for general text
    val buttonColor = if (darkTheme) LumeaPinkDark else LumeaPinkDark // Assuming LumeaPinkDark is the main button color for both. Adjust if you prefer LumeaPinkLight for dark theme buttons.
    val iconTint = if (darkTheme) LumeaOnBackgroundDark else LumeaOnBackgroundLight // Icon tint should match text color
    val checkedColor = if (darkTheme) LumeaPinkLight else LumeaPinkDark // Color for the checkmark/checkbox

    // State to control the visibility of the success dialog
    var showSuccessDialog by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState() // For vertical scrolling in landscape

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Checkout",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = iconTint
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor)
            )
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(scrollState) // Make the column scrollable
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Delivery Address Section
            Text(
                text = "DELIVERY ADDRESS",
                style = MaterialTheme.typography.labelLarge,
                color = textColor.copy(alpha = 0.6f),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = surfaceColor),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "HOME ADDRESS",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                        Text(
                            text = "928 Lehner Junction Apt. 047",
                            style = MaterialTheme.typography.bodyMedium,
                            color = textColor.copy(alpha = 0.8f)
                        )
                    }
                    Icon(
                        Icons.Filled.CheckCircle,
                        contentDescription = "Selected",
                        tint = checkedColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Payment Method Section
            Text(
                text = "PAYMENT METHOD",
                style = MaterialTheme.typography.labelLarge,
                color = textColor.copy(alpha = 0.6f),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            // VISA Card
            PaymentMethodCard(
                iconRes = R.drawable.visa_logo, // Replace with your actual VISA logo drawable
                methodName = "VISA",
                cardNumber = "**** **** 5967",
                isSelected = true,
                surfaceColor = surfaceColor,
                textColor = textColor,
                checkedColor = checkedColor
            )
            Spacer(modifier = Modifier.height(12.dp))

            // PayPal
            PaymentMethodCard(
                iconRes = R.drawable.paypal_logo, // Replace with your actual PayPal logo drawable
                methodName = "wilson.casper@bernice.info",
                cardNumber = "", // Email instead of card number
                isSelected = true,
                surfaceColor = surfaceColor,
                textColor = textColor,
                checkedColor = checkedColor
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Other Card
            PaymentMethodCard(
                iconRes = R.drawable.mastercard_logo, // Replace with your actual Mastercard logo drawable
                methodName = "•••• •••• 3461",
                cardNumber = "",
                isSelected = false,
                surfaceColor = surfaceColor,
                textColor = textColor,
                checkedColor = checkedColor
            )

            Spacer(modifier = Modifier.weight(1f)) // Pushes content to top

            // Payment Button
            Button(
                onClick = { showSuccessDialog = true }, // Show success dialog on click
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Payment",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            // Removed "Pay with Touch ID" section as requested
        }
    }

    // Success Dialog
    if (showSuccessDialog) {
        OrderSuccessDialog(
            onDismissRequest = { showSuccessDialog = false },
            onContinueShoppingClick = {
                showSuccessDialog = false // Dismiss dialog
                onContinueShoppingClick() // Navigate to Home
            },
            onGoToOrdersClick = {
                showSuccessDialog = false // Dismiss dialog
                onGoToOrdersClick() // Navigate to Cart (as per request)
            },
            backgroundColor = backgroundColor,
            surfaceColor = surfaceColor,
            textColor = textColor,
            buttonColor = buttonColor,
            checkedColor = checkedColor
        )
    }
}

@Composable
fun PaymentMethodCard(
    iconRes: Int,
    methodName: String,
    cardNumber: String,
    isSelected: Boolean,
    surfaceColor: Color,
    textColor: Color,
    checkedColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = surfaceColor),
        shape = RoundedCornerShape(12.dp),
        // FIX: Use SolidColor for the brush parameter instead of 'color'
        border = if (isSelected) ButtonDefaults.outlinedButtonBorder.copy(brush = SolidColor(checkedColor)) else null,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = methodName,
                    modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = methodName,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                    if (cardNumber.isNotEmpty()) {
                        Text(
                            text = cardNumber,
                            style = MaterialTheme.typography.bodyMedium,
                            color = textColor.copy(alpha = 0.7f)
                        )
                    }
                }
            }
            if (isSelected) {
                Icon(
                    Icons.Filled.CheckCircle,
                    contentDescription = "Selected",
                    tint = checkedColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun OrderSuccessDialog(
    onDismissRequest: () -> Unit,
    onContinueShoppingClick: () -> Unit,
    onGoToOrdersClick: () -> Unit,
    backgroundColor: Color,
    surfaceColor: Color,
    textColor: Color,
    buttonColor: Color,
    checkedColor: Color
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min), // Allow content to dictate height
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = surfaceColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Success",
                    tint = checkedColor,
                    modifier = Modifier.size(80.dp)
                )
                Text(
                    text = "Your order is successfully.",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    lineHeight = 30.sp // Adjust line height if needed
                )
                Text(
                    text = "You can track the delivery in the 'Orders' section.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor.copy(alpha = 0.8f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp)) // Spacer before buttons

                Button(
                    onClick = onContinueShoppingClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Continue Shopping",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                TextButton(
                    onClick = onGoToOrdersClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Go to orders",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = buttonColor // Assuming accent color
                    )
                }
            }
        }
    }
}

// --- Previews ---

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "PaymentScreen_Portrait_Light")
@Composable
fun PaymentScreenPortraitLightPreview() {
    MobileAppLumeaTheme(darkTheme = false) {
        PaymentScreen(
            onBackClick = {},
            onPaymentSuccess = {},
            onContinueShoppingClick = {},
            onGoToOrdersClick = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "PaymentScreen_Portrait_Dark")
@Composable
fun PaymentScreenPortraitDarkPreview() {
    MobileAppLumeaTheme(darkTheme = true) {
        PaymentScreen(
            onBackClick = {},
            onPaymentSuccess = {},
            onContinueShoppingClick = {},
            onGoToOrdersClick = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 400, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "PaymentScreen_Landscape_Light")
@Composable
fun PaymentScreenLandscapeLightPreview() {
    MobileAppLumeaTheme(darkTheme = false) {
        PaymentScreen(
            onBackClick = {},
            onPaymentSuccess = {},
            onContinueShoppingClick = {},
            onGoToOrdersClick = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 400, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "PaymentScreen_Landscape_Dark")
@Composable
fun PaymentScreenLandscapeDarkPreview() {
    MobileAppLumeaTheme(darkTheme = true) {
        PaymentScreen(
            onBackClick = {},
            onPaymentSuccess = {},
            onContinueShoppingClick = {},
            onGoToOrdersClick = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "OrderSuccessDialog_Light")
@Composable
fun OrderSuccessDialogLightPreview() {
    MobileAppLumeaTheme(darkTheme = false) {
        // You might want to wrap this in a Box or Surface for better preview rendering
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            OrderSuccessDialog(
                onDismissRequest = {},
                onContinueShoppingClick = {},
                onGoToOrdersClick = {},
                backgroundColor = LumeaBackgroundLight,
                surfaceColor = LumeaSurfaceLight,
                textColor = LumeaOnBackgroundLight,
                buttonColor = LumeaPinkDark,
                checkedColor = LumeaPinkDark
            )
        }
    }
}
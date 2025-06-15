package com.example.mobileapplumea.ui.screens.payment

import androidx.compose.foundation.BorderStroke

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
import androidx.compose.ui.text.style.TextAlign // For dialog text alignment


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
    val checkedColor = if (darkTheme) LumeaPinkLight else LumeaPinkDark // Color for the checkmark/checkbox for selection/success

    // State to control the visibility of the success dialog
    var showSuccessDialog by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState() // For vertical scrolling

    val subtotal = 15000.00
    val shipping = 500.00
    val totalAmount = subtotal + shipping

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
                style = MaterialTheme.typography.titleSmall, // Adjusted size
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
                            style = MaterialTheme.typography.bodyLarge, // Keep this relatively prominent
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                        Text(
                            // Sri Lankan Address
                            text = "No. 123, Galle Road,\nColombo 03, Sri Lanka",
                            style = MaterialTheme.typography.bodyMedium, // Adjusted size
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
                style = MaterialTheme.typography.titleSmall, // Adjusted size
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
                methodName = "PayPal",
                cardNumber = "wilson.casper@bernice.info", // Email instead of card number
                isSelected = false, // Set to false to show difference
                surfaceColor = surfaceColor,
                textColor = textColor,
                checkedColor = checkedColor
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Other Card
            PaymentMethodCard(
                iconRes = R.drawable.mastercard_logo, // Replace with your actual Mastercard logo drawable
                methodName = "Mastercard",
                cardNumber = "**** **** 3461",
                isSelected = false,
                surfaceColor = surfaceColor,
                textColor = textColor,
                checkedColor = checkedColor
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Order Summary Section
            Text(
                text = "ORDER SUMMARY",
                style = MaterialTheme.typography.titleSmall, // Adjusted size
                color = textColor.copy(alpha = 0.6f),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OrderSummaryCard(
                subtotal = subtotal,
                shipping = shipping,
                totalAmount = totalAmount,
                surfaceColor = surfaceColor,
                textColor = textColor,
                priceColor = checkedColor // Using checkedColor for accent prices
            )

            Spacer(modifier = Modifier.weight(1f)) // Pushes content to top, creating flexible space

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
        border = if (isSelected) BorderStroke(2.dp, checkedColor) else null, // Use BorderStroke directly
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
fun OrderSummaryCard(
    subtotal: Double,
    shipping: Double,
    totalAmount: Double,
    surfaceColor: Color,
    textColor: Color,
    priceColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = surfaceColor),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Subtotal
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Subtotal",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor.copy(alpha = 0.8f)
                )
                Text(
                    text = "LKR ${"%.2f".format(subtotal)}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor
                )
            }
            // Shipping
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Shipping",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor.copy(alpha = 0.8f)
                )
                Text(
                    text = "LKR ${"%.2f".format(shipping)}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor
                )
            }
            Divider(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                color = textColor.copy(alpha = 0.1f),
                thickness = 1.dp
            )
            // Total
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.titleLarge, // Larger for total
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Text(
                    text = "LKR ${"%.2f".format(totalAmount)}",
                    style = MaterialTheme.typography.titleLarge, // Larger for total amount
                    fontWeight = FontWeight.ExtraBold,
                    color = priceColor // Highlight total amount
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
                .fillMaxWidth(),
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
                    style = MaterialTheme.typography.titleLarge, // Adjusted size
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    textAlign = TextAlign.Center, // Center text
                    lineHeight = 32.sp // Adjust line height for better readability if needed
                )
                Text(
                    text = "You can track the delivery in the 'Orders' section.",
                    style = MaterialTheme.typography.bodyMedium, // Adjusted size
                    color = textColor.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
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
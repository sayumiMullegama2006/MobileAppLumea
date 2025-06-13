package com.example.mobileapplumea.ui.screens.cart


import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack // For auto-mirrored back arrow
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapplumea.R // Assuming your drawables are in R.drawable
import com.example.mobileapplumea.ui.theme.LumeaBackgroundDark
import com.example.mobileapplumea.ui.theme.LumeaBackgroundLight
import com.example.mobileapplumea.ui.theme.LumeaPinkDark
import com.example.mobileapplumea.ui.theme.LumeaPinkLight
import com.example.mobileapplumea.ui.theme.MobileAppLumeaTheme
import androidx.compose.runtime.* // Import for remember, mutableStateListOf

// Dummy data classes for Cart items (you should replace this with your actual data model)
data class CartItem(
    val id: String,
    val name: String,
    val style: String,
    val price: String, // Keep as String for simplicity, parse to Double for calculation
    var quantity: Int, // Make quantity mutable as it changes
    val imageResId: Int,
    var isSelected: Boolean // New property for checkbox state
)

// Dummy list of cart items - CORRECTED DRAWABLE REFERENCES AND ADDED isSelected
val dummyCartItems = listOf(
    CartItem("1", "Sporty Chic Top", "Women Style", "$150.00", 1, R.drawable.product_1, true),
    CartItem("2", "Elegant Blouse", "Women Style", "$250.00", 1, R.drawable.product_2, true),
    CartItem("3", "Comfy Hoodie", "Unisex Style", "$350.00", 1, R.drawable.product_3, true),
    CartItem("4", "Classic Jeans", "Men Style", "$550.00", 1, R.drawable.product_4, false) // Example of an unselected item
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    initialCartItems: List<CartItem>, // Renamed to initialCartItems
    onBackClick: () -> Unit,
    onPayNowClick: () -> Unit //
) {
    val darkTheme = isSystemInDarkTheme()
    val backgroundColor = if (darkTheme) LumeaBackgroundDark else LumeaBackgroundLight
    val surfaceColor = if (darkTheme) Color(0xFF2C2C2C) else Color.White // Slightly different surface for cards
    val textColor = if (darkTheme) Color.White else Color.Black.copy(alpha = 0.9f)
    val buttonColor = if (darkTheme) LumeaPinkLight else LumeaPinkDark
    val iconTint = if (darkTheme) Color.White else Color.Black

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val localLayoutDirection = LocalLayoutDirection.current

    // State to hold and manage cart items dynamically
    val cartItems = remember { mutableStateListOf<CartItem>().apply { addAll(initialCartItems) } }

    // Calculate total amount based on selected items
    val totalAmount = remember(cartItems) {
        val total = cartItems.filter { it.isSelected }.sumOf {
            it.price.replace("$", "").toDouble() * it.quantity
        }
        "$%.2f".format(total)
    }

    // Function to handle quantity changes
    val onQuantityChange: (CartItem, Int) -> Unit = { item, newQuantity ->
        val index = cartItems.indexOfFirst { it.id == item.id }
        if (index != -1) {
            cartItems[index] = cartItems[index].copy(quantity = newQuantity.coerceAtLeast(1))
        }
    }

    // Function to handle item selection changes
    val onSelectionChange: (CartItem, Boolean) -> Unit = { item, isSelected ->
        val index = cartItems.indexOfFirst { it.id == item.id }
        if (index != -1) {
            cartItems[index] = cartItems[index].copy(isSelected = isSelected)
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Card", // Title from image
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
                actions = {
                    // Profile picture placeholder from image
                    Image(
                        painter = painterResource(id = R.drawable.profile), // Replace with your profile drawable
                        contentDescription = "Profile",
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(50)) // Circular clip
                            .background(Color.Gray), // Placeholder background
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor)
            )
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding( // Apply scaffold padding
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = paddingValues.calculateStartPadding(localLayoutDirection),
                    end = paddingValues.calculateEndPadding(localLayoutDirection)
                )
        ) {
            if (isLandscape) {
                // Landscape layout: Cart items on the left, total/pay on the right
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp) // Add some padding for the row
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(0.7f) // Takes more space for items
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(bottom = 16.dp) // Padding at the end of the list
                    ) {
                        items(cartItems) { item ->
                            CartItemCard(
                                item = item,
                                textColor = textColor,
                                surfaceColor = surfaceColor,
                                buttonColor = buttonColor,
                                onQuantityChange = onQuantityChange, // Pass the functional callback
                                onSelectionChange = onSelectionChange // Pass the functional callback
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp)) // Space between list and summary

                    // Summary and Pay Now button on the right
                    Column(
                        modifier = Modifier
                            .weight(0.3f) // Takes less space for summary
                            .fillMaxHeight()
                            .padding(horizontal = 8.dp, vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween // Pushes button to bottom
                    ) {
                        Text(
                            text = "Total",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = textColor
                        )
                        Text(
                            text = totalAmount,
                            style = MaterialTheme.typography.headlineMedium, // Adjusted size
                            fontWeight = FontWeight.ExtraBold,
                            color = buttonColor
                        )
                        Spacer(modifier = Modifier.height(16.dp)) // Space before button

                        Button(
                            onClick = onPayNowClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                text = "Pay Now",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            } else {
                // Portrait Layout: Cart items list, then sticky total/pay section at bottom
                Box(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .padding(bottom = 120.dp), // Make space for the sticky bottom bar
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(cartItems) { item ->
                            CartItemCard(
                                item = item,
                                textColor = textColor,
                                surfaceColor = surfaceColor,
                                buttonColor = buttonColor,
                                onQuantityChange = onQuantityChange, // Pass the functional callback
                                onSelectionChange = onSelectionChange // Pass the functional callback
                            )
                        }
                    }

                    // Total and Pay Now at the bottom, sticky
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                        color = surfaceColor,
                        shadowElevation = 8.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Total",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = textColor
                                )
                                Text(
                                    text = totalAmount,
                                    style = MaterialTheme.typography.headlineMedium, // Adjusted size
                                    fontWeight = FontWeight.ExtraBold,
                                    color = buttonColor
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Button(
                                onClick = onPayNowClick,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Text(
                                    text = "Pay Now",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemCard(
    item: CartItem,
    textColor: Color,
    surfaceColor: Color,
    buttonColor: Color,
    onQuantityChange: (CartItem, Int) -> Unit, // Callback for quantity changes
    onSelectionChange: (CartItem, Boolean) -> Unit // Callback for selection changes
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp), // Height adjusted for card content
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = surfaceColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            Image(
                painter = painterResource(id = item.imageResId),
                contentDescription = item.name,
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.LightGray), // Placeholder background
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Product Details and Quantity
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Text(
                    text = item.style,
                    style = MaterialTheme.typography.bodySmall,
                    color = textColor.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.price,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = buttonColor
                )
            }

            // Checkbox and Quantity Buttons
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxHeight()
            ) {
                // Checkbox from image
                Checkbox(
                    checked = item.isSelected, // Use item's selection state
                    onCheckedChange = { isChecked -> onSelectionChange(item, isChecked) }, // Update selection state
                    colors = CheckboxDefaults.colors(
                        checkedColor = buttonColor,
                        uncheckedColor = textColor.copy(alpha = 0.6f)
                    ),
                    modifier = Modifier.size(24.dp) // Adjusted size for better tap target
                )
                Spacer(modifier = Modifier.height(8.dp)) // Space between checkbox and quantity

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.background(Color.LightGray.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                ) {
                    IconButton(
                        onClick = { onQuantityChange(item, item.quantity - 1) },
                        enabled = item.quantity > 1 // Disable if quantity is 1
                    ) {
                        Icon(
                            Icons.Default.Remove,
                            contentDescription = "Decrease quantity",
                            tint = textColor.copy(alpha = 0.7f)
                        )
                    }
                    Text(
                        text = "${item.quantity}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = textColor
                    )
                    IconButton(
                        onClick = { onQuantityChange(item, item.quantity + 1) }
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Increase quantity",
                            tint = textColor.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}


// --- Previews ---

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "CartScreen_Portrait_Light")
@Composable
fun CartScreenPortraitLightPreview() {
    MobileAppLumeaTheme(darkTheme = false) {
        CartScreen(
            initialCartItems = dummyCartItems,
            onBackClick = {},
            onPayNowClick = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "CartScreen_Portrait_Dark")
@Composable
fun CartScreenPortraitDarkPreview() {
    MobileAppLumeaTheme(darkTheme = true) {
        CartScreen(
            initialCartItems = dummyCartItems,
            onBackClick = {},
            onPayNowClick = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 400, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "CartScreen_Landscape_Light")
@Composable
fun CartScreenLandscapeLightPreview() {
    MobileAppLumeaTheme(darkTheme = false) {
        CartScreen(
            initialCartItems = dummyCartItems,
            onBackClick = {},
            onPayNowClick = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 400, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "CartScreen_Landscape_Dark")
@Composable
fun CartScreenLandscapeDarkPreview() {
    MobileAppLumeaTheme(darkTheme = true) {
        CartScreen(
            initialCartItems = dummyCartItems,
            onBackClick = {},
            onPayNowClick = {}
        )
    }
}
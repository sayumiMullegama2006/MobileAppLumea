package com.example.mobileapplumea.ui.screens.productdetail


import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.padding // Ensure this is imported
import androidx.compose.ui.platform.LocalConfiguration // Ensure this is imported
import androidx.compose.ui.platform.LocalLayoutDirection // Ensure this is imported
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import com.example.mobileapplumea.data.Product
import com.example.mobileapplumea.data.productList
import com.example.mobileapplumea.ui.theme.LumeaBackgroundDark
import com.example.mobileapplumea.ui.theme.LumeaBackgroundLight
import com.example.mobileapplumea.ui.theme.LumeaPinkDark
import com.example.mobileapplumea.ui.theme.LumeaPinkLight
import com.example.mobileapplumea.ui.theme.MobileAppLumeaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    product: Product,
    onBackClick: () -> Unit,
    onAddToCartClick: (Product) -> Unit = {}
) {
    val darkTheme = isSystemInDarkTheme()
    val backgroundColor = if (darkTheme) LumeaBackgroundDark else LumeaBackgroundLight
    val textColor = if (darkTheme) Color.White else Color.Black.copy(alpha = 0.9f)
    val priceColor = if (darkTheme) LumeaPinkLight else LumeaPinkDark
    val starColor = Color(0xFFFFA726) // Consistent star color

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* No title needed here, image will fill */ },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = if (darkTheme) Color.White else Color.Black
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Add to favorites logic */ }) {
                        Icon(
                            Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (darkTheme) Color.White else Color.Black
                        )
                    }
                },
                // Make TopAppBar transparent to show image underneath
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = backgroundColor // Set background color for the whole screen
    ) { paddingValues ->
        // Using a Box to layer the image and the content card
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Apply scaffold padding
        ) {
            // Product Image (fills the top part)
            Image(
                painter = painterResource(id = product.imageResId),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp) // Adjusted height slightly for image dominance
                    .align(Alignment.TopCenter), // Align to top
                contentScale = ContentScale.Crop
            )

            // Content Card (overlaps the image slightly and takes rest of the space)
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter) // Align to bottom
                    .offset(y = (-30).dp) // Overlap by moving up 30dp
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)), // Rounded top corners
                color = backgroundColor, // Background color of the content card
                shadowElevation = 8.dp // Add shadow for separation
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 20.dp) // Generous padding
                ) {
                    // Product Name
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.ExtraBold),
                        color = textColor,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    // Brand Name
                    Text(
                        text = product.brand,
                        style = MaterialTheme.typography.titleMedium,
                        color = textColor.copy(alpha = 0.7f),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Price and Rating Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = product.price,
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                            color = priceColor
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "Rating",
                                tint = starColor,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${product.rating} (${product.reviewCount} reviews)",
                                style = MaterialTheme.typography.bodyMedium,
                                color = textColor.copy(alpha = 0.7f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Product Description Title
                    Text(
                        text = "About this product",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = textColor,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    // Product Description
                    Text(
                        text = product.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = textColor.copy(alpha = 0.8f),
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Add to Cart Button
                    Button(
                        onClick = { onAddToCartClick(product) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = priceColor),
                        shape = RoundedCornerShape(16.dp) // More rounded button
                    ) {
                        Text(
                            text = "Add to Cart",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun ProductDetailScreenPreviewLight() {
    MobileAppLumeaTheme {
        ProductDetailScreen(
            product = productList[0], // Example product (HydraBoost Serum)
            onBackClick = {},
            onAddToCartClick = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun ProductDetailScreenPreviewDark() {
    MobileAppLumeaTheme(darkTheme = true) {
        ProductDetailScreen(
            product = productList[0], // Example product (HydraBoost Serum)
            onBackClick = {},
            onAddToCartClick = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 400, uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun ProductDetailScreenLandscapePreviewLight() {
    MobileAppLumeaTheme {
        ProductDetailScreen(
            product = productList[0], // Example product (HydraBoost Serum)
            onBackClick = {},
            onAddToCartClick = {}
        )
    }
}
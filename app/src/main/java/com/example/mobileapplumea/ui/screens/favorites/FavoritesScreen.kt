package com.example.mobileapplumea.ui.screens.favorites


import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn // Changed from LazyVerticalGrid
import androidx.compose.foundation.lazy.items // Use items for LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite // Heart icon
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf // For half stars
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapplumea.data.Product
import com.example.mobileapplumea.data.productList // Assuming you want to pull products from here for now
import com.example.mobileapplumea.ui.screens.home.HomeBottomNavBar // Reusing your bottom nav bar
// import com.example.mobileapplumea.ui.screens.home.ProductCard // No longer directly used here
import com.example.mobileapplumea.ui.theme.LumeaBackgroundDark
import com.example.mobileapplumea.ui.theme.LumeaBackgroundLight
import com.example.mobileapplumea.ui.theme.LumeaPinkDark
import com.example.mobileapplumea.ui.theme.LumeaPinkLight
import com.example.mobileapplumea.ui.theme.MobileAppLumeaTheme
import com.example.mobileapplumea.R // Ensure R is imported for drawable resources


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onProductClick: (Product) -> Unit = { /* TODO: Navigate to product detail */ },
    onBackClick: () -> Unit = { /* TODO: Navigate back */ },
    onCartClick: () -> Unit = { /* TODO: Navigate to cart */ },
    onHomeTabClick: () -> Unit = {},
    onShopTabClick: () -> Unit = {},
    onFavoritesTabClick: () -> Unit = {}, // This tab is currently selected
    onProfileTabClick: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val darkTheme = isSystemInDarkTheme()

    val backgroundColor = if (darkTheme) LumeaBackgroundDark else LumeaBackgroundLight
    val contentColor = if (darkTheme) Color.White else Color.Black.copy(alpha = 0.9f)
    val iconTint = if (darkTheme) LumeaPinkLight else LumeaPinkDark

    // For demonstration, let's pick a few products from your productList as "favorites"
    // In a real app, this would come from a user's favorite list/database.
    // Ensure these products have 'isBestSeller' or 'isHotThisWeek' set if you want the badges to show
    val favoriteProducts = remember { productList.take(4) } // Display the first 4 products

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Favorites", // Changed title to match image
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface // Use onSurface for general text, or iconTint if you prefer pink
                    )
                },
                navigationIcon = {
                    // Back button not in the image, but kept for general navigation pattern
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = iconTint
                        )
                    }
                },
                actions = {
                    // Search icon from image
                    IconButton(onClick = { /* TODO: Implement search */ }) {
                        Icon(
                            Icons.Default.ShoppingCart, // Replaced search with cart as per your top bar in home
                            contentDescription = "Search",
                            tint = iconTint,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    // Cart icon from image
                    IconButton(onClick = onCartClick) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = "Shopping Cart",
                            tint = iconTint,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = if (darkTheme) LumeaBackgroundDark else LumeaBackgroundLight,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {
            HomeBottomNavBar(
                darkTheme = darkTheme,
                selectedTab = "Favorites", // Explicitly set "Favorites" as the selected tab
                onHomeTabClick = onHomeTabClick,
                onShopTabClick = onShopTabClick,
                onFavoritesTabClick = onFavoritesTabClick, // Will usually do nothing if already on this screen
                onProfileTabClick = onProfileTabClick
            )
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        if (favoriteProducts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No favorite products yet. Start adding some!",
                    style = MaterialTheme.typography.titleMedium,
                    color = contentColor.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            LazyColumn( // Changed to LazyColumn
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp), // Spacing between list items
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(favoriteProducts) { product ->
                    FavoriteProductListItem( // Use the new list item composable
                        product = product,
                        darkTheme = darkTheme,
                        onProductClick = { onProductClick(product) },
                        onToggleFavorite = { /* TODO: Implement actual favorite toggle logic */ }
                    )
                }
            }
        }
    }
}

// Re-using the ProductCard for grid display is good, but for the list, we need a new one.
// The new FavoriteProductListItem composable should be defined outside or inside FavoritesScreen,
// but preferably outside for reusability if needed elsewhere.
// Here's the FavoriteProductListItem composable. You can place it directly below FavoritesScreen
// or in a separate file as mentioned.

@Composable
fun FavoriteProductListItem(
    product: Product,
    darkTheme: Boolean,
    onProductClick: (Product) -> Unit,
    onToggleFavorite: (Product) -> Unit = {} // Added for potential favorite toggle
) {
    val cardBackgroundColor = if (darkTheme) Color.DarkGray.copy(alpha = 0.3f) else Color.White
    val textColor = if (darkTheme) Color.White else Color.Black.copy(alpha = 0.9f)
    val starColor = Color(0xFFFFD700) // Gold color for stars
    val heartColor = Color(0xD3E91E63) // A more prominent red for favorite icon

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp) // Fixed height for consistency
            .clickable { onProductClick(product) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image with Badge (similar to image)
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = product.imageResId),
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                // Badge for "Best Seller" or "Hot This Week"
                if (product.isBestSeller) {
                    Text(
                        text = "Best Seller",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .background(Color(0xFF81D4FA), RoundedCornerShape(topStart = 8.dp, bottomEnd = 8.dp)) // Light blue badge
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                } else if (product.isHotThisWeek) {
                    Text(
                        text = "Hot This Week",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .background(Color(0xFFEF5350), RoundedCornerShape(topStart = 8.dp, bottomEnd = 8.dp)) // Red badge
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Product Details
            Column(
                modifier = Modifier.weight(1f) // Takes remaining space
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = textColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = product.brand, // Using 'brand' as author/creator
                    style = MaterialTheme.typography.bodySmall,
                    color = textColor.copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Display stars
                    repeat(product.rating.toInt()) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = starColor,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    if (product.rating - product.rating.toInt() > 0) {
                        Icon(
                            imageVector = Icons.Default.StarHalf,
                            contentDescription = null,
                            tint = starColor,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Text(
                        text = " ${product.rating}",
                        style = MaterialTheme.typography.labelSmall,
                        color = textColor.copy(alpha = 0.8f),
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = product.description, // Short description
                    style = MaterialTheme.typography.bodySmall,
                    color = textColor.copy(alpha = 0.8f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Favorite Icon (always solid as it's a favorites list)
            IconButton(onClick = { onToggleFavorite(product) }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Remove from Favorites",
                    tint = heartColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
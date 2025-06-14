package com.example.mobileapplumea.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Store // Import for shop icon
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector // Added for ImageVector in BottomNavItem
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapplumea.R
import com.example.mobileapplumea.ui.theme.LumeaBackgroundDark
import com.example.mobileapplumea.ui.theme.LumeaBackgroundLight
import com.example.mobileapplumea.ui.theme.LumeaPinkDark
import com.example.mobileapplumea.ui.theme.LumeaPinkDarkPrimary
import com.example.mobileapplumea.ui.theme.LumeaPinkLight
import com.example.mobileapplumea.ui.theme.MobileAppLumeaTheme
import com.example.mobileapplumea.data.Product
import com.example.mobileapplumea.data.Category
import com.example.mobileapplumea.data.productList
import com.example.mobileapplumea.data.categories
import com.example.mobileapplumea.data.bannerImageResId
import androidx.compose.ui.graphics.graphicsLayer // Import graphicsLayer
import androidx.compose.ui.unit.IntOffset // Import IntOffset


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onProductClick: (Product) -> Unit = { /* TODO: Navigate to product detail */ },
    onCartClick: () -> Unit = { /* TODO: Navigate to cart */ },
    onSearchClick: (String) -> Unit = { /* TODO: Handle search */ },
    onCategoryClick: (Category) -> Unit = { /* TODO: Filter products by category */ },
    onHomeTabClick: () -> Unit = {},
    onShopTabClick: () -> Unit = {},
    onFavoritesTabClick: () -> Unit = {},
    onProfileTabClick: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val darkTheme = isSystemInDarkTheme()

    val backgroundColor = if (darkTheme) LumeaBackgroundDark else LumeaBackgroundLight

    Scaffold(
        topBar = {
            HomeTopBar(
                darkTheme = darkTheme,
                isLandscape = isLandscape,
                onCartClick = onCartClick,
                onSearchClick = onSearchClick
            )
        },
        bottomBar = {
            HomeBottomNavBar(
                darkTheme = darkTheme,
                selectedTab = "Home",
                onHomeTabClick = onHomeTabClick,
                onShopTabClick = onShopTabClick,
                onFavoritesTabClick = onFavoritesTabClick,
                onProfileTabClick = onProfileTabClick
            )
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        if (isLandscape) {
            LandscapeHomeContent(
                paddingValues = paddingValues,
                darkTheme = darkTheme,
                onProductClick = onProductClick,
                onCategoryClick = onCategoryClick
            )
        } else {
            PortraitHomeContent(
                paddingValues = paddingValues,
                darkTheme = darkTheme,
                onProductClick = onProductClick,
                onCategoryClick = onCategoryClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopBar(
    darkTheme: Boolean,
    isLandscape: Boolean,
    onCartClick: () -> Unit,
    onSearchClick: (String) -> Unit
) {
    val searchBarBackgroundColor = if (darkTheme) Color.Gray.copy(alpha = 0.2f) else LumeaPinkLight.copy(alpha = 0.3f)
    val searchBarContentColor = if (darkTheme) Color.White else Color.Black
    val searchBarHintColor = if (darkTheme) Color.LightGray else Color.Gray
    val searchBarBorderColor = if (darkTheme) Color.Gray.copy(alpha = 0.5f) else LumeaPinkLight // Subtle border

    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // App Name 'Lumea' - STYLISH TEXT with Shadow
                Box(
                    modifier = Modifier.wrapContentSize(align = Alignment.Center)
                ) {
                    Text(
                        text = "LUMEA",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 24.sp
                        ),
                        color = LumeaPinkLight.copy(alpha = 0.5f), // Shadow color
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .graphicsLayer {
                                translationX = 2.dp.toPx()
                                translationY = 2.dp.toPx()
                            }
                    )
                    Text(
                        text = "LUMEA",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Black,
                            fontSize = 24.sp
                        ),
                        color = if (darkTheme) LumeaPinkLight else LumeaPinkDark, // Main text color, adjusting for theme
                        textAlign = TextAlign.Center
                    )
                }

                // Search Bar
                var searchText by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                        // Optional: onSearchClick(it) for live search
                    },
                    placeholder = { Text("Search for products...", color = searchBarHintColor) },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = searchBarHintColor
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .clip(RoundedCornerShape(24.dp)) // Ensures rounded corners are applied
                        .clickable { onSearchClick(searchText) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = searchBarBackgroundColor,
                        unfocusedContainerColor = searchBarBackgroundColor,
                        disabledContainerColor = searchBarBackgroundColor,
                        cursorColor = searchBarContentColor,
                        focusedBorderColor = searchBarBorderColor, // Added subtle border
                        unfocusedBorderColor = searchBarBorderColor, // Added subtle border
                        focusedTextColor = searchBarContentColor,
                        unfocusedTextColor = searchBarContentColor,
                        focusedLeadingIconColor = searchBarHintColor,
                        unfocusedLeadingIconColor = searchBarHintColor
                    ),
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 16.sp // Ensure text size is reasonable for the field
                    )
                )

                // Shopping Bag/Cart Icon
                IconButton(
                    onClick = onCartClick,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = "Shopping Cart",
                        tint = if (darkTheme) LumeaPinkLight else LumeaPinkDark,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = if (darkTheme) LumeaBackgroundDark else LumeaBackgroundLight,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}


@Composable
private fun PortraitHomeContent(
    paddingValues: PaddingValues,
    darkTheme: Boolean,
    onProductClick: (Product) -> Unit,
    onCategoryClick: (Category) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Promotional Banner - A spacer is added here for spacing from the TopBar
        item {
            Spacer(modifier = Modifier.height(16.dp)) // Space after top bar
            PromotionalBanner(darkTheme = darkTheme, bannerImageResId = bannerImageResId)
        }

        // Categories Section
        item {
            Text(
                text = "Categories", // Changed to "Categories"
                style = MaterialTheme.typography.titleLarge.copy( // Using titleLarge for smaller, normal font
                    fontWeight = FontWeight.Bold
                ),
                color = if (darkTheme) Color.White else Color.Black.copy(alpha = 0.9f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(categories) { category ->
                    CategoryItem(category = category, darkTheme = darkTheme, onClick = { onCategoryClick(category) })
                }
            }
        }

        // Products Section
        item {
            Text(
                text = "Popular", // Changed to "Popular"
                style = MaterialTheme.typography.titleLarge.copy( // Using titleLarge for smaller, normal font
                    fontWeight = FontWeight.Bold
                ),
                color = if (darkTheme) Color.White else Color.Black.copy(alpha = 0.9f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(650.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(productList) { product ->
                        ProductCard(product = product, darkTheme = darkTheme, onClick = { onProductClick(product) })
                    }
                }
            }
        }
    }
}

@Composable
private fun LandscapeHomeContent(
    paddingValues: PaddingValues,
    darkTheme: Boolean,
    onProductClick: (Product) -> Unit,
    onCategoryClick: (Category) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Promotional Banner - A spacer is added here for spacing from the TopBar
        item {
            Spacer(modifier = Modifier.height(16.dp)) // Space after top bar
            PromotionalBanner(darkTheme = darkTheme, isLandscape = true, bannerImageResId = bannerImageResId)
        }

        // Categories Section
        item {
            Text(
                text = "Categories", // Changed to "Categories"
                style = MaterialTheme.typography.titleLarge.copy( // Using titleLarge for smaller, normal font
                    fontWeight = FontWeight.Bold
                ),
                color = if (darkTheme) Color.White else Color.Black.copy(alpha = 0.9f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(categories) { category ->
                    CategoryItem(category = category, darkTheme = darkTheme, onClick = { onCategoryClick(category) })
                }
            }
        }

        // Products Section
        item {
            Text(
                text = "Popular", // Changed to "Popular"
                style = MaterialTheme.typography.titleLarge.copy( // Using titleLarge for smaller, normal font
                    fontWeight = FontWeight.Bold
                ),
                color = if (darkTheme) Color.White else Color.Black.copy(alpha = 0.9f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }
        item {
            val columns = if (LocalConfiguration.current.screenWidthDp > 800) 4 else 3
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columns),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(productList) { product ->
                        ProductCard(product = product, darkTheme = darkTheme, onClick = { onProductClick(product) })
                    }
                }
            }
        }
    }
}

@Composable
fun PromotionalBanner(darkTheme: Boolean, isLandscape: Boolean = false, bannerImageResId: Int) {
    val scrimBrush = Brush.verticalGradient(
        colors = listOf(Color.Black.copy(alpha = 0.3f), Color.Transparent, Color.Black.copy(alpha = 0.3f)),
        startY = 0f,
        endY = Float.POSITIVE_INFINITY
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (isLandscape) 120.dp else 180.dp)
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = bannerImageResId),
                contentDescription = "Promotional Banner",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(scrimBrush)
            )

            // Content within the banner (Shop Now button remains)
            if (isLandscape) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { /* Handle Shop Now */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = LumeaPinkDark),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Shop Now")
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { /* Handle Shop Now */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = LumeaPinkDark),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Shop Now")
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItem(category: Category, darkTheme: Boolean, onClick: () -> Unit) {
    val containerColor = if (darkTheme) Color.Gray.copy(alpha = 0.2f) else LumeaPinkLight.copy(alpha = 0.5f)
    val contentColor = if (darkTheme) Color.White else Color.Black.copy(alpha = 0.8f)


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Surface(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            color = Color.Transparent,
            shadowElevation = 2.dp
        ) {
            Image(
                painter = painterResource(id = category.imageResId),
                contentDescription = category.name,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = category.name,
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 13.sp),
            color = contentColor,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Composable
fun ProductCard(product: Product, darkTheme: Boolean, onClick: () -> Unit) {
    val cardBackgroundColor = if (darkTheme) Color.DarkGray.copy(alpha = 0.3f) else Color.White.copy(alpha = 0.95f)
    val textColor = if (darkTheme) Color.White else Color.Black.copy(alpha = 0.9f)
    val priceColor = if (darkTheme) LumeaPinkLight else LumeaPinkDark
    val starColor = Color(0xFFFFD700) // Gold color for stars

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if(darkTheme) Color.Gray.copy(alpha = 0.1f) else Color.LightGray.copy(alpha = 0.1f))
            ) {
                Image(
                    painter = painterResource(id = product.imageResId),
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.name,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                color = textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            Text(
                text = product.brand,
                style = MaterialTheme.typography.bodySmall,
                color = textColor.copy(alpha = 0.7f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating star",
                    tint = starColor,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "${product.rating}",
                    style = MaterialTheme.typography.bodySmall,
                    color = textColor,
                    modifier = Modifier.padding(start = 2.dp)
                )
                Text(
                    text = " (${product.reviewCount})",
                    style = MaterialTheme.typography.bodySmall,
                    color = textColor.copy(alpha = 0.7f)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.price,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = priceColor
            )
        }
    }
}


@Composable
fun HomeBottomNavBar(
    darkTheme: Boolean,
    selectedTab: String,
    onHomeTabClick: () -> Unit,
    onShopTabClick: () -> Unit,
    onFavoritesTabClick: () -> Unit,
    onProfileTabClick: () -> Unit
) {
    val selectedColor = if (darkTheme) LumeaPinkLight else LumeaPinkDarkPrimary
    val unselectedColor = if (darkTheme) Color.Gray else Color.DarkGray.copy(alpha = 0.6f)

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = if (darkTheme) LumeaBackgroundDark else LumeaBackgroundLight,
        tonalElevation = 4.dp
    ) {
        val items = listOf(
            BottomNavItem("Home", Icons.Default.Home, "Home"),
            BottomNavItem("Shop", Icons.Default.Store, "Shop"),
            BottomNavItem("Favorites", Icons.Default.Favorite, "Favorites"),
            BottomNavItem("Profile", Icons.Default.Person, "Profile")
        )

        items.forEach { item ->
            val isSelected = selectedTab == item.route
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = isSelected,
                onClick = {
                    when (item.route) {
                        "Home" -> onHomeTabClick()
                        "Shop" -> onShopTabClick()
                        "Favorites" -> onFavoritesTabClick()
                        "Profile" -> onProfileTabClick()
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = selectedColor,
                    selectedTextColor = selectedColor,
                    unselectedIconColor = unselectedColor,
                    unselectedTextColor = unselectedColor,
                    indicatorColor = if (darkTheme) LumeaPinkDark.copy(alpha = 0.3f) else LumeaPinkLight.copy(alpha = 0.3f)
                )
            )
        }
    }
}

data class BottomNavItem(val label: String, val icon: ImageVector, val route: String)

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun HomeScreenPreviewLight() {
    MobileAppLumeaTheme {
        HomeScreen()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun HomeScreenPreviewDark() {
    MobileAppLumeaTheme(darkTheme = true) {
        HomeScreen()
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 360, uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun HomeScreenPreviewLandscapeLight() {
    MobileAppLumeaTheme {
        HomeScreen()
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 360, uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun HomeScreenPreviewLandscapeDark() {
    MobileAppLumeaTheme(darkTheme = true) {
        HomeScreen()
    }
}
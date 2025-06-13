package com.example.mobileapplumea.ui.screens.shop


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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapplumea.R
import com.example.mobileapplumea.data.Category
import com.example.mobileapplumea.data.Product
import com.example.mobileapplumea.data.categories
import com.example.mobileapplumea.data.productList
import com.example.mobileapplumea.ui.screens.home.HomeBottomNavBar // Re-use the bottom nav bar
import com.example.mobileapplumea.ui.screens.home.ProductCard // Re-use product card
import com.example.mobileapplumea.ui.theme.LumeaBackgroundDark
import com.example.mobileapplumea.ui.theme.LumeaBackgroundLight
import com.example.mobileapplumea.ui.theme.LumeaPinkDark
import com.example.mobileapplumea.ui.theme.LumeaPinkDarkPrimary
import com.example.mobileapplumea.ui.theme.LumeaPinkLight
import com.example.mobileapplumea.ui.theme.MobileAppLumeaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(
    onProductClick: (Product) -> Unit,
    onBackClick: () -> Unit, // Added back click for top bar
    onCartClick: () -> Unit,
    onSearchClick: (String) -> Unit,
    onCategoryClick: (Category) -> Unit,
    onHomeTabClick: () -> Unit,
    onShopTabClick: () -> Unit,
    onFavoritesTabClick: () -> Unit,
    onProfileTabClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val darkTheme = isSystemInDarkTheme()

    val backgroundColor = if (darkTheme) LumeaBackgroundDark else LumeaBackgroundLight

    // State for selected category
    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    // Filter products based on selected category
    val filteredProducts = remember(selectedCategory) {
        if (selectedCategory == null || selectedCategory?.name == "All") { // Assuming "All" is a category to show all products
            productList
        } else {
            // This is a simplified filter. In a real app, products would have category IDs
            // For now, we'll check if product name or description contains category name (very basic)
            // OR you would have a 'category' property in your Product data class.
            // For demonstrative purposes, let's assume we filter by category name directly if available
            productList.filter { product ->
                // Example: If product had a 'category' property (e.g., product.category.name == selectedCategory.name)
                // For now, we'll just show all if no proper category filtering is set up in Product data
                // In a real app, productList would be filtered by a backend or a more robust local data structure
                true // For now, show all products regardless of category selection on ShopScreen
            }
        }
    }


    Scaffold(
        topBar = {
            ShopTopBar(
                darkTheme = darkTheme,
                onBackClick = onBackClick,
                onCartClick = onCartClick,
                onSearchClick = onSearchClick,
                onFilterClick = { /* TODO: Implement filter dialog/sheet */ },
                onSortClick = { /* TODO: Implement sort options */ }
            )
        },
        bottomBar = {
            HomeBottomNavBar(
                darkTheme = darkTheme,
                selectedTab = "Shop", // Highlight Shop tab
                onHomeTabClick = onHomeTabClick,
                onShopTabClick = onShopTabClick,
                onFavoritesTabClick = onFavoritesTabClick,
                onProfileTabClick = onProfileTabClick
            )
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        if (isLandscape) {
            LandscapeShopContent(
                paddingValues = paddingValues,
                darkTheme = darkTheme,
                products = filteredProducts,
                categories = categories,
                selectedCategory = selectedCategory,
                onProductClick = onProductClick,
                onCategoryClick = { category -> selectedCategory = category } // Update selected category
            )
        } else {
            PortraitShopContent(
                paddingValues = paddingValues,
                darkTheme = darkTheme,
                products = filteredProducts,
                categories = categories,
                selectedCategory = selectedCategory,
                onProductClick = onProductClick,
                onCategoryClick = { category -> selectedCategory = category } // Update selected category
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShopTopBar(
    darkTheme: Boolean,
    onBackClick: () -> Unit,
    onCartClick: () -> Unit,
    onSearchClick: (String) -> Unit,
    onFilterClick: () -> Unit,
    onSortClick: () -> Unit
) {
    val searchBarBackgroundColor = if (darkTheme) Color.Gray.copy(alpha = 0.2f) else LumeaPinkLight.copy(alpha = 0.3f)
    val searchBarContentColor = if (darkTheme) Color.White else Color.Black
    val searchBarHintColor = if (darkTheme) Color.LightGray else Color.Gray
    val iconTint = if (darkTheme) LumeaPinkLight else LumeaPinkDark

    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Back Button
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = iconTint
                    )
                }

                // Search Bar (Can be simplified or made a clickable placeholder)
                var searchText by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                        // Optional: onSearchClick(it) for live search
                    },
                    placeholder = { Text("Search products...", color = searchBarHintColor) },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = searchBarHintColor
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .clickable { onSearchClick(searchText) }, // Trigger search on click
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = searchBarBackgroundColor,
                        unfocusedContainerColor = searchBarBackgroundColor,
                        disabledContainerColor = searchBarBackgroundColor,
                        cursorColor = searchBarContentColor,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = searchBarContentColor,
                        unfocusedTextColor = searchBarContentColor,
                        focusedLeadingIconColor = searchBarHintColor,
                        unfocusedLeadingIconColor = searchBarHintColor
                    )
                )

                Spacer(Modifier.width(8.dp))

                // Filter Icon
                IconButton(onClick = onFilterClick) {
                    Icon(
                        Icons.Default.FilterList,
                        contentDescription = "Filter",
                        tint = iconTint,
                        modifier = Modifier.size(28.dp)
                    )
                }

                // Sort Icon
                IconButton(onClick = onSortClick) {
                    Icon(
                        Icons.Default.Sort,
                        contentDescription = "Sort",
                        tint = iconTint,
                        modifier = Modifier.size(28.dp)
                    )
                }

                // Shopping Cart Icon
                IconButton(onClick = onCartClick) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = "Shopping Cart",
                        tint = iconTint,
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
private fun PortraitShopContent(
    paddingValues: PaddingValues,
    darkTheme: Boolean,
    products: List<Product>,
    categories: List<Category>,
    selectedCategory: Category?,
    onProductClick: (Product) -> Unit,
    onCategoryClick: (Category) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp)) // Small space after top bar

            Text(
                text = "Browse Categories",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = if (darkTheme) Color.White else Color.Black.copy(alpha = 0.9f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(categories) { category ->
                    CategoryFilterItem(
                        category = category,
                        darkTheme = darkTheme,
                        isSelected = category == selectedCategory,
                        onClick = { onCategoryClick(category) }
                    )
                }
            }
        }

        item {
            Text(
                text = "All Products",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = if (darkTheme) Color.White else Color.Black.copy(alpha = 0.9f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }

        item {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 300.dp, max = 1200.dp), // Flexible height
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(products) { product ->
                    ProductCard(product = product, darkTheme = darkTheme, onClick = { onProductClick(product) })
                }
            }
        }
    }
}

@Composable
private fun LandscapeShopContent(
    paddingValues: PaddingValues,
    darkTheme: Boolean,
    products: List<Product>,
    categories: List<Category>,
    selectedCategory: Category?,
    onProductClick: (Product) -> Unit,
    onCategoryClick: (Category) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Categories column (optional, can be a filter button too)
        Column(
            modifier = Modifier
                .width(150.dp) // Fixed width for categories
                .fillMaxHeight()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Categories",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = if (darkTheme) Color.White else Color.Black.copy(alpha = 0.9f),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categories) { category ->
                    CategoryFilterItem(
                        category = category,
                        darkTheme = darkTheme,
                        isSelected = category == selectedCategory,
                        onClick = { onCategoryClick(category) }
                    )
                }
            }
        }

        // Products Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(3), // More columns in landscape
            modifier = Modifier
                .weight(1f) // Takes remaining width
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                Text(
                    text = "All Products",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = if (darkTheme) Color.White else Color.Black.copy(alpha = 0.9f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
            items(products) { product ->
                ProductCard(product = product, darkTheme = darkTheme, onClick = { onProductClick(product) })
            }
        }
    }
}

@Composable
fun CategoryFilterItem(category: Category, darkTheme: Boolean, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) {
        if (darkTheme) LumeaPinkDarkPrimary.copy(alpha = 0.7f) else LumeaPinkLight.copy(alpha = 0.7f)
    } else {
        if (darkTheme) Color.Gray.copy(alpha = 0.2f) else LumeaPinkLight.copy(alpha = 0.3f)
    }
    val contentColor = if (darkTheme) Color.White else Color.Black.copy(alpha = 0.8f)
    val selectedContentColor = Color.White // Text color when selected

    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
        color = backgroundColor,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = category.imageResId),
                contentDescription = null, // Content description for icon
                modifier = Modifier.size(24.dp),
                contentScale = ContentScale.Fit, // Use Fit for icons
                colorFilter = if (isSelected) androidx.compose.ui.graphics.ColorFilter.tint(selectedContentColor) else null // Tint icon if selected
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = category.name,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                color = if (isSelected) selectedContentColor else contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun ShopScreenPreviewLight() {
    MobileAppLumeaTheme {
        ShopScreen(
            onProductClick = {},
            onBackClick = {},
            onCartClick = {},
            onSearchClick = {},
            onCategoryClick = {},
            onHomeTabClick = {},
            onShopTabClick = {},
            onFavoritesTabClick = {},
            onProfileTabClick = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun ShopScreenPreviewDark() {
    MobileAppLumeaTheme(darkTheme = true) {
        ShopScreen(
            onProductClick = {},
            onBackClick = {},
            onCartClick = {},
            onSearchClick = {},
            onCategoryClick = {},
            onHomeTabClick = {},
            onShopTabClick = {},
            onFavoritesTabClick = {},
            onProfileTabClick = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 360, uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun ShopScreenLandscapePreviewLight() {
    MobileAppLumeaTheme {
        ShopScreen(
            onProductClick = {},
            onBackClick = {},
            onCartClick = {},
            onSearchClick = {},
            onCategoryClick = {},
            onHomeTabClick = {},
            onShopTabClick = {},
            onFavoritesTabClick = {},
            onProfileTabClick = {}
        )
    }
}
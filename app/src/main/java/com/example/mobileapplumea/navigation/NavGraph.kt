package com.example.mobileapplumea.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp

import com.example.mobileapplumea.ui.screens.getstarted.GetStartedScreen
import com.example.mobileapplumea.ui.screens.home.HomeScreen
import com.example.mobileapplumea.ui.screens.login.LoginScreen
import com.example.mobileapplumea.ui.screens.signup.SignupScreen
import com.example.mobileapplumea.data.productList
import com.example.mobileapplumea.ui.screens.productdetail.ProductDetailScreen
import com.example.mobileapplumea.ui.screens.shop.ShopScreen
import com.example.mobileapplumea.ui.screens.favorites.FavoritesScreen
import com.example.mobileapplumea.ui.screens.profile.ProfileScreen
import com.example.mobileapplumea.ui.screens.cart.CartScreen // <-- Import CartScreen
import com.example.mobileapplumea.ui.screens.cart.dummyCartItems // <-- IMPORTANT: Import dummyCartItems
import com.example.mobileapplumea.ui.screens.payment.PaymentScreen

/**
 * Defines the routes for different screens in the application.
 */
sealed class Screen(val route: String) {
    object GetStarted : Screen("get_started_screen")
    object Login : Screen("login_screen")
    object SignUp : Screen("sign_up_screen")
    object Home : Screen("home_screen")
    object Shop : Screen("shop_screen")
    object ProductDetail : Screen("product_detail_screen/{productId}") {
        fun createRoute(productId: String) = "product_detail_screen/$productId"
    }
    object Favorites : Screen("favorites_screen")
    object Profile : Screen("profile_screen")
    object Cart : Screen("cart_screen")
    object Payment : Screen("payment_screen") // <-- NEW: Payment Screen route

}

/**
 * Defines the navigation graph for the Lumea application.
 *
 * @param navController The NavHostController to manage navigation.
 */
@Composable
fun LumeaNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.GetStarted.route
    ) {
        // Get Started Screen
        composable(Screen.GetStarted.route) {
            GetStartedScreen(
                onGetStartedClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.GetStarted.route) { inclusive = true }
                    }
                }
            )
        }

        // Login Screen
        composable(Screen.Login.route) {
            LoginScreen(
                onSignInClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onForgotPasswordClick = { /* TODO */ },
                onGoogleSignInClick = { /* TODO */ },
                onFacebookSignInClick = { /* TODO */ },
                onCreateAccountClick = { navController.navigate(Screen.SignUp.route) }
            )
        }

        // SignUp Screen
        composable(Screen.SignUp.route) {
            SignupScreen(
                onRegisterClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.GetStarted.route) { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.popBackStack(Screen.Login.route, inclusive = false)
                },
                onGoogleSignInClick = { /* TODO */ },
                onFacebookSignInClick = { /* TODO */ }
            )
        }

        // Home Screen
        composable(Screen.Home.route) {
            HomeScreen(
                onProductClick = { product ->
                    navController.navigate(Screen.ProductDetail.createRoute(product.id))
                },
                onCartClick = { navController.navigate(Screen.Cart.route) }, // Navigation to Cart
                onSearchClick = { /* TODO: Handle search logic */ },
                onCategoryClick = { /* TODO: Handle category filtering on Home if needed */ },
                onHomeTabClick = { /* Already on Home, perhaps scroll to top or do nothing */ },
                onShopTabClick = { navController.navigate(Screen.Shop.route) {
                    popUpTo(Screen.Home.route) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                } },
                onFavoritesTabClick = { navController.navigate(Screen.Favorites.route) {
                    popUpTo(Screen.Home.route) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                } },
                onProfileTabClick = { navController.navigate(Screen.Profile.route) {
                    popUpTo(Screen.Home.route) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                } }
            )
        }

        // Shop Screen
        composable(Screen.Shop.route) {
            ShopScreen(
                onProductClick = { product ->
                    navController.navigate(Screen.ProductDetail.createRoute(product.id))
                },
                onBackClick = { navController.popBackStack() },
                onCartClick = { navController.navigate(Screen.Cart.route) }, // Navigation to Cart
                onSearchClick = { /* TODO: Handle search logic for shop */ },
                onCategoryClick = { /* TODO: Handle category filtering for shop */ },
                onHomeTabClick = { navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                } },
                onShopTabClick = { /* Already on Shop, do nothing or reset state if needed */ },
                onFavoritesTabClick = { navController.navigate(Screen.Favorites.route) {
                    popUpTo(Screen.Shop.route) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                } },
                onProfileTabClick = { navController.navigate(Screen.Profile.route) {
                    popUpTo(Screen.Shop.route) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                } }
            )
        }

        // Favorites Screen
        composable(Screen.Favorites.route) {
            FavoritesScreen(
                onProductClick = { product ->
                    navController.navigate(Screen.ProductDetail.createRoute(product.id))
                },
                onBackClick = { navController.popBackStack() },
                onCartClick = { navController.navigate(Screen.Cart.route) }, // Navigation to Cart
                onHomeTabClick = { navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                } },
                onShopTabClick = { navController.navigate(Screen.Shop.route) {
                    popUpTo(Screen.Shop.route) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                } },
                onFavoritesTabClick = { /* Already on Favorites, do nothing or reset state */ },
                onProfileTabClick = { navController.navigate(Screen.Profile.route) {
                    popUpTo(Screen.Profile.route) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                } }
            )
        }

        // Profile Screen
        composable(Screen.Profile.route) {
            ProfileScreen(
                onBackClick = { navController.popBackStack() },
                onEditProfileClick = { /* TODO: Implement edit profile */ },
                onPrivacySettingsClick = { /* TODO: Implement privacy settings */ },
                onNotificationsClick = { /* TODO: Implement notifications */ },
                onBeautyQuizClick = { /* TODO: Implement beauty quiz */ },
                onOrderHistoryClick = { /* TODO: Implement order history */ },
                onShippingAddressClick = { /* TODO: Implement shipping address */ },
                onSignOutClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onHomeTabClick = { navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                } },
                onShopTabClick = { navController.navigate(Screen.Shop.route) {
                    popUpTo(Screen.Shop.route) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                } },
                onFavoritesTabClick = { navController.navigate(Screen.Favorites.route) {
                    popUpTo(Screen.Favorites.route) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                } },
                onProfileTabClick = { /* Already on Profile, do nothing or reset state */ }
            )
        }

        // Cart Screen
        // Cart Screen
        composable(Screen.Cart.route) {
            CartScreen(
                initialCartItems = dummyCartItems,
                onBackClick = { navController.popBackStack() },
                onPayNowClick = { navController.navigate(Screen.Payment.route) } // <-- UPDATED: Navigate to PaymentScreen
            )
        }

        // NEW: Payment Screen
        composable(Screen.Payment.route) {
            PaymentScreen(
                onBackClick = { navController.popBackStack() },
                onPaymentSuccess = { /* This callback is not directly used for navigation in NavGraph */ },
                onContinueShoppingClick = {
                    // Navigate to Home and clear the back stack up to Home
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true } // Clear back stack up to Home
                        launchSingleTop = true
                    }
                },
                onGoToOrdersClick = {
                    // Navigate back to Cart (as per your request)
                    navController.navigate(Screen.Cart.route) {
                        popUpTo(Screen.Cart.route) { inclusive = true } // Clear Payment from back stack
                        launchSingleTop = true
                    }
                }
            )
        }

        // Product Detail Screen
        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            val product = productList.find { it.id == productId }

            if (product != null) {
                ProductDetailScreen(
                    product = product,
                    onBackClick = { navController.popBackStack() },
                    onAddToCartClick = { /* TODO: Handle add to cart logic */ }
                )
            } else {
                Text(
                    "Error: Product not found!",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
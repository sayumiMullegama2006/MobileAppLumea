package com.example.mobileapplumea

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController // Import for navController
import com.example.mobileapplumea.navigation.LumeaNavGraph // Import your NavGraph
import com.example.mobileapplumea.ui.theme.MobileAppLumeaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileAppLumeaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController() // Create NavController
                    LumeaNavGraph(navController = navController) // Use your NavGraph
                }
            }
        }
    }
}

// You can keep a simplified preview here, or remove it as NavGraph handles navigation
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MobileAppLumeaTheme {
        // This preview is now more for the overall theme, individual screen previews are better
        // For example: GetStartedScreen(onGetStartedClick = {})
    }
}
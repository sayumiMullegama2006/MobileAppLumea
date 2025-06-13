package com.example.mobileapplumea.ui.screens.login

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobileapplumea.R
import com.example.mobileapplumea.ui.theme.MobileAppLumeaTheme
import com.example.mobileapplumea.ui.theme.LumeaBackgroundDark
import com.example.mobileapplumea.ui.theme.LumeaBackgroundLight
import com.example.mobileapplumea.ui.theme.LumeaPinkDark
import com.example.mobileapplumea.ui.theme.LumeaPinkDarkPrimary
import com.example.mobileapplumea.ui.theme.LumeaPinkLight
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onSignInClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onGoogleSignInClick: () -> Unit,
    onFacebookSignInClick: () -> Unit,
    onCreateAccountClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val darkTheme = isSystemInDarkTheme()

    // Revised radial gradient for a "mist gradient" and subtle glow
    val backgroundBrush = if (darkTheme) {
        Brush.radialGradient(
            colors = listOf(
                Color(0xFF5A4A5E), // A desaturated, muted purple-pink for a misty center glow
                Color(0xFF3B2F3F), // Fades to a slightly darker, muted tone
                LumeaBackgroundDark // Fades to the standard dark background
            ),
            radius = 1600f // Larger radius for a softer, broader, more ethereal glow
        )
    } else {
        Brush.radialGradient(
            colors = listOf(
                Color(0xFFFFFFFF), // Pure white or very, very light desaturated pink for the very center
                Color(0xFFF0E0E5), // A very light, desaturated pink for a misty effect
                LumeaBackgroundLight // Fades to the standard light background color
            ),
            radius = 1600f // Larger radius for a softer, broader, more ethereal glow
        )
    }

    var usernameOrEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundBrush)
        ) {
            if (isLandscape) {
                LandscapeLoginContent(
                    usernameOrEmail = usernameOrEmail,
                    onUsernameOrEmailChange = { usernameOrEmail = it },
                    password = password,
                    onPasswordChange = { password = it },
                    onSignInClick = onSignInClick,
                    onForgotPasswordClick = onForgotPasswordClick,
                    onGoogleSignInClick = onGoogleSignInClick,
                    onFacebookSignInClick = onFacebookSignInClick,
                    onCreateAccountClick = onCreateAccountClick
                )
            } else {
                PortraitLoginContent(
                    usernameOrEmail = usernameOrEmail,
                    onUsernameOrEmailChange = { usernameOrEmail = it },
                    password = password,
                    onPasswordChange = { password = it },
                    onSignInClick = onSignInClick,
                    onForgotPasswordClick = onForgotPasswordClick,
                    onGoogleSignInClick = onGoogleSignInClick,
                    onFacebookSignInClick = onFacebookSignInClick,
                    onCreateAccountClick = onCreateAccountClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PortraitLoginContent(
    usernameOrEmail: String,
    onUsernameOrEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onGoogleSignInClick: () -> Unit,
    onFacebookSignInClick: () -> Unit,
    onCreateAccountClick: () -> Unit
) {
    val darkTheme = isSystemInDarkTheme()

    val buttonContainerColor = if (darkTheme) LumeaPinkDarkPrimary else LumeaPinkDark
    val buttonContentColor = if (darkTheme) Color.Black else MaterialTheme.colorScheme.onPrimary

    val inputBorderColor = if (darkTheme) LumeaPinkDarkPrimary.copy(alpha = 0.7f) else LumeaPinkDark.copy(alpha = 0.7f)
    val inputBackgroundColor = if (darkTheme) Color.White.copy(alpha = 0.1f) else Color.White.copy(alpha = 0.85f)
    val inputTextColor = if (darkTheme) Color.White else Color.Black
    val inputPlaceholderColor = if (darkTheme) Color.LightGray.copy(alpha = 0.7f) else Color.Gray.copy(alpha = 0.7f)
    val inputLabelColor = if (darkTheme) Color.White.copy(alpha = 0.8f) else Color.Gray.copy(alpha = 0.8f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // App Logo/Image - MADE BIGGER
        Image(
            painter = painterResource(id = R.drawable.ic_lumea_logo_circle), // Your circular logo image
            contentDescription = "Lumea App Logo",
            modifier = Modifier
                .size(200.dp) // Increased size from 100.dp to 120.dp
                .padding(bottom = 16.dp)
        )

        // LUMEA Text - Font Weight changed to Bold, slightly adjusted size
        Box(
            modifier = Modifier
                .wrapContentSize(align = Alignment.Center)
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = "LUMEA",
                style = MaterialTheme.typography.displayLarge.copy(fontSize = 52.sp, fontWeight = FontWeight.Bold), // Changed to Bold, slightly smaller fontSize
                color = LumeaPinkLight,
                textAlign = TextAlign.Center,
                modifier = Modifier.graphicsLayer {
                    translationX = 2.dp.toPx()
                    translationY = 2.dp.toPx()
                    alpha = 0.5f
                }
            )
            Text(
                text = "LUMEA",
                style = MaterialTheme.typography.displayLarge.copy(fontSize = 52.sp, fontWeight = FontWeight.Bold), // Changed to Bold, slightly smaller fontSize
                color = LumeaPinkLight,
                textAlign = TextAlign.Center
            )
        }

        // Welcome Message
        Text(
            text = "Welcome Back!",
            style = MaterialTheme.typography.headlineLarge,
            color = if (darkTheme) Color.White else Color.Black.copy(alpha = 0.9f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Input Fields
        OutlinedTextField(
            value = usernameOrEmail,
            onValueChange = onUsernameOrEmailChange,
            label = { Text("Username or Email") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Username/Email Icon", tint = inputTextColor) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = inputBorderColor,
                unfocusedBorderColor = inputBorderColor,
                focusedContainerColor = inputBackgroundColor,
                unfocusedContainerColor = inputBackgroundColor,
                cursorColor = inputBorderColor,
                focusedTextColor = inputTextColor,
                unfocusedTextColor = inputTextColor,
                focusedPlaceholderColor = inputPlaceholderColor,
                unfocusedPlaceholderColor = inputPlaceholderColor,
                focusedLabelColor = inputLabelColor,
                unfocusedLabelColor = inputPlaceholderColor,
                focusedLeadingIconColor = inputTextColor,
                unfocusedLeadingIconColor = inputTextColor
            )
        )

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password Icon", tint = inputTextColor) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = inputBorderColor,
                unfocusedBorderColor = inputBorderColor,
                focusedContainerColor = inputBackgroundColor,
                unfocusedContainerColor = inputBackgroundColor,
                cursorColor = inputBorderColor,
                focusedTextColor = inputTextColor,
                unfocusedTextColor = inputTextColor,
                focusedPlaceholderColor = inputPlaceholderColor,
                unfocusedPlaceholderColor = inputPlaceholderColor,
                focusedLabelColor = inputLabelColor,
                unfocusedLabelColor = inputPlaceholderColor,
                focusedLeadingIconColor = inputTextColor,
                unfocusedLeadingIconColor = inputTextColor
            )
        )

        // Forgot Password Link
        Text(
            text = "Forgot Password?",
            color = if (darkTheme) LumeaPinkLight.copy(alpha = 0.8f) else LumeaPinkDark.copy(alpha = 0.8f),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .clickable(onClick = onForgotPasswordClick),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodySmall
        )

        // Sign In Button
        Button(
            onClick = onSignInClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonContainerColor,
                contentColor = buttonContentColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Sign In",
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp)
            )
        }

        // Separator
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Divider(modifier = Modifier.weight(1f), color = if (darkTheme) Color.White.copy(alpha = 0.5f) else Color.Gray.copy(alpha = 0.5f), thickness = 1.dp)
            Text(
                text = "  - OR Continue with -  ",
                color = if (darkTheme) Color.White.copy(alpha = 0.8f) else Color.Gray.copy(alpha = 0.8f),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Divider(modifier = Modifier.weight(1f), color = if (darkTheme) Color.White.copy(alpha = 0.5f) else Color.Gray.copy(alpha = 0.5f), thickness = 1.dp)
        }

        // Social Login Options (Circular)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Google Icon (Image based)
            Surface(
                modifier = Modifier
                    .size(56.dp)
                    .clickable(onClick = onGoogleSignInClick),
                shape = RoundedCornerShape(50),
                color = Color.White
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Sign in with Google",
                    modifier = Modifier.size(32.dp).wrapContentSize(align = Alignment.Center)
                )
            }
            // Facebook Icon (Image based)
            Surface(
                modifier = Modifier
                    .size(56.dp)
                    .clickable(onClick = onFacebookSignInClick),
                shape = RoundedCornerShape(50),
                color = Color.White
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_facebook),
                    contentDescription = "Sign in with Facebook",
                    modifier = Modifier.size(32.dp).wrapContentSize(align = Alignment.Center)
                )
            }
        }

        // Account Creation Prompt
        Text(
            text = buildAnnotatedString {
                append("Create An Account? ")
                withStyle(style = SpanStyle(color = if (darkTheme) LumeaPinkDarkPrimary else LumeaPinkDark, fontWeight = FontWeight.SemiBold)) {
                    append("Sign Up")
                }
            },
            color = if (darkTheme) Color.White.copy(alpha = 0.8f) else Color.Gray.copy(alpha = 0.8f),
            modifier = Modifier.clickable(onClick = onCreateAccountClick),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LandscapeLoginContent(
    usernameOrEmail: String,
    onUsernameOrEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onGoogleSignInClick: () -> Unit,
    onFacebookSignInClick: () -> Unit,
    onCreateAccountClick: () -> Unit
) {
    val darkTheme = isSystemInDarkTheme()

    val buttonContainerColor = if (darkTheme) LumeaPinkDarkPrimary else LumeaPinkDark
    val buttonContentColor = if (darkTheme) Color.Black else MaterialTheme.colorScheme.onPrimary

    val inputBorderColor = if (darkTheme) LumeaPinkDarkPrimary.copy(alpha = 0.7f) else LumeaPinkDark.copy(alpha = 0.7f)
    val inputBackgroundColor = if (darkTheme) Color.White.copy(alpha = 0.1f) else Color.White.copy(alpha = 0.85f)
    val inputTextColor = if (darkTheme) Color.White else Color.Black
    val inputPlaceholderColor = if (darkTheme) Color.LightGray.copy(alpha = 0.7f) else Color.Gray.copy(alpha = 0.7f)
    val inputLabelColor = if (darkTheme) Color.White.copy(alpha = 0.8f) else Color.Gray.copy(alpha = 0.8f)


    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side: Logo and Welcome Message
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Logo/Image - MADE BIGGER for Landscape
            Image(
                painter = painterResource(id = R.drawable.ic_lumea_logo_circle),
                contentDescription = "Lumea App Logo",
                modifier = Modifier.size(100.dp).padding(bottom = 8.dp)
            )
            // LUMEA Text for Landscape
            Box(
                modifier = Modifier
                    .wrapContentSize(align = Alignment.Center)
                    .padding(bottom = 8.dp)
            ) {
                Text(
                    text = "LUMEA",
                    style = MaterialTheme.typography.displayMedium.copy(fontSize = 36.sp, fontWeight = FontWeight.Bold),
                    color = LumeaPinkLight,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.graphicsLayer {
                        translationX = 1.dp.toPx()
                        translationY = 1.dp.toPx()
                        alpha = 0.5f
                    }
                )
                Text(
                    text = "LUMEA",
                    style = MaterialTheme.typography.displayMedium.copy(fontSize = 36.sp, fontWeight = FontWeight.Bold),
                    color = LumeaPinkLight,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                text = "Welcome Back!",
                style = MaterialTheme.typography.headlineMedium,
                color = if (darkTheme) Color.White else Color.Black.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.width(32.dp))

        // Right side: Login form and social options
        Column(
            modifier = Modifier.weight(1.5f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = usernameOrEmail,
                onValueChange = onUsernameOrEmailChange,
                label = { Text("Username or Email") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Username/Email Icon", tint = inputTextColor) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = inputBorderColor,
                    unfocusedBorderColor = inputBorderColor,
                    focusedContainerColor = inputBackgroundColor,
                    unfocusedContainerColor = inputBackgroundColor,
                    cursorColor = inputBorderColor,
                    focusedTextColor = inputTextColor,
                    unfocusedTextColor = inputTextColor,
                    focusedPlaceholderColor = inputPlaceholderColor,
                    unfocusedPlaceholderColor = inputPlaceholderColor,
                    focusedLabelColor = inputLabelColor,
                    unfocusedLabelColor = inputPlaceholderColor,
                    focusedLeadingIconColor = inputTextColor,
                    unfocusedLeadingIconColor = inputTextColor
                )
            )

            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password Icon", tint = inputTextColor) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = inputBorderColor,
                    unfocusedBorderColor = inputBorderColor,
                    focusedContainerColor = inputBackgroundColor,
                    unfocusedContainerColor = inputBackgroundColor,
                    cursorColor = inputBorderColor,
                    focusedTextColor = inputTextColor,
                    unfocusedTextColor = inputTextColor,
                    focusedPlaceholderColor = inputPlaceholderColor,
                    unfocusedPlaceholderColor = inputPlaceholderColor,
                    focusedLabelColor = inputLabelColor,
                    unfocusedLabelColor = inputPlaceholderColor,
                    focusedLeadingIconColor = inputTextColor,
                    unfocusedLeadingIconColor = inputTextColor
                )
            )

            Text(
                text = "Forgot Password?",
                color = if (darkTheme) LumeaPinkLight.copy(alpha = 0.8f) else LumeaPinkDark.copy(alpha = 0.8f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .clickable(onClick = onForgotPasswordClick),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodySmall
            )

            Button(
                onClick = onSignInClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonContainerColor,
                    contentColor = buttonContentColor
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Sign In",
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Divider(modifier = Modifier.weight(1f), color = if (darkTheme) Color.White.copy(alpha = 0.5f) else Color.Gray.copy(alpha = 0.5f), thickness = 1.dp)
                Text(
                    text = "  - OR Continue with -  ",
                    color = if (darkTheme) Color.White.copy(alpha = 0.8f) else Color.Gray.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Divider(modifier = Modifier.weight(1f), color = if (darkTheme) Color.White.copy(alpha = 0.5f) else Color.Gray.copy(alpha = 0.5f), thickness = 1.dp)
            }

            // Social Login Options (Circular)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Google Icon
                Surface(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable(onClick = onGoogleSignInClick),
                    shape = RoundedCornerShape(50),
                    color = Color.White
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "Sign in with Google",
                        modifier = Modifier.size(28.dp).wrapContentSize(align = Alignment.Center)
                    )
                }
                // Facebook Icon
                Surface(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable(onClick = onFacebookSignInClick),
                    shape = RoundedCornerShape(50),
                    color = Color.White
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_facebook),
                        contentDescription = "Sign in with Facebook",
                        modifier = Modifier.size(28.dp).wrapContentSize(align = Alignment.Center)
                    )
                }
            }

            Text(
                text = buildAnnotatedString {
                    append("Create An Account? ")
                    withStyle(style = SpanStyle(color = if (darkTheme) LumeaPinkDarkPrimary else LumeaPinkDark, fontWeight = FontWeight.SemiBold)) {
                        append("Sign Up")
                    }
                },
                color = if (darkTheme) Color.White.copy(alpha = 0.8f) else Color.Gray.copy(alpha = 0.8f),
                modifier = Modifier.clickable(onClick = onCreateAccountClick),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun LoginScreenPreviewLight() {
    MobileAppLumeaTheme {
        LoginScreen(
            onSignInClick = {},
            onForgotPasswordClick = {},
            onGoogleSignInClick = {},
            onFacebookSignInClick = {},
            onCreateAccountClick = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun LoginScreenPreviewDark() {
    MobileAppLumeaTheme(darkTheme = true) {
        LoginScreen(
            onSignInClick = {},
            onForgotPasswordClick = {},
            onGoogleSignInClick = {},
            onFacebookSignInClick = {},
            onCreateAccountClick = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 360, uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun LoginScreenPreviewLandscapeLight() {
    MobileAppLumeaTheme {
        LoginScreen(
            onSignInClick = {},
            onForgotPasswordClick = {},
            onGoogleSignInClick = {},
            onFacebookSignInClick = {},
            onCreateAccountClick = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 360, uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun LoginScreenPreviewLandscapeDark() {
    MobileAppLumeaTheme(darkTheme = true) {
        LoginScreen(
            onSignInClick = {},
            onForgotPasswordClick = {},
            onGoogleSignInClick = {},
            onFacebookSignInClick = {},
            onCreateAccountClick = {}
        )
    }
}
package com.example.mobileapplumea.ui.screens.signup

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
import androidx.compose.material3.*
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
import com.example.mobileapplumea.R // Ensure your R file is correctly imported for drawables
import com.example.mobileapplumea.ui.theme.MobileAppLumeaTheme
import com.example.mobileapplumea.ui.theme.LumeaBackgroundDark
import com.example.mobileapplumea.ui.theme.LumeaBackgroundLight
import com.example.mobileapplumea.ui.theme.LumeaPinkDark
import com.example.mobileapplumea.ui.theme.LumeaPinkDarkPrimary
import com.example.mobileapplumea.ui.theme.LumeaPinkLight
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalConfiguration // Import for LocalConfiguration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    onGoogleSignInClick: () -> Unit,
    onFacebookSignInClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val darkTheme = isSystemInDarkTheme()

    // Background gradient, consistent with LoginScreen for a professional mixture
    val backgroundBrush = if (darkTheme) {
        Brush.radialGradient(
            colors = listOf(
                Color(0xFF3B2F3F), // Slightly warmer, muted dark pink/purple for center glow
                LumeaBackgroundDark // Fades to the standard dark background
            ),
            radius = 1200f
        )
    } else {
        Brush.radialGradient(
            colors = listOf(
                Color(0xFFFBF4F8), // Very light, almost white pink for center
                LumeaBackgroundLight // Fades to the standard light background
            ),
            radius = 1200f
        )
    }

    var usernameOrEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

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
                LandscapeSignupContent( // <--- Using Landscape content for landscape orientation
                    usernameOrEmail = usernameOrEmail,
                    onUsernameOrEmailChange = { usernameOrEmail = it },
                    password = password,
                    onPasswordChange = { password = it },
                    confirmPassword = confirmPassword,
                    onConfirmPasswordChange = { confirmPassword = it },
                    onRegisterClick = onRegisterClick,
                    onGoogleSignInClick = onGoogleSignInClick,
                    onFacebookSignInClick = onFacebookSignInClick,
                    onLoginClick = onLoginClick
                )
            } else {
                PortraitSignupContent( // <--- Using Portrait content for portrait orientation
                    usernameOrEmail = usernameOrEmail,
                    onUsernameOrEmailChange = { usernameOrEmail = it },
                    password = password,
                    onPasswordChange = { password = it },
                    confirmPassword = confirmPassword,
                    onConfirmPasswordChange = { confirmPassword = it },
                    onRegisterClick = onRegisterClick,
                    onGoogleSignInClick = onGoogleSignInClick,
                    onFacebookSignInClick = onFacebookSignInClick,
                    onLoginClick = onLoginClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PortraitSignupContent(
    usernameOrEmail: String,
    onUsernameOrEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onGoogleSignInClick: () -> Unit,
    onFacebookSignInClick: () -> Unit,
    onLoginClick: () -> Unit
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
        // "Create an Account!" Title
        Text(
            text = "Create an Account!",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            color = if (darkTheme) Color.White else Color.Black.copy(alpha = 0.9f),
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        // Username or Email Input Field
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

        // Password Input Field
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

        // Confirm Password Input Field
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = { Text("Confirm Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Confirm Password Icon", tint = inputTextColor) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
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

        // Agreement Text
        Text(
            text = buildAnnotatedString {
                append("By clicking the ")
                withStyle(style = SpanStyle(color = if (darkTheme) LumeaPinkDarkPrimary else LumeaPinkDark, fontWeight = FontWeight.Bold)) {
                    append("Register")
                }
                append(" button, you agree to the ")
                withStyle(style = SpanStyle(color = if (darkTheme) LumeaPinkDarkPrimary else LumeaPinkDark, fontWeight = FontWeight.Bold)) {
                    append("public offer")
                }
            },
            color = if (darkTheme) Color.White.copy(alpha = 0.8f) else Color.Gray.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Create Account Button
        Button(
            onClick = onRegisterClick,
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
                text = "Create Account",
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
            // Google Icon
            Surface(
                modifier = Modifier
                    .size(56.dp)
                    .clickable(onClick = onGoogleSignInClick),
                shape = RoundedCornerShape(50),
                color = Color.White
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_google), // Ensure this drawable exists
                    contentDescription = "Sign in with Google",
                    modifier = Modifier.size(32.dp).wrapContentSize(align = Alignment.Center)
                )
            }
            // Facebook Icon
            Surface(
                modifier = Modifier
                    .size(56.dp)
                    .clickable(onClick = onFacebookSignInClick),
                shape = RoundedCornerShape(50),
                color = Color.White
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_facebook), // Ensure this drawable exists
                    contentDescription = "Sign in with Facebook",
                    modifier = Modifier.size(32.dp).wrapContentSize(align = Alignment.Center)
                )
            }
        }

        // "I Already Have an Account Login" Text
        Text(
            text = buildAnnotatedString {
                append("I Already Have an Account ")
                withStyle(style = SpanStyle(color = if (darkTheme) LumeaPinkDarkPrimary else LumeaPinkDark, fontWeight = FontWeight.SemiBold)) {
                    append("Login")
                }
            },
            color = if (darkTheme) Color.White.copy(alpha = 0.8f) else Color.Gray.copy(alpha = 0.8f),
            modifier = Modifier.clickable(onClick = onLoginClick),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LandscapeSignupContent(
    usernameOrEmail: String,
    onUsernameOrEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onGoogleSignInClick: () -> Unit,
    onFacebookSignInClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val darkTheme = isSystemInDarkTheme()

    val buttonContainerColor = if (darkTheme) LumeaPinkDarkPrimary else LumeaPinkDark
    val buttonContentColor = if (darkTheme) Color.Black else MaterialTheme.colorScheme.onPrimary

    val inputBorderColor = if (darkTheme) LumeaPinkDarkPrimary.copy(alpha = 0.7f) else LumeaPinkDark.copy(alpha = 0.7f)
    val inputBackgroundColor = if (darkTheme) Color.White.copy(alpha = 0.1f) else Color.White.copy(alpha = 0.85f)
    val inputTextColor = if (darkTheme) Color.White else Color.Black
    val inputPlaceholderColor = if (darkTheme) Color.LightGray.copy(alpha = 0.7f) else Color.Gray.copy(alpha = 0.7f)
    val inputLabelColor = if (darkTheme) Color.White.copy(alpha = 0.8f) else Color.Gray.copy(alpha = 0.8f)

    // Using a Row to place title/agreement on left and inputs/buttons on right
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left Column: Title and Agreement Text
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Create an Account!",
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = if (darkTheme) Color.White else Color.Black.copy(alpha = 0.9f),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp) // Adjusted padding for landscape
            )
            // Agreement Text
            Text(
                text = buildAnnotatedString {
                    append("By clicking the ")
                    withStyle(style = SpanStyle(color = if (darkTheme) LumeaPinkDarkPrimary else LumeaPinkDark, fontWeight = FontWeight.Bold)) {
                        append("Register")
                    }
                    append(" button, you agree to the ")
                    withStyle(style = SpanStyle(color = if (darkTheme) LumeaPinkDarkPrimary else LumeaPinkDark, fontWeight = FontWeight.Bold)) {
                        append("public offer")
                    }
                },
                color = if (darkTheme) Color.White.copy(alpha = 0.8f) else Color.Gray.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Spacer(modifier = Modifier.width(32.dp))

        // Right Column: Input Fields, Buttons, and Social Options
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
                value = confirmPassword,
                onValueChange = onConfirmPasswordChange,
                label = { Text("Confirm Password") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Confirm Password Icon", tint = inputTextColor) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp), // Reduced padding for landscape
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

            // Create Account Button
            Button(
                onClick = onRegisterClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonContainerColor,
                    contentColor = buttonContentColor
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(bottom = 12.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Create Account",
                    style = MaterialTheme.typography.labelLarge
                )
            }

            // Separator
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
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
                    .padding(bottom = 16.dp),
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
                    append("I Already Have an Account ")
                    withStyle(style = SpanStyle(color = if (darkTheme) LumeaPinkDarkPrimary else LumeaPinkDark, fontWeight = FontWeight.SemiBold)) {
                        append("Login")
                    }
                },
                color = if (darkTheme) Color.White.copy(alpha = 0.8f) else Color.Gray.copy(alpha = 0.8f),
                modifier = Modifier.clickable(onClick = onLoginClick),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun SignupScreenPreviewLight() {
    MobileAppLumeaTheme {
        SignupScreen(
            onRegisterClick = {},
            onLoginClick = {},
            onGoogleSignInClick = {},
            onFacebookSignInClick = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun SignupScreenPreviewDark() {
    MobileAppLumeaTheme(darkTheme = true) {
        SignupScreen(
            onRegisterClick = {},
            onLoginClick = {},
            onGoogleSignInClick = {},
            onFacebookSignInClick = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 360, uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun SignupScreenPreviewLandscapeLight() {
    MobileAppLumeaTheme {
        SignupScreen(
            onRegisterClick = {},
            onLoginClick = {},
            onGoogleSignInClick = {},
            onFacebookSignInClick = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 720, heightDp = 360, uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun SignupScreenPreviewLandscapeDark() {
    MobileAppLumeaTheme(darkTheme = true) {
        SignupScreen(
            onRegisterClick = {},
            onLoginClick = {},
            onGoogleSignInClick = {},
            onFacebookSignInClick = {}
        )
    }
}
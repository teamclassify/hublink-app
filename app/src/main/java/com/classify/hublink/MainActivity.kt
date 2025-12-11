package com.classify.hublink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.classify.hublink.ui.components.NavigationBar
import com.classify.hublink.ui.screens.CompleteProfileScreen // Nuevo import
import com.classify.hublink.ui.screens.LoadingScreen
import com.classify.hublink.ui.screens.LoginScreen
import com.classify.hublink.ui.screens.RegisterScreen
import com.classify.hublink.ui.theme.HublinkTheme
import com.classify.hublink.viewmodel.AuthState
import com.classify.hublink.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HublinkTheme(dynamicColor = false) {
                App()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(viewModel: AuthViewModel = viewModel()) {
    val navController = rememberNavController()
    val authState by viewModel.authState.collectAsState()

    var hasProfile by remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            hasProfile = null

            viewModel.checkUserProfile { exists ->
                hasProfile = exists
            }
        } else if (authState is AuthState.Idle || authState is AuthState.Error) {
            hasProfile = null
        }
    }
    // ----------------------------------------------------

    when (authState) {
        is AuthState.Loading -> LoadingScreen()

        is AuthState.Success -> {
            when (hasProfile) {
                null -> {
                    LoadingScreen()
                }
                true -> {
                    NavigationBar()
                }
                false -> {
                    NavHost(navController = navController, startDestination = "complete_profile") {
                        composable("complete_profile") {
                            CompleteProfileScreen(
                                onProfileSaved = {
                                    navController.navigate("main_home_screen") {
                                        popUpTo("complete_profile") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable("main_home_screen") {
                            NavigationBar()
                        }
                    }
                }
            }
        }

        is AuthState.Idle, is AuthState.Error -> {
            NavHost(navController = navController, startDestination = "login") {
                composable("login") {
                    LoginScreen(
                        onNavigateToRegister = { navController.navigate("register") },
                    )
                }
                composable("register") {
                    RegisterScreen(onNavigateToLogin = { navController.navigate("login") })
                }
            }
        }
    }
}
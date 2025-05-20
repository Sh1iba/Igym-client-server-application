package com.example.igym

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.igym.navigation.navigationRoutes
import com.example.igym.screens.HomeScreen
import com.example.igym.screens.RegistrationScreen
import com.example.igym.screens.SignInScreen

class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = navigationRoutes.SIGN_IN
            ) {
                composable(navigationRoutes.SIGN_IN) {
                    SignInScreen(navController)
                }
                composable(navigationRoutes.HOME) {
                    HomeScreen(navController)
                }
                composable(navigationRoutes.REGISTRATION) {
                    RegistrationScreen(navController)
                }


            }
        }
    }
}
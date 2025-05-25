package com.example.igym

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.igym.manager.AuthManager
import com.example.igym.navigation.navigationRoutes
import com.example.igym.screens.CalculatorScreen
import com.example.igym.screens.HomeScreen
import com.example.igym.screens.MainAppScreen
import com.example.igym.screens.RegistrationScreen
import com.example.igym.screens.SignInScreen
import com.example.igym.screens.calculators.BMICalculatorScreen
import com.example.igym.screens.calculators.IdealWeightCalculatorScreen

class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                val navController = rememberNavController()
                val authManager = remember { AuthManager(this) }

                val startDestination = if (authManager.isLoggedIn()) {
                    "main_app"
                } else {
                    navigationRoutes.SIGN_IN
                }

                NavHost(
                    navController = navController,
                    startDestination = startDestination
                ) {

                    composable(navigationRoutes.SIGN_IN) {
                        SignInScreen(navController)
                    }


                    composable(navigationRoutes.REGISTRATION) {
                        RegistrationScreen(navController)
                    }

                    navigation(
                        route = "main_app",
                        startDestination = navigationRoutes.HOME
                    ) {
                        composable(navigationRoutes.HOME) {
                            MainAppScreen(navController) {
                                HomeScreen(navController)
                            }
                        }
                        composable(navigationRoutes.CALCULATORS) {
                            MainAppScreen(navController) {
                                CalculatorScreen(navController)
                            }
                        }
                        composable(navigationRoutes.BMI_CALCULATOR) {
                            MainAppScreen(navController) {
                                BMICalculatorScreen(navController)
                            }
                        }
                        composable(navigationRoutes.IDEAL_WEIGHT) {
                            MainAppScreen(navController) {
                                IdealWeightCalculatorScreen(navController)
                            }
                        }
                    }
                }
            }
    }
}
package com.example.igym

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.igym.navigation.navigationRoutes
import com.example.igym.screens.HomeScreen
import com.example.igym.screens.SignInScreen
import com.example.igym.ui.theme.IgymTheme

class MainActivity : ComponentActivity() {
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


            }
        }
    }
}
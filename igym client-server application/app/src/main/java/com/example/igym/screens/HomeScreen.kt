package com.example.igym.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.igym.R
import com.example.igym.navigation.navigationRoutes
import com.example.igym.ui.theme.colorDarkGray
import com.example.igym.ui.theme.colorLightPurple
import com.example.igym.ui.theme.colorLightWhite


@Composable
fun HomeScreen(navController: NavController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorDarkGray),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {

            Image(
                painter = painterResource(id = R.drawable.home),
                contentDescription = "Profile image",
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }





@Preview(showBackground = true, showSystemUi = true, name = "pre")
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)

}
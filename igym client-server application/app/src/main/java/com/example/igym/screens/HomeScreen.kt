package com.example.igym.screens

import androidx.compose.foundation.Image
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
import com.example.igym.ui.theme.colorDarkGray
import com.example.igym.ui.theme.colorLightPurple
import com.example.igym.ui.theme.colorLightWhite


@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        containerColor = colorDarkGray
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
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
}
@Composable
fun BottomNavigationBar(navController: NavController) {
    Row(
        modifier = Modifier
            .height(60.dp)
    ) {
    val items = listOf(
        BottomNavItem.Welcome,
        BottomNavItem.Calculators,
        BottomNavItem.Favorites,
        BottomNavItem.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = colorLightPurple,
        contentColor = Color.White
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    if (item is BottomNavItem.Calculators) {
                        Icon(
                            painter = painterResource(id = R.drawable.calc),
                            contentDescription = item.title,
                            modifier = Modifier.size(30.dp)
                        )
                    } else {
                        item.icon?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = item.title,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                },

                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorLightWhite,
                    selectedTextColor = colorLightWhite,
                    unselectedIconColor = colorLightWhite,
                    unselectedTextColor = colorLightWhite,
                    indicatorColor = colorLightWhite
                )
            )
        }
    }
}
}

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector? = null
) {
    object Profile : BottomNavItem(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    )

    object Calculators : BottomNavItem(
        route = "calculators",
        title = "Calculators"
    )

    object Welcome : BottomNavItem(
        route = "welcome",
        title = "Home",
        icon = Icons.Default.Home
    )

    object Favorites : BottomNavItem(
        route = "favorites",
        title = "Favorites",
        icon = Icons.Default.Favorite
    )
}

@Preview(showBackground = true, showSystemUi = true, name = "pre")
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        val navController = rememberNavController()
        HomeScreen(navController = navController)
    }
}
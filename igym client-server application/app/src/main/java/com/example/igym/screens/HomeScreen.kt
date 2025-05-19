package com.example.igym.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController




@Composable
fun HomeScreen(navController: NavController){
    Column(

    ) {  }

}



@Preview(showBackground = true, showSystemUi = true, name = "pre")
@Composable
fun HomeScreenPreview(){
    val navController = rememberNavController()
    SignInScreen(navController = navController)
}
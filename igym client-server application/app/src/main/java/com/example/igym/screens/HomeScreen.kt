package com.example.igym.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.igym.R
import com.example.igym.manager.AuthManager
import com.example.igym.navigation.navigationRoutes
import com.example.igym.network.api.ApiClient
import com.example.igym.network.model.response.workout.WorkoutsResponse

import com.example.igym.ui.theme.colorDarkGray
import com.example.igym.ui.theme.colorLightPurple
import com.example.igym.ui.theme.colorLightWhite
import com.example.igym.ui.theme.colorYellowGreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val authManager = remember { AuthManager(context) }
    val (selectedTab, setSelectedTab) = remember { mutableStateOf("workouts") }
    val fullName = authManager.getFullName() ?: "Unknown"
    val authToken = remember { authManager.getAuthToken() }

    val workouts = remember { mutableStateListOf<WorkoutsResponse>() }

    LaunchedEffect(selectedTab) {
        if (selectedTab == "workouts" && workouts.isEmpty()) {
            try {
                if (authToken != null) {
                    val response = ApiClient.gymApi.getWorkouts(authToken)
                    if (response.isSuccessful) {
                        response.body()?.let { workouts.addAll(it) }
                    }
                }
            } catch (e: Exception) {
                Log.e("WorkoutsAPI", "Ошибка загрузки", e)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorDarkGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)
                .padding(top = 30.dp)
        ) {
            Text(
                text = "Привет, $fullName",
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                fontSize = 20.sp,
                color = colorLightPurple,
            )
            Text(
                text = "Пора бросить вызов своим возможностям.",
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontSize = 14.sp,
                color = colorLightWhite,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)
                .padding(top = 16.dp)
        ) {
            Button(
                onClick = { setSelectedTab("workouts") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedTab == "workouts") colorYellowGreen else colorLightWhite,
                    contentColor = if (selectedTab == "workouts") colorDarkGray else colorLightPurple
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text("Тренировки", fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { setSelectedTab("muscleGroups") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedTab == "muscleGroups") colorYellowGreen else colorLightWhite,
                    contentColor = if (selectedTab == "muscleGroups") colorDarkGray else colorLightPurple
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text("Группы Мышц", fontSize = 14.sp)
            }
        }

        when (selectedTab) {
            "workouts" -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(workouts) { workout ->
                        WorkoutBoxItem(
                            workout = workout,
                            onClick = {
                                navController.navigate("${navigationRoutes.WORKOUT_DETAILS}/${workout.id}")
                            }
                        )

                    }
                }
            }
            "muscleGroups" -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Группы мышц будут здесь",
                        color = colorLightWhite
                    )
                }
            }
        }
    }
}

@Composable
fun WorkoutBoxItem(
    workout: WorkoutsResponse,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(color = colorLightPurple)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = workout.title,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                color = colorDarkGray,
                fontSize = 20.sp,
                lineHeight = 24.sp,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Группа: ${workout.muscleGroup}",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                color = colorDarkGray,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = workout.description,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                color = colorDarkGray,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "pre")
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)

}
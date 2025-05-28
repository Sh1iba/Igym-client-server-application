package com.example.igym.screens.workout
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.igym.R
import com.example.igym.manager.AuthManager
import com.example.igym.network.api.ApiClient
import com.example.igym.network.model.response.workout.Exercise
import com.example.igym.network.model.response.workout.WorkoutDetails

import com.example.igym.ui.theme.colorDarkGray
import com.example.igym.ui.theme.colorLightPurple
import com.example.igym.ui.theme.colorLightWhite
import com.example.igym.ui.theme.colorYellowGreen


@Composable
fun WorkoutDetailsScreen(
    navController: NavController,
    workoutId: Long
) {
    val context = LocalContext.current
    val authManager = remember { AuthManager(context) }
    val authToken = remember { authManager.getAuthToken() }
    val workoutDetails = remember { mutableStateOf<WorkoutDetails?>(null) }

    LaunchedEffect(workoutId) {
        try {
            if (authToken != null) {
                val response = ApiClient.gymApi.getWorkoutDetails(workoutId, authToken)
                if (response.isSuccessful) {
                    workoutDetails.value = response.body()
                }
            }
        } catch (e: Exception) {
            Log.e("WorkoutDetails", "Ошибка загрузки", e)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorDarkGray)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
            ) {

                Row(
                    modifier = Modifier
                        .padding(top = 30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_left_24),
                        contentDescription = "Назад",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { navController.popBackStack() },
                        tint = colorYellowGreen
                    )
                    Text(
                        text = workoutDetails.value?.title ?: "Тренировка",
                        fontFamily = FontFamily(Font(R.font.poppins_bold)),
                        fontSize = 20.sp,
                        color = colorLightPurple,
                    )
                }

                Text(
                    text = "Группа: ${workoutDetails.value?.muscleGroup ?: ""}",
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    fontSize = 16.sp,
                    color = colorLightWhite,
                    modifier = Modifier
                        .padding(start = 15.dp)

                )
            }
        }


        workoutDetails.value?.let { details ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(details.exercises) { exercise ->
                    ExerciseItem(exercise = exercise)
                }
            }
        }
    }
}

@Composable
fun ExerciseItem(exercise: Exercise) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = colorLightPurple)
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = exercise.name,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                color = colorDarkGray,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = exercise.description,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                color = colorDarkGray,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WorkoutScreenPreview() {
    val navController = rememberNavController()
    WorkoutDetailsScreen(
        navController = navController,
        workoutId = 1L
    )
}
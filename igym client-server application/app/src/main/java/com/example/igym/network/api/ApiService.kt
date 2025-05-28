package com.example.igym.network.api

import com.example.igym.network.model.request.LoginRequest
import com.example.igym.network.model.request.RegisterRequest
import com.example.igym.network.model.request.UserProfile
import com.example.igym.network.model.response.AuthResponse
import com.example.igym.network.model.response.ProfileResponse
import com.example.igym.network.model.response.RegisterResponse
import com.example.igym.network.model.response.workout.Exercise
import com.example.igym.network.model.response.workout.WorkoutDetails
import com.example.igym.network.model.response.workout.WorkoutsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("auth/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("auth/login")
    suspend fun loginUser(@Body request: LoginRequest): Response<AuthResponse>

    @PUT("profiles")
    suspend fun updateProfile(@Body profile: UserProfile,@Header( "Authorization") token : String): Response<ProfileResponse>

    @GET("profiles/{id}")
    suspend fun getProfile(@Path("id") id: Long): UserProfile

    ///------------------------------------Тренировки------------------------------------///

    @GET("workouts")
    suspend fun getWorkouts(@Header( "Authorization") token : String): Response<List<WorkoutsResponse>>

    @GET("workouts/{workoutId}")
    suspend fun getWorkoutDetails(@Path("workoutId") workoutId: Long, @Header("Authorization") token: String):
            Response<WorkoutDetails>

    @GET("workouts/exercises")
    suspend fun getExercisesByMuscleGroup(
        @Query("muscleGroupId") muscleGroupId: Long,
        @Header("Authorization") token: String
    ): Response<List<Exercise>>




}
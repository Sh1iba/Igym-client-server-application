package com.example.igym.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.igym.R
import com.example.igym.manager.LoginManager
import com.example.igym.navigation.navigationRoutes
import com.example.igym.ui.theme.PoppinsFontFamily
import com.example.igym.ui.theme.colorDarkGray
import com.example.igym.ui.theme.colorLightGray
import com.example.igym.ui.theme.colorLightPurple
import com.example.igym.ui.theme.colorLightWhite
import com.example.igym.ui.theme.colorYellowGreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SignInScreen(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorDarkGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)

    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 196.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            Text(
                text = "Войти в Аккаунт",
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                fontSize = 20.sp,
                color = colorYellowGreen,
            )

            Text(
                text = "Продолжайте отслеживать свои тренировки и прогресс в достижении целей." ,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 14.sp,
                lineHeight = 14.sp,
                color = colorLightWhite,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 20.dp)


            )
        }

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .padding(top = 10.dp )
                .background(colorLightPurple)
                .fillMaxWidth()
                .height(210.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 35.dp)
                    .padding(top = 20.dp)

            ){
                Text(
                    text = "Почта",
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = colorDarkGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 2.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(colorLightWhite),
                    contentAlignment = Alignment.CenterStart
                ) {
                    BasicTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 16.sp,
                            lineHeight = 16.sp
                        ),
                        singleLine = true
                    )
                }
            }


            Column(
                modifier = Modifier
                    .padding(horizontal = 35.dp)
                    .padding(top = 10.dp)

            ){
                Text(
                    text = "Пароль",
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = colorDarkGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 2.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(colorLightWhite),
                    contentAlignment = Alignment.CenterStart

                ) {
                    BasicTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 16.sp,
                            lineHeight = 16.sp
                        ),
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        singleLine = true
                    )
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible },
                        modifier = Modifier
                            .size(48.dp)
                            .align(alignment = Alignment.CenterEnd)
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (passwordVisible)
                                    R.drawable.eye_on_foreground
                                else
                                    R.drawable.eye_off_foreground
                            ),
                            contentDescription = if (passwordVisible)
                                "Скрыть пароль"
                            else
                                "Показать пароль",
                            tint = colorDarkGray
                        )
                    }
                }
        }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Text(
                    text = "забыли Пароль?",
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    color = colorDarkGray,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(top = 10.dp, end = 35.dp)
                )
            }
        }

        val loginManager = remember { LoginManager() }
        var errorMessage by remember { mutableStateOf<String?>(null) }
        var isLoading by remember { mutableStateOf(false) }
        val context = LocalContext.current

        LaunchedEffect(errorMessage) {
            errorMessage?.let { message ->
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                errorMessage = null
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Button(
                onClick = {
                    if (email.isEmpty() || password.isEmpty()) {
                        errorMessage = "Все поля должны быть заполнены"
                        Log.d("Login", "Все поля должны быть заполнены")
                        return@Button
                    }
                    errorMessage = null
                    isLoading = true
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            Log.d("Login", "Выполняется вход...")
                            val result = loginManager.loginUser(email, password)

                            withContext(Dispatchers.Main) {
                                isLoading = false

                                when {
                                    result.isSuccess -> {

                                        Log.d("Login", "Успещный вход!")
                                    }

                                    else -> {
                                        val error = result.exceptionOrNull()
                                        errorMessage = when {
                                            error?.message?.contains("400") == true -> "Пользователь не найден"
                                            error?.message?.contains("409") == true -> "Неверный пароль"
                                            error?.message?.contains("500") == true -> "Ошибка сервера"
                                            else -> "Ошибка: ${error?.message ?: "Неизвестная ошибка"}"
                                        }
                                    }
                                }
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                isLoading = false
                                errorMessage = "Ошибка сети: ${e.message}"
                                Log.e("Registration", "Ошибка в корутине", e)
                            }
                        }
                    }
                },
                border = BorderStroke(1.dp, Color.White),
                modifier = Modifier
                    .width(180.dp)
                    .height(44.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorLightGray
                )
            ) {
                Text(
                    text = "Войти",
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    fontSize = 18.sp,
                    lineHeight = 24.sp,
                    color = Color.White,
                    modifier = Modifier
                        .width(57.dp)
                        .height(24.dp)
                )
            }
        }


    }
}

@Preview(showBackground = true, showSystemUi = true, name = "pre")
@Composable
fun SignInScreenPreview(){
    val navController = rememberNavController()
    SignInScreen(navController = navController)
}
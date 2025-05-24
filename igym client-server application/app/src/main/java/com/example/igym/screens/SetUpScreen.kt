package com.example.igym.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.igym.R
import com.example.igym.ui.theme.colorDarkGray
import com.example.igym.ui.theme.colorLightPurple
import com.example.igym.ui.theme.colorLightWhite
import com.example.igym.ui.theme.colorYellowGreen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetUpScreen(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorDarkGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 106.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Заполните свой Профиль",
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                fontSize = 25.sp,
                color = colorLightWhite,
            )

            Text(
                text = "Расскажите о себе — это поможет создать персонализированную программу тренировок и отслеживать ваш прогресс!",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 14.sp,
                lineHeight = 14.sp,
                color = colorLightWhite,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 20.dp)

            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "Profile image",
                modifier = Modifier
                    .width(1200.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val genderOptions = listOf("Мужчина", "Женщина")
            var gender by remember { mutableStateOf("") }
            var isGenderExpanded by remember { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .padding(horizontal = 35.dp)
                    .padding(top = 10.dp)
            ) {
                Text(
                    text = "Пол",
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    fontSize = 14.sp,
                    color = colorLightPurple,
                    modifier = Modifier.padding(start = 2.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(colorLightWhite)
                ) {

                    Text(
                        text = gender.ifEmpty { "Выберите ваш пол" },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                            .align(alignment = Alignment.Center)
                            .clickable { isGenderExpanded = true },
                        color = if (gender.isEmpty()) Color.Gray else Color.Black
                    )


                    Icon(
                        painter = painterResource(R.drawable.arrow_down),
                        contentDescription = "Gender dropdown",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 8.dp)
                            .clickable { isGenderExpanded = true },
                        tint = colorDarkGray
                    )

                    // Выпадающее меню
                    DropdownMenu(
                        expanded = isGenderExpanded,
                        onDismissRequest = { isGenderExpanded = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorLightWhite)
                    ) {
                        genderOptions.forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = option,
                                        fontFamily = FontFamily(Font(R.font.poppins_regular))
                                    )
                                },
                                onClick = {
                                    gender = option
                                    isGenderExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            var birthDate by remember { mutableStateOf("") }
            var showDatePicker by remember { mutableStateOf(false) }
            val dateFormatter = remember {
                SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            }


            Column(
                modifier = Modifier
                    .padding(horizontal = 35.dp)
                    .padding(top = 10.dp)

            ) {
                Text(
                    text = "Дата рождения",
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    fontSize = 14.sp,
                    color = colorLightPurple,
                    modifier = Modifier.padding(start = 2.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(colorLightWhite)
                        .clickable { showDatePicker = true },
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = birthDate.ifEmpty { "дд-мм-гггг" },
                        modifier = Modifier.padding(horizontal = 12.dp),
                        color = if (birthDate.isEmpty()) Color.Gray else Color.Black
                    )
                }
                if (showDatePicker) {
                    val datePickerState = rememberDatePickerState()

                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    datePickerState.selectedDateMillis?.let { millis ->
                                        birthDate =
                                            dateFormatter.format(Date(millis))
                                    }
                                    showDatePicker = false
                                }
                            ) {
                                Text("OK", color = colorLightPurple)
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = { showDatePicker = false }
                            ) {
                                Text("Отмена", color = colorLightPurple)
                            }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }
            }

            var height by remember { mutableStateOf("") }
            var weight by remember { mutableStateOf("") }

            Row(
                modifier = Modifier
                    .padding(horizontal = 35.dp)
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                OutlinedTextField(
                    value = height,
                    onValueChange = { height = it.filter { char -> char.isDigit() } },
                    label = { Text("Рост (см)") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorLightWhite,
                        unfocusedContainerColor = colorLightWhite
                    )
                )

                // Поле для веса
                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it.filter { char -> char.isDigit() } },
                    label = { Text("Вес (кг)") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorLightWhite,
                        unfocusedContainerColor = colorLightWhite
                    )
                )
            }


        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Button(
                onClick = {
                },
                modifier = Modifier
                    .width(195.dp)
                    .height(44.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorYellowGreen
                )
            ) {
                Text(
                    text = "Начать",
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    fontSize = 18.sp,
                    lineHeight = 24.sp,
                    color = colorDarkGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .width(80.dp)
                        .height(24.dp)

                )
            }
        }


    }
}

@Preview(showBackground = true, showSystemUi = true, name = "pre")
@Composable
fun SetUpScreenPreview(){
    val navController = rememberNavController()
    SetUpScreen(navController = navController)
}
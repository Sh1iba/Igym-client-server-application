package com.example.igym.screens.calculators

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.igym.R
import com.example.igym.network.model.enums.Gender
import com.example.igym.screens.CalculatorScreen
import com.example.igym.ui.theme.colorDarkGray
import com.example.igym.ui.theme.colorLightPurple
import com.example.igym.ui.theme.colorLightWhite
import com.example.igym.ui.theme.colorYellowGreen
import java.nio.file.WatchEvent


@Composable
fun BMICalculatorScreen(navController: NavController) {

    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf(Gender.MALE) }

    var bmiResult by remember { mutableStateOf<Double?>(null) }
    var bmiCategory by remember { mutableStateOf("") }

    fun calculateBMI() {
        try {
            val heightValue = height.toDouble() / 100
            val weightValue = weight.toDouble()
            val bmi = weightValue / (heightValue * heightValue)
            bmiResult = bmi

            // Разные категории для мужчин и женщин
            bmiCategory = when (selectedGender) {
                Gender.MALE -> when {
                    bmi < 18.5 -> "Недостаточная масса тела"
                    bmi < 25.0 -> "Нормальная масса тела"
                    bmi < 30.0 -> "Избыточная масса тела"
                    bmi < 35.0 -> "Ожирение I степени"
                    bmi < 40.0 -> "Ожирение II степени"
                    else -> "Ожирение III степени"
                }
                Gender.FEMALE -> when {
                    bmi < 18.0 -> "Недостаточная масса тела"
                    bmi < 24.0 -> "Нормальная масса тела"
                    bmi < 29.0 -> "Избыточная масса тела"
                    bmi < 34.0 -> "Ожирение I степени"
                    bmi < 39.0 -> "Ожирение II степени"
                    else -> "Ожирение III степени"
                }
            }

        } catch (e: Exception) {
            bmiResult = null
            bmiCategory = "Ошибка ввода данных"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorDarkGray)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .padding(top = 60.dp),
        ) {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_left_24),
                    contentDescription = "Назад",
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { navController.popBackStack() },
                    tint = colorYellowGreen
                )
                Text(
                    text = "Калькулятор индекса массы тела (ИМТ)",
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    fontSize = 20.sp,
                    color = colorLightPurple,
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Индекс массы тела (BMI) — формула для оценки соответствия массы и роста тела. Пол влияет на интерпретацию результата.",
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontSize = 16.sp,
                color = colorLightWhite,
            )
        }

        // Пол
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = "Пол:",
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontSize = 16.sp,
                color = colorLightPurple,
                modifier = Modifier.align(Alignment.CenterVertically)
                        .padding(start = 2.dp)
            )

            Gender.values().forEach { gender ->
                Row(
                    modifier = Modifier
                        .clickable { selectedGender = gender }
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedGender == gender,
                        onClick = { selectedGender = gender },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorYellowGreen,
                            unselectedColor = colorLightWhite
                        )
                    )
                    Text(
                        text = gender.displayName,
                        color = colorLightWhite,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        // Рост
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)
        ) {
            Text(
                text = "Рост (см)",
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontSize = 16.sp,
                color = colorLightPurple,
                modifier = Modifier.padding(start = 2.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(colorLightWhite),
                contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    value = height,
                    onValueChange = { height = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 16.sp,
                        color = colorDarkGray
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)
        ) {
            Text(
                text = "Вес (кг)",
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontSize = 16.sp,
                color = colorLightPurple,
                modifier = Modifier.padding(start = 2.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(colorLightWhite),
                contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 16.sp,
                        color = colorDarkGray
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true
                )
            }
        }


        Button(
            onClick = { calculateBMI() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorYellowGreen
            )
        ) {
            Text(
                text = "Рассчитать ИМТ",
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                fontSize = 16.sp,
                color = colorDarkGray
            )
        }

        // Результат
        if (bmiResult != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 35.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(colorLightPurple)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Ваш ИМТ: %.1f".format(bmiResult),
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    fontSize = 20.sp,
                    color = colorDarkGray
                )
                Text(
                    text = "Категория: $bmiCategory",
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    fontSize = 16.sp,
                    color = colorDarkGray
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true, name = "pre")
@Composable
fun BMICalculatorScreenPreview(){
    val navController = rememberNavController()
    BMICalculatorScreen(navController = navController)
}
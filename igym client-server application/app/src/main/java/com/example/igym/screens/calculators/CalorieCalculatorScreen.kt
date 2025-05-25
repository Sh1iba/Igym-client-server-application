package com.example.igym.screens.calculators

import androidx.compose.foundation.layout.wrapContentHeight
import com.example.igym.navigation.navigationRoutes
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import com.example.igym.ui.theme.colorDarkGray
import com.example.igym.ui.theme.colorLightPurple
import com.example.igym.ui.theme.colorLightWhite
import com.example.igym.ui.theme.colorYellowGreen
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.example.igym.network.model.enums.ActivityLevel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalorieCalculatorScreen(navController: NavController) {
    var age by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }


    var selectedGender by remember { mutableStateOf(Gender.MALE) }
    var selectedActivityLevel by remember { mutableStateOf(ActivityLevel.MODERATE) }
    var selectedFormula by remember { mutableStateOf(Formula.MIFFLIN) }


    var calculatedGender by remember { mutableStateOf(Gender.MALE) }
    var calculatedActivityLevel by remember { mutableStateOf(ActivityLevel.MODERATE) }
    var calculatedFormula by remember { mutableStateOf(Formula.MIFFLIN) }

    var resultInKcal by remember { mutableStateOf<Double?>(null) }
    var resultInKj by remember { mutableStateOf<Double?>(null) }

    fun calculateCalories() {
        try {
            val ageValue = age.toInt()
            val weightValue = weight.toDouble()
            val heightValue = height.toDouble()

            calculatedGender = selectedGender
            calculatedActivityLevel = selectedActivityLevel
            calculatedFormula = selectedFormula

            val bmr = when (calculatedFormula) {
                Formula.MIFFLIN -> calculateMifflin(calculatedGender, weightValue, heightValue, ageValue)
                Formula.HARRIS -> calculateHarris(calculatedGender, weightValue, heightValue, ageValue)
            }

            val calories = bmr * calculatedActivityLevel.multiplier
            resultInKcal = calories
            resultInKj = calories * 4.184
        } catch (e: Exception) {
            resultInKcal = null
            resultInKj = null
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
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
                    text = "Калькулятор калорий",
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
                text = "Рассчитайте вашу дневную норму калорий на основе выбранной формулы.",
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontSize = 16.sp,
                color = colorLightWhite,
            )
        }

        InputField(
            label = "Возраст (лет)",
            value = age,
            onValueChange = { age = it },
            keyboardType = KeyboardType.Number
        )

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

        InputField(
            label = "Вес (кг)",
            value = weight,
            onValueChange = { weight = it },
            keyboardType = KeyboardType.Number
        )

        InputField(
            label = "Рост (см)",
            value = height,
            onValueChange = { height = it },
            keyboardType = KeyboardType.Number
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)
        ) {
            Text(
                text = "Уровень активности:",
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
                    .background(colorLightWhite)
            ) {
                var expanded by remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = selectedActivityLevel.displayName,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorLightWhite,
                            unfocusedContainerColor = colorLightWhite,
                            disabledContainerColor = colorLightWhite,
                            focusedTextColor = colorDarkGray,
                            unfocusedTextColor = colorDarkGray,
                            cursorColor = colorDarkGray,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        ActivityLevel.values().forEach { level ->
                            DropdownMenuItem(
                                text = { Text(level.displayName) },
                                onClick = {
                                    selectedActivityLevel = level
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Формула расчета:",
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontSize = 16.sp,
                color = colorLightPurple,
                modifier = Modifier.padding(start = 2.dp)
            )

            Formula.values().forEach { formula ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedFormula = formula },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedFormula == formula,
                        onClick = { selectedFormula = formula },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorYellowGreen,
                            unselectedColor = colorLightWhite
                        )
                    )
                    Text(
                        text = formula.displayName,
                        color = colorLightWhite,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        Button(
            onClick = { calculateCalories() },
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
                text = "Рассчитать калории",
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                fontSize = 16.sp,
                color = colorDarkGray
            )
        }

        if (resultInKcal != null) {
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
                    text = "Ваша дневная норма:",
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    fontSize = 18.sp,
                    color = colorDarkGray
                )

                Text(
                    text = "%.0f ккал".format(resultInKcal),
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    fontSize = 24.sp,
                    color = colorDarkGray
                )

                Text(
                    text = "%.0f кДж".format(resultInKj),
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    fontSize = 18.sp,
                    color = colorDarkGray
                )

                Divider(color = colorDarkGray, thickness = 1.dp)

                Text(
                    text = "Детали расчета:\n" +
                            "- Формула: ${calculatedFormula.displayName}\n" +
                            "- Пол: ${calculatedGender.displayName}\n" +
                            "- Возраст: $age лет\n" +
                            "- Вес: $weight кг\n" +
                            "- Рост: $height см\n" +
                            "- Активность: ${calculatedActivityLevel.displayName}",
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    fontSize = 14.sp,
                    color = colorDarkGray
                )
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(color = colorDarkGray)
        )
    }
}


fun calculateMifflin(gender: Gender, weight: Double, height: Double, age: Int): Double {
    return when (gender) {
        Gender.MALE -> 10 * weight + 6.25 * height - 5 * age + 5
        Gender.FEMALE -> 10 * weight + 6.25 * height - 5 * age - 161
    }
}

fun calculateHarris(gender: Gender, weight: Double, height: Double, age: Int): Double {
    return when (gender) {
        Gender.MALE -> 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age)
        Gender.FEMALE -> 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age)
    }
}

enum class Formula(val displayName: String) {
    MIFFLIN("Миффлина-Сан Жеора"),
    HARRIS("Харриса-Бенедикта")
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CalorieCalculatorPreview() {
    val navController = rememberNavController()
    CalorieCalculatorScreen(navController = navController)
}

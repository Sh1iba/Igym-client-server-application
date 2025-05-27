package com.example.igym.screens.calculators

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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

@Composable
fun IdealWeightCalculatorScreen(navController: NavController) {
    var height by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var wristSize by remember { mutableStateOf("") }

    var selectedGender by remember { mutableStateOf(Gender.MALE) }

    var calculatedGender by remember { mutableStateOf(Gender.MALE) }
    var calculatedAge by remember { mutableStateOf("") }
    var calculatedWristSize by remember { mutableStateOf("") }
    var calculatedHeight by remember { mutableStateOf("") }

    var result by remember { mutableStateOf<Double?>(null) }

    fun calculateIdealWeight() {
        try {
            val heightValue = height.toDouble()
            val ageValue = age.toInt()
            val wristValue = wristSize.toDouble()


            calculatedGender = selectedGender
            calculatedAge = age
            calculatedWristSize = wristSize
            calculatedHeight = height

            val baseValue = when (selectedGender) {
                Gender.MALE -> (heightValue - 100) - ((heightValue - 150) / 4)
                Gender.FEMALE -> (heightValue - 100) - ((heightValue - 150) / 2.5)
            }

            val bodyType = when (selectedGender) {
                Gender.MALE -> when {
                    wristValue > 20 -> BodyType.HYPERSTHENIC
                    wristValue >= 17 -> BodyType.NORMOSTHENIC
                    else -> BodyType.ASTHENIC
                }
                Gender.FEMALE -> when {
                    wristValue > 18 -> BodyType.HYPERSTHENIC
                    wristValue >= 16 -> BodyType.NORMOSTHENIC
                    else -> BodyType.ASTHENIC
                }
            }

            val ageCoefficient = when {
                ageValue < 20 -> 1.0
                ageValue <= 30 -> 0.95
                ageValue <= 50 -> 1.0
                else -> 1.05
            }

            val bodyTypeCoefficient = when (bodyType) {
                BodyType.ASTHENIC -> 0.9
                BodyType.NORMOSTHENIC -> 1.0
                BodyType.HYPERSTHENIC -> 1.1
            }

            result = baseValue * ageCoefficient * bodyTypeCoefficient

        } catch (e: Exception) {
            result = null
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
                    text = "Калькулятор идеального веса",
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
                text = "Расчет по формуле Лоренца с учетом пола, возраста и типа телосложения.",
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontSize = 16.sp,
                color = colorLightWhite,
            )
        }

        InputField(
            label = "Рост (см)",
            value = height,
            onValueChange = { height = it },
            keyboardType = KeyboardType.Number
        )

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

        InputField(
            label = "Обхват запястья (см)",
            value = wristSize,
            onValueChange = { wristSize = it },
            keyboardType = KeyboardType.Number
        )

        Button(
            onClick = { calculateIdealWeight() },
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
                text = "Рассчитать идеальный вес",
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                fontSize = 16.sp,
                color = colorDarkGray
            )
        }

        if (result != null) {
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
                    text = "Ваш идеальный вес: %.1f кг".format(result),
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    fontSize = 20.sp,
                    color = colorDarkGray
                )

                Text(
                    text = "Рассчитано по формуле Лоренца:\n" +
                            "- Пол: ${calculatedGender.displayName}\n" +
                            "- Рост: ${calculatedHeight} см\n" +
                            "- Возраст: ${calculatedAge} лет\n" +
                            "- Обхват запястья: ${calculatedWristSize} см",
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

@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 35.dp)
    ) {
        Text(
            text = label,
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
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 16.sp,
                    color = colorDarkGray
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = keyboardType
                ),
                singleLine = true
            )
        }
    }
}

enum class BodyType(val s: String, val s1: String) {
    ASTHENIC("Астенический", "тонкокостный"), NORMOSTHENIC(
        "Астенический",
        "тонкокостный"
    ), HYPERSTHENIC("Астенический", "тонкокостный")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun IdealWeightCalculatorPreview() {
    val navController = rememberNavController()
    IdealWeightCalculatorScreen(navController = navController)
}
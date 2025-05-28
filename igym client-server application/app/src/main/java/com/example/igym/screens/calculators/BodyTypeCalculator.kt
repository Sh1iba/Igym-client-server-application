package com.example.igym.screens.calculators
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.igym.R
import com.example.igym.network.model.enums.Gender
import com.example.igym.ui.theme.colorDarkGray
import com.example.igym.ui.theme.colorLightPurple
import com.example.igym.ui.theme.colorLightWhite
import com.example.igym.ui.theme.colorYellowGreen
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyTypeCalculatorScreen(navController: NavController) {

    var wristSize by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf(Gender.MALE) }

    var bodyTypeResult by remember { mutableStateOf<BodyType?>(null) }
    var resultText by remember { mutableStateOf("") }

    fun calculateBodyType() {
        try {

            val normalizedInput = wristSize.trim().replace(',', '.')
            val wristValue = normalizedInput.toDouble()


            if (wristValue <= 0) {
                throw IllegalArgumentException("Значение должно быть больше нуля")
            }

            val type = when (selectedGender) {
                Gender.MALE -> when {
                    wristValue < 17 -> BodyType.ASTHENIC
                    wristValue <= 20 -> BodyType.NORMOSTHENIC
                    else -> BodyType.HYPERSTHENIC
                }
                Gender.FEMALE -> when {
                    wristValue < 16 -> BodyType.ASTHENIC
                    wristValue <= 18 -> BodyType.NORMOSTHENIC
                    else -> BodyType.HYPERSTHENIC
                }
            }

            bodyTypeResult = type

            resultText = when (selectedGender) {
                Gender.MALE -> when (type) {
                    BodyType.ASTHENIC -> "Менее 17 см"
                    BodyType.NORMOSTHENIC -> "17–20 см"
                    BodyType.HYPERSTHENIC -> "Более 20 см"
                }
                Gender.FEMALE -> when (type) {
                    BodyType.ASTHENIC -> "Менее 16 см"
                    BodyType.NORMOSTHENIC -> "16–18 см"
                    BodyType.HYPERSTHENIC -> "Более 18 см"
                }
            }

        } catch (e: Exception) {
            bodyTypeResult = null
            resultText = "Ошибка ввода данных"
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
                .padding(top = 60.dp)
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
                    text = "Определение типа телосложения",
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    fontSize = 20.sp,
                    color = colorLightPurple,
                    modifier = Modifier
                        .padding(top = 5.dp)
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
                text = "Определите ваш тип телосложения по обхвату запястья. Пол влияет на интерпретацию результата.",
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontSize = 16.sp,
                color = colorLightWhite,
            )
        }


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

        // Wrist size input
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)
        ) {
            Text(
                text = "Обхват запястья (см)",
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontSize = 16.sp,
                color = colorLightPurple
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
                    value = wristSize,
                    onValueChange = { wristSize = it },
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

        // Убрали блок выбора единиц измерения полностью!

        // Button
        Button(
            onClick = { calculateBodyType() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorYellowGreen)
        ) {
            Text(
                text = "Определить тип",
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                fontSize = 16.sp,
                color = colorDarkGray
            )
        }

        // Result
        bodyTypeResult?.let { type ->
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
                    text = "Тип телосложения:",
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    fontSize = 18.sp,
                    color = colorDarkGray
                )
                Text(
                    text = "${type.s} (${type.s1})",
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    fontSize = 20.sp,
                    color = colorDarkGray
                )
                Divider(color = colorDarkGray, thickness = 1.dp)
                Text(
                    text = "Обхват: $wristSize см",
                    color = colorDarkGray
                )
                Text(
                    text = "Интерпретация: $resultText",
                    color = colorDarkGray
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}






@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BodyTypeCalculatorPreview() {
    val navController = rememberNavController()
    BodyTypeCalculatorScreen(navController)
}

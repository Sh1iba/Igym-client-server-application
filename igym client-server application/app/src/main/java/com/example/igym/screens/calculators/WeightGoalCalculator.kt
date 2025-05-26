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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

@Composable
fun WeightGoalCalculatorScreen(navController: NavController) {
    var age by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var fatPercent by remember { mutableStateOf("") }
    var goalWeightChange by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }

    var selectedGender by remember { mutableStateOf(Gender.MALE) }
    var selectedActivityLevel by remember { mutableStateOf(PhysicalActivityLevel.MODERATE) }
    var goalType by remember { mutableStateOf(GoalType.LOSE) }
    var selectedTimeUnit by remember { mutableStateOf(TimeUnit.MONTHS) }

    var maintenanceCalories by remember { mutableStateOf<Double?>(null) }
    var targetCalories by remember { mutableStateOf<Double?>(null) }

    fun calculate() {
        try {
            val w = weight.toDouble()
            val h = height.toDouble()
            val a = age.toInt()
            val goalKg = goalWeightChange.toDouble()
            val dur = duration.toInt()

            val bmr = calculateMifflin(selectedGender, w, h, a)
            val maintenance = bmr * selectedActivityLevel.multiplier
            maintenanceCalories = maintenance

            val totalDelta = 7700 * goalKg
            val days = selectedTimeUnit.toDays(dur)
            val deltaPerDay = totalDelta / days

            targetCalories = when (goalType) {
                GoalType.LOSE -> maintenance - deltaPerDay
                GoalType.GAIN -> maintenance + deltaPerDay
            }

        } catch (e: Exception) {
            maintenanceCalories = null
            targetCalories = null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorDarkGray)
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
                    text = "Калькулятор набора/сброса веса",
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    fontSize = 20.sp,
                    color = colorLightPurple,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }
        }

        // Ввод параметров
        InputField("Возраст (лет)", age, { age = it }, KeyboardType.Number)
        InputField("Вес (кг)", weight, { weight = it }, KeyboardType.Number)
        InputField("Рост (см)", height, { height = it }, KeyboardType.Number)
        InputField("Процент жира (необязательно)", fatPercent, { fatPercent = it }, KeyboardType.Number)

        // Пол
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Пол:",
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontSize = 16.sp,
                color = colorLightPurple,
                modifier = Modifier.align(Alignment.CenterVertically)
                    .padding(start = 2.dp)
            )
            Gender.values().forEach {
                Row(
                    modifier = Modifier
                        .clickable { selectedGender = it }
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedGender == it,
                        onClick = { selectedGender = it },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorYellowGreen,
                            unselectedColor = colorLightWhite
                        )
                    )
                    Text(it.displayName, color = colorLightWhite)
                }
            }
        }

        DropdownSelector(
            label = "Физическая активность",
            selectedOption = selectedActivityLevel,
            options = PhysicalActivityLevel.values().toList(),
            onOptionSelected = { selectedActivityLevel = it }
        )


        Row(
        ) {
            // DropdownSelector — Цель
            Column(modifier = Modifier.weight(1f)) {
                DropdownSelector(
                    label = "Цель",
                    selectedOption = goalType,
                    options = GoalType.values().toList(),
                    onOptionSelected = { goalType = it }
                )
            }

            // InputField — на сколько кг
            Column(modifier = Modifier.weight(1f)) {
                InputField(
                    label = "На сколько кг",
                    value = goalWeightChange,
                    onValueChange = { goalWeightChange = it },
                    keyboardType = KeyboardType.Number
                )
            }
        }


        Row(
        ) {
            Column(modifier = Modifier.weight(1f)) {
                InputField(
                    label = "Срок",
                    value = duration,
                    onValueChange = { duration = it },
                    keyboardType = KeyboardType.Number
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                DropdownSelector(
                    label = "Единицы",
                    selectedOption = selectedTimeUnit,
                    options = TimeUnit.values().toList(),
                    onOptionSelected = { selectedTimeUnit = it }
                )
            }
        }



        Button(
            onClick = { calculate() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorYellowGreen)
        ) {
            Text(
                text = "Рассчитать",
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                fontSize = 16.sp,
                color = colorDarkGray
            )
        }


        if (maintenanceCalories != null && targetCalories != null) {
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
                    text = "Результаты:",
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    fontSize = 18.sp,
                    color = colorDarkGray
                )

                Text(
                    text = "Количество калорий для сохранения текущего веса: ${maintenanceCalories!!.toInt()} ккал/день",
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    fontSize = 16.sp,
                    color = colorDarkGray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = when (goalType) {
                        GoalType.LOSE -> "Для похудения на ${goalWeightChange} кг за $duration ${selectedTimeUnit.displayName} вам нужно сократить потребление калорий до:"
                        GoalType.GAIN -> "Для набора ${goalWeightChange} кг за $duration ${selectedTimeUnit.displayName} вам нужно увеличить потребление калорий до:"
                    },
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    fontSize = 16.sp,
                    color = colorDarkGray
                )

                Text(
                    text = "${targetCalories!!.toInt()} ккал/день",
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    fontSize = 22.sp,
                    color = colorDarkGray
                )
            }
        }


        Spacer(modifier = Modifier.height(20.dp))
    }
}


enum class GoalType(override val displayName: String) : DisplayableOption {
    LOSE("Похудеть"),
    GAIN("Набрать")
}



enum class TimeUnit(
    override val displayName: String,
    val toDaysMultiplier: Int
) : DisplayableOption {
    DAYS("дней", 1),
    WEEKS("недель", 7),
    MONTHS("месяцев", 30),
    YEARS("лет", 365);

    fun toDays(value: Int): Int = value * toDaysMultiplier
}


enum class PhysicalActivityLevel(
    override val displayName: String,
    val multiplier: Double
) : DisplayableOption {
    MINIMAL("Минимум физ. нагрузки", 1.2),
    LIGHT("Легкая нагрузка", 1.375),
    MODERATE("Умеренный уровень нагрузок", 1.55),
    INTENSE("Интенсивные нагрузки", 1.725),
    EXTREME("Экстремальные нагрузки", 1.9)
}


interface DisplayableOption {
    val displayName: String
}


@Composable
fun <T> DropdownSelector(
    label: String,
    selectedOption: T?,
    options: List<T>,
    onOptionSelected: (T) -> Unit,
    displayPlaceholder: String = "Выберите значение"
) where T : DisplayableOption {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 35.dp)) {

        Text(
            text = label,
            fontFamily = FontFamily(Font(R.font.poppins_medium)),
            fontSize = 16.sp,
            color = colorLightPurple,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colorLightWhite)
        ) {
            Text(
                text = selectedOption?.displayName ?: displayPlaceholder,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .align(Alignment.CenterStart)
                    .clickable { expanded = true },
                color = if (selectedOption == null) Color.Gray else colorDarkGray,
                fontFamily = FontFamily(Font(R.font.poppins_regular))
            )

            Icon(
                painter = painterResource(R.drawable.arrow_down),
                contentDescription = "$label dropdown",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp)
                    .clickable { expanded = true },
                tint = colorDarkGray
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorLightWhite)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option.displayName,
                                fontFamily = FontFamily(Font(R.font.poppins_regular))
                            )
                        },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WeightGoalCalculatorPreview() {
    val navController = rememberNavController()
    WeightGoalCalculatorScreen(navController = navController)
}

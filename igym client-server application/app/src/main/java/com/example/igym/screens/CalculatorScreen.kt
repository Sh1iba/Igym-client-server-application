package com.example.igym.screens

import androidx.compose.foundation.layout.wrapContentHeight
import com.example.igym.navigation.navigationRoutes
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.igym.R
import com.example.igym.ui.theme.colorDarkGray
import com.example.igym.ui.theme.colorLightPurple
import com.example.igym.ui.theme.colorYellowGreen

@Composable
fun CalculatorScreen(navController: NavController) {
    val calculators = listOf(
        CalculatorItem(
            title = "Калькулятор ИМТ",
            description = "Расчет индекса массы тела",
            imageRes = R.drawable.imt,
            onClick = { navController.navigate(navigationRoutes.BMI_CALCULATOR) }
        ),
        CalculatorItem(
            title = "Калькулятор идеального веса",
            description = "Определите ваш идеальный вес",
            imageRes = R.drawable.perfect_weight,
            onClick = {navController.navigate(navigationRoutes.IDEAL_WEIGHT) }
        ),
        CalculatorItem(
            title = "Калькулятор калорий",
            description = "Суточная норма калорий",
            imageRes = R.drawable.food,
            onClick = {navController.navigate(navigationRoutes.DAILY_CALORIES) }
        ),
        CalculatorItem(
            title = "Калькулятор набора/сброса веса",
            description = "Посчитай каллории",
            imageRes = R.drawable.gain_loss,
            onClick = {navController.navigate(navigationRoutes.WEIGHT_GAIN_LOSS) }
        ),


    )

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
                .wrapContentHeight()
                .padding(start = 30.dp, top = 30.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Калькуляторы",
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                fontSize = 30.sp,
                color = colorYellowGreen,
            )
        }

        calculators.forEach { calculator ->
            CalculatorBoxItem(
                title = calculator.title,
                description = calculator.description,
                imageRes = calculator.imageRes,
                onClick = calculator.onClick
            )
        }
    }
}

data class CalculatorItem(
    val title: String,
    val description: String,
    val imageRes: Int,
    val onClick: () -> Unit
)

@Composable
fun CalculatorBoxItem(
    title: String,
    description: String,
    imageRes: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(color = colorLightPurple)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp),

            ) {
            Column(
                modifier = Modifier.weight(1f)
                    .padding(vertical = 10.dp)
            ) {
                Text(
                    text = title,
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    color = colorDarkGray,
                    fontSize = 20.sp,
                    lineHeight = 24.sp,

                    )

                Text(
                    text = description,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    color = colorDarkGray,
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                )
            }
            Column (
                modifier = Modifier
                    .wrapContentSize()

            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    alignment = Alignment.CenterEnd,
                    modifier = Modifier
                        .clip(RoundedCornerShape(25.dp))

                )
            }  }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CalculatorScreenPreview() {
    val navController = rememberNavController()
    CalculatorScreen(navController = navController)
}
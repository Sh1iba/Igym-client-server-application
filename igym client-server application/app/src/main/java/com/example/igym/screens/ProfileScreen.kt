package com.example.igym.screens

import android.view.MenuItem
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.igym.R
import com.example.igym.manager.AuthManager
import com.example.igym.navigation.navigationRoutes
import com.example.igym.ui.theme.colorDarkGray
import com.example.igym.ui.theme.colorDarkPurple
import com.example.igym.ui.theme.colorLightPurple
import com.example.igym.ui.theme.colorLightWhite
import com.example.igym.ui.theme.colorYellowGreen
import kotlin.reflect.jvm.internal.impl.load.kotlin.DeserializationComponentsForJava.Companion.ModuleData


@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val authManager = remember { AuthManager(context) }

    val fullName = authManager.getFullName() ?: "Unknown"
    val email = authManager.getUserEmail() ?: "No email"
    val birthDate = authManager.getBirthDate() ?: "Not set"
    val weight = authManager.getWeight() ?: "0"
    val age = authManager.getAge() ?: "0 лет"
    val height = authManager.getHeight() ?: "100 см"

    val showLogoutDialog = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorDarkGray)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorLightPurple)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 15.dp)
                                .padding(top = 30.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "Мой Профиль",
                                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                                fontSize = 20.sp,
                                color = colorLightWhite,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }


                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(bottom = 40.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.user),
                            contentDescription = "Profile image",
                            modifier = Modifier
                                .size(140.dp)
                                .padding(2.dp)
                        )
                        Text(
                            text = fullName,
                            fontFamily = FontFamily(Font(R.font.poppins_bold)),
                            fontSize = 20.sp,
                            color = colorLightWhite,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                        Text(
                            text = email,
                            fontFamily = FontFamily(Font(R.font.poppins_medium)),
                            fontSize = 13.sp,
                            color = colorLightWhite,

                        )
                        Text(
                            text = "Дата рождения: $birthDate",
                            fontFamily = FontFamily(Font(R.font.poppins_medium)),
                            fontSize = 16.sp,
                            color = colorLightWhite,

                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 35.dp)
                    .offset(y = (-20).dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(colorDarkPurple)
                        .padding(vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Вес
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "$weight Кг",
                                color = colorLightWhite,
                                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Вес",
                                color = colorLightWhite,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                        }

                        Divider(
                            color = colorLightWhite,
                            modifier = Modifier
                                .width(1.dp)
                                .height(30.dp)
                        )

                        // Возраст
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "$age",
                                color = colorLightWhite,
                                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Лет",
                                color = colorLightWhite,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                        }

                        Divider(
                            color = colorLightWhite,
                            modifier = Modifier
                                .width(1.dp)
                                .height(30.dp)
                        )

                        // Рост
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "$height См",
                                color = colorLightWhite,
                                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Рост",
                                color = colorLightWhite,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(colorDarkGray),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val menuItems = listOf(
                    MenuItem("Profile", Icons.Default.Person, "profile"),
                    MenuItem("Logout", Icons.Default.ExitToApp, "sign_in")
                )

                menuItems.forEach { item ->
                    val onClick = {
                        if (item.title == "Logout") {
                            showLogoutDialog.value = true
                        } else {
                            navController.navigate(item.route)
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clickable(onClick = onClick)
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                modifier = Modifier.size(35.dp),
                                tint = colorLightWhite
                            )
                            Text(
                                text = item.title,
                                color = colorLightWhite,
                                modifier = Modifier.padding(start = 16.dp),
                                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                                fontSize = 16.sp
                            )
                        }

                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_right_24),
                            contentDescription = "Arrow",
                            modifier = Modifier
                                .size(30.dp)
                                .clickable(onClick = onClick),
                            tint = colorYellowGreen
                        )
                    }

                    Divider(
                        color = colorLightWhite.copy(alpha = 0.2f),
                        modifier = Modifier.padding(horizontal = 16.dp),
                        thickness = 0.5.dp
                    )
                }
            }
        }

        // Диалог подтверждения выхода
        if (showLogoutDialog.value) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog.value = false },
                title = {
                    Text(
                        text = "Подтверждение выхода",
                        fontFamily = FontFamily(Font(R.font.poppins_bold)),
                        fontSize = 18.sp,
                        color = colorLightPurple
                    )
                },
                text = {
                    Text(
                        text = "Вы действительно хотите выйти из аккаунта?",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        color = colorLightWhite,
                        fontSize = 16.sp
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            authManager.logout()
                            navController.navigate("sign_in") {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }
                            showLogoutDialog.value = false
                        },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = colorYellowGreen
                        )
                    ) {
                        Text(
                            text = "Да",
                            fontFamily = FontFamily(Font(R.font.poppins_bold))
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showLogoutDialog.value = false },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = colorLightWhite
                        )
                    ) {
                        Text(
                            text = "Нет",
                            fontFamily = FontFamily(Font(R.font.poppins_medium))
                        )
                    }
                },
                containerColor = colorDarkGray,
                shape = RoundedCornerShape(12.dp)
            )
        }
    }
}




data class MenuItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)
@Preview(showBackground = true, showSystemUi = true, name = "pre")
@Composable
fun ProfileScreenPreview() {
    val navController = rememberNavController()
    ProfileScreen(navController = navController)

}
package com.project.daynergy.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.daynergy.R
import com.project.daynergy.core.navigation.Screen

@Composable
fun BottomNav(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(Color(0xFFEDE9FF))
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_home),
                contentDescription = null,
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        navController.navigate(Screen.Home.route)
                    }
            )

            Image(
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = null,
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        navController.navigate(Screen.TodayTasks.route)
                    }
            )

            Spacer(Modifier.width(48.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_quotes),
                contentDescription = null,
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        navController.navigate(Screen.Quotes.route)
                    }
            )

            Image(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
        }


        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-24).dp)
                .size(56.dp)
                .background(Color(0xFF5B3EE4), CircleShape)
                .clickable {
                    navController.navigate(Screen.AddTask.route)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Add, null, tint = Color.White)
        }
    }
}

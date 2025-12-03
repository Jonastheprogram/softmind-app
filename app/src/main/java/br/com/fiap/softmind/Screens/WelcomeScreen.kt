package br.com.fiap.softmind.Screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import br.com.fiap.softmind.R





@Composable
fun WelcomeScreen(navController: NavController) {

    val visible = remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        visible.value = true
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1F4E79),
                        Color(0xFFB5EAD7)
                    )
                )
            )
    )
    {
        AnimatedVisibility(
            visible = visible.value,
            enter = fadeIn(animationSpec = tween(1200)) // tempo da animação
        ) {
            androidx.compose.foundation.Image(
                painter = painterResource(id = R.drawable.softteklogo),
                contentDescription = "Logo Softtek",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(9.dp)
                    .size(70.dp)
            )
        }


        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            AnimatedVisibility(
                visible = visible.value,
                enter = fadeIn(animationSpec = tween(1200)) //
            ) {
                Text(
                    text = stringResource(id = R.string.soft_hometitle),
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    fontSize = 35.sp,
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
            AnimatedVisibility(
                visible = visible.value,
                enter = fadeIn(animationSpec = tween(1200))
            ) {
                Text(
                    text = stringResource(id = R.string.soft_homewelcome),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontSize = 33.sp,
                )
            }

            Spacer(modifier = Modifier.height(80.dp))
            AnimatedVisibility(
                visible = visible.value,
                enter = fadeIn(animationSpec = tween(1200))
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.soft_homedesc),
                    fontSize = 22.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(180.dp))
            AnimatedVisibility(
                visible = visible.value,
                enter = fadeIn(animationSpec = tween(1200))
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4479A9)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.size(width = 280.dp, height = 60.dp),
                    onClick = { navController.navigate("feelings") }
                ) {
                    Text(
                        text = stringResource(id = R.string.soft_homecomecar),
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }

        }
    }
}


//@Preview(showSystemUi = true,showBackground = true)
//@Composable
//fun WelcomeScreenPreview() {
//     val navController = rememberNavController() // cria um navController de teste
//        WelcomeScreen(navController = navController) }




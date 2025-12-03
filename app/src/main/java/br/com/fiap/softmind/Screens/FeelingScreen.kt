package br.com.fiap.softmind.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.softmind.ui.components.GradientBackground
import br.com.fiap.softmind.ui.theme.AppBlueDark
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.softmind.R
import br.com.fiap.softmind.ui.ViewModel.FeelingViewModel

@Composable
fun FeelingScreen(navController: NavController, viewModel: FeelingViewModel) {

    val sentimentos = listOf(
        "Motivado" to Icons.Outlined.SentimentVerySatisfied,
        "Animado" to Icons.Outlined.SentimentSatisfied,
        "Satisfeito" to Icons.Outlined.SentimentNeutral,
        "Preocupado" to Icons.Outlined.SentimentDissatisfied,
        "Estressado" to Icons.Outlined.MoodBad,
        "Cansado" to Icons.Outlined.SentimentVeryDissatisfied
    )
    var selectedSentimento by remember { mutableStateOf<String?>(null) }

    GradientBackground {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            androidx.compose.foundation.Image(
                painter = painterResource(id = R.drawable.softteklogo),
                contentDescription = "Logo Softtek",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(9.dp)
                    .size(70.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Spacer(modifier = Modifier.height(110.dp))

                Text(
                    text = "Como você se sente hoje?",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(32.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    sentimentos.forEach { (texto, icone) ->
                        SentimentoOption(
                            text = texto,
                            icon = icone,
                            isSelected = texto == selectedSentimento,
                            onSelected = { selectedSentimento = texto }
                        )
                    }
                }
            }
            FloatingActionButton(
                onClick = {
                    selectedSentimento?.let { sentimento -> viewModel.registrarSentimento(sentimento){navController.navigate("riskassessment")}
                    navController.navigate("riskassessment") }},
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 40.dp),
                shape = CircleShape,
                containerColor = Color.White,
                contentColor = AppBlueDark
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Continuar")
            }
        }
    }
}

@Composable
fun SentimentoOption(text: String, icon: ImageVector, isSelected: Boolean, onSelected: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable(onClick = onSelected)
            .background(
                color = if (isSelected) Color.White.copy(alpha = 0.2f) else Color.Transparent,
                shape = MaterialTheme.shapes.medium
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = Color.White,
            modifier = Modifier.size(36.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, color = Color.White, fontSize = 20.sp)
    }
}

//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun FeelingScreenPreview() {
//    val navController = rememberNavController()
//    FeelingScreen(navController = navController, viewModel())
//}

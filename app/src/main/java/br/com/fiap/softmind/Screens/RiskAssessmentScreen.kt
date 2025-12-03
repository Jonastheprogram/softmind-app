package br.com.fiap.softmind.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.softmind.ui.ViewModel.FeelingViewModel
import br.com.fiap.softmind.ui.components.GradientBackground
import br.com.fiap.softmind.ui.theme.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import br.com.fiap.softmind.R
import br.com.fiap.softmind.model.Humor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RiskAssessmentScreen( navController: NavController, viewModel: FeelingViewModel) {

    val humores by viewModel.humores.collectAsState()

    GradientBackground {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Box() {
                            androidx.compose.foundation.Image(
                                painter = painterResource(id = R.drawable.softteklogo),
                                contentDescription = "Logo Softtek",
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(9.dp)
                                    .size(70.dp)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            },
            bottomBar = { AppBottomBar(navController) },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate("feelings")},
                    shape = CircleShape,
                    containerColor = AppBlueDark,
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Adicionar")
                }
            },
            floatingActionButtonPosition = FabPosition.Center
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(humores) { humorItem -> RegistroHumorCard(humor = humorItem, viewModel = viewModel)


                }
            }
        }
    }
}

@Composable
fun RegistroHumorCard(humor: Humor, viewModel: FeelingViewModel) {

    val (dataFormatada, horaFormatada) = formatarDataHora(humor.dataCheckin)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppBlueDark),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = dataFormatada,
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        getIconForSentimento(humor.sentimento),

                        contentDescription = humor.sentimento,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = humor.sentimento,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = horaFormatada, color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = {
                    humor.id?.let { idParaDeletar ->
                        viewModel.deletarHumor(idParaDeletar)
                    }
                }) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Deletar Humor",
                        tint = Color.White.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

@Composable
fun AppBottomBar(navController: NavController) {
    BottomAppBar(
        containerColor = GradientStartGreen,
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomBarItem(
                    label = "Registros",
                    icon = Icons.Default.Edit,
                    onClick = { navController.navigate("riskassessment") }
                )
                BottomBarItem(
                    label = "Estatísticas",
                    icon = Icons.Default.BarChart,
                    onClick = { navController.navigate("statisticmood") }
                )

                BottomBarItem(
                    label = "Checkin",
                    icon = Icons.Default.Check,
                    onClick = {  navController.navigate("moodhistory") }
                )
                BottomBarItem(
                    label = "Apoio",
                    icon = Icons.Default.Support,
                    onClick = { navController.navigate("support") }
                )
            }
        }
    )
}

@Composable
fun BottomBarItem(label: String, icon: ImageVector, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Icon(icon, contentDescription = label, tint = TextGray)
        Text(label, fontSize = 12.sp, color = TextGray)
    }
}

@Composable
fun getIconForSentimento(sentimento: String?): ImageVector {
    return when (sentimento?.lowercase()) {
        "motivado" -> Icons.Outlined.SentimentVerySatisfied
        "animado" -> Icons.Outlined.SentimentSatisfied
        "satisfeito" -> Icons.Outlined.SentimentNeutral
        "preocupado" -> Icons.Outlined.SentimentDissatisfied
        "estressado" -> Icons.Outlined.MoodBad
        "cansado" -> Icons.Outlined.SentimentVeryDissatisfied
        else -> Icons.Filled.SentimentNeutral
    }
}

fun formatarDataHora(dataString: String?): Pair<String, String> {
    if (dataString == null) {
        return Pair("Data indisponível", "")
    }
    return try {
        val parsedDate = LocalDateTime.parse(dataString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        val dateFormatter = DateTimeFormatter.ofPattern("dd 'de' MMM", Locale("pt", "BR"))

        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

        Pair(parsedDate.format(dateFormatter), parsedDate.format(timeFormatter))
    } catch (e: Exception) {

        Pair("Data inválida", "")
    }
}

//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun RiskAssessmentScreenPreview() {
//    val navController = rememberNavController()
//    RiskAssessmentScreen(navController, viewModel())
//}
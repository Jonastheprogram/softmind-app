package br.com.fiap.softmind.Screens


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.softmind.ui.ViewModel.StatisticViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.softmind.model.RegistroDiarioHumor
import br.com.fiap.softmind.ui.components.GradientBackground
import br.com.fiap.softmind.ui.theme.TextGray
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticMoodScreen(navController: NavController) {

    val viewModel: StatisticViewModel = viewModel()
    val humorStats by viewModel.humorStats.collectAsState()
    val registros by viewModel.registrosDiarios.collectAsState()
    val chartEntryModelProducer = remember { ChartEntryModelProducer() }

    LaunchedEffect(humorStats) {
        if (humorStats.isNotEmpty()) {
            val chartEntries = humorStats.mapIndexed { index, stat -> entryOf(index, stat.count) }
            chartEntryModelProducer.setEntries(chartEntries)
        }
    }

    val bottomAxisValueFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, _ ->
        humorStats.getOrNull(value.toInt())?.sentimento ?: ""
    }

    GradientBackground {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = { TopAppBar( title = { Text("Estatísticas", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            ) },
            bottomBar = { AppBottomBar(navController = navController)}
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)

            ) {
                item {Text(
                    "Frequência de Humores Registrados",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp))}


                item{
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        if (humorStats.isNotEmpty()) {
                            Chart(
                                chart = columnChart(),
                                chartModelProducer = chartEntryModelProducer,
                                startAxis = rememberStartAxis(),
                                bottomAxis = rememberBottomAxis(valueFormatter = bottomAxisValueFormatter),
                                modifier = Modifier.padding(16.dp)
                            )
                        } else {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "Sem dados para exibir o gráfico.",
                                    fontSize = 16.sp,
                                    color = TextGray.copy(alpha = 0.7f),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }

                item {
                    Text(
                        "Seus Desabafos e Registros",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                items(registros) { registro ->
                    RegistroDiarioCard(registro = registro, viewModel = viewModel)
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }

            }
        }
    }

@Composable
fun RegistroDiarioCard(registro: RegistroDiarioHumor, viewModel: StatisticViewModel) {
    val (dataFormatada, _) = formatarDataHora(registro.dataRegistro)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = dataFormatada,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black.copy(alpha = 0.8f)
                )
                IconButton(onClick = { registro.id?.let { viewModel.deletarRegistroDiario(it) } }) {
                    Icon(Icons.Default.Delete, contentDescription = "Deletar Registro", tint = TextGray)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            if (registro.desabafo?.isNotBlank() == true) {
                Text(
                    text = registro.desabafo,
                    color = Color.Black.copy(alpha = 0.7f)
                )
            } else {
                Text(
                    text = "(Sem desabafo neste dia)",
                    color = TextGray,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            }
        }
    }
}


//@Preview(showSystemUi = true,showBackground = true)
//@Composable
//fun StatisticMoodScreenPreview() {
//     val navController = rememberNavController()
//        StatisticMoodScreen(navController = navController) }
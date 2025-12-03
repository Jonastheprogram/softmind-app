package br.com.fiap.softmind.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.softmind.ui.ViewModel.RegistroViewModel
import br.com.fiap.softmind.ui.ViewModel.SubmissionState
import br.com.fiap.softmind.ui.components.GradientBackground
import br.com.fiap.softmind.ui.theme.AppBlueDark

@Composable
fun MoodHistoryScreen(navController: NavController) {

    var cargaTarefas by remember { mutableStateOf("") }
    var apoioEquipe by remember { mutableStateOf("") }
    var desabafo by remember { mutableStateOf("") }
    val viewModel: RegistroViewModel = viewModel()
    val submissionState by viewModel.submissionState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
//    val scope = rememberCoroutineScope()

    LaunchedEffect(submissionState) {
        if (submissionState is SubmissionState.Success) {


            snackbarHostState.showSnackbar("Seu registro foi enviado com sucesso!")


            viewModel.resetState()
            navController.navigate("riskassessment") {
                popUpTo("riskassessment") { inclusive = false }
            }
        }
    }

    GradientBackground {

        Scaffold(
            containerColor = Color.Transparent,
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 32.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    item {
                        QuestaoCargaTarefas(
                            currentSelection = cargaTarefas,
                            onOptionSelected = { cargaTarefas = it })
                        Spacer(modifier = Modifier.height(32.dp))
                        QuestaoApoio(
                            currentSelection = apoioEquipe,
                            onOptionSelected = { apoioEquipe = it })
                        Spacer(modifier = Modifier.height(32.dp))
                        QuestaoDesabafo(
                            text = desabafo,
                            onTextChange = { desabafo = it })
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }

                FloatingActionButton(
                    onClick = {
                        viewModel.salvarRegistro(
                            carga = cargaTarefas,
                            apoio = apoioEquipe,
                            desabafo = desabafo
                        )
                    },
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
}

@Composable
fun QuestaoCargaTarefas(currentSelection: String,
                        onOptionSelected: (String) -> Unit) {
    val options = listOf("Leve", "Moderada", "Estressante", "Exigente")


    Column {
        Text(
            "Como está seu nível de carga de tarefas hoje?",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        options.forEach { text ->
            RadioOption(
                text = text,
                selected = (text == currentSelection),
                onClick = { onOptionSelected (text) }
            )
        }
    }
}

@Composable
fun QuestaoApoio(
    currentSelection: String,
    onOptionSelected: (String) -> Unit
) {
    val options = listOf("sim, com certeza", "mais ou menos", "não tive esse apoio")


    Column {
        Text(
            "Você se sentiu ouvido ou apoiado por alguém da equipe hoje?",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        options.forEach { text ->
            RadioOption(
                text = text,
                // USE OS PARÂMETROS DA FUNÇÃO
                selected = (text == currentSelection),
                onClick = { onOptionSelected(text) }
            )
        }
    }
}

@Composable
fun QuestaoDesabafo(text: String,
                    onTextChange: (String) -> Unit) {
    Column {
        Text(
            "Quer desabafar sobre algo do seu dia?",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White.copy(alpha = 0.3f),
                unfocusedContainerColor = Color.White.copy(alpha = 0.2f),
                disabledContainerColor = Color.White.copy(alpha = 0.2f),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}


@Composable
fun RadioOption(text: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                onClick = onClick
            )
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color.White,
                unselectedColor = Color.White.copy(alpha = 0.7f)
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = Color.White)
    }
}

//@Preview(showSystemUi = true,showBackground = true)
//@Composable
//fun Tela5Preview() {
//    val navController = rememberNavController()
//    MoodHistoryScreen(navController = navController) }

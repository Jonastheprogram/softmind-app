package br.com.fiap.softmind.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.softmind.ui.components.GradientBackground
import br.com.fiap.softmind.ui.theme.AppBlueLight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.softmind.model.Lembrete
import br.com.fiap.softmind.ui.ViewModel.LembreteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportScreen(navController: NavController) {

    val viewModel: LembreteViewModel = viewModel()
    val lembretes by viewModel.lembretes.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AdicionarLembreteDialog(
            onDismiss = { showDialog = false },
            onConfirm = { hora, descricao ->
                viewModel.criarLembrete(hora, descricao)
                showDialog = false
            }
        )
    }

    GradientBackground {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = { TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                title = { Text("Apoio e Bem-estar", fontWeight = FontWeight.Bold) },
            )},
            bottomBar = { AppBottomBar(navController = navController) }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item {
                    SecaoLembretes(
                        lembretes = lembretes,
                        viewModel = viewModel,
                        onAddClick = { showDialog = true }
                    )
                }
                item { SecaoDicas() }
                item { SecaoRecursos(navController) }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

@Composable
fun SecaoLembretes(
    lembretes: List<Lembrete>,
    viewModel: LembreteViewModel,
    onAddClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Meus lembretes", fontWeight = FontWeight.Bold, fontSize = 18.sp)

        lembretes.forEach { lembrete ->
            LembreteItem(lembrete = lembrete, viewModel = viewModel)
        }

        Button(
            onClick = onAddClick,
            colors = ButtonDefaults.buttonColors(containerColor = AppBlueLight),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Adicionar Lembrete")
        }
    }
}

@Composable
fun LembreteItem(lembrete: Lembrete, viewModel: LembreteViewModel) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(lembrete.hora, fontWeight = FontWeight.Bold)
            Spacer(Modifier.width(16.dp))
            Text(lembrete.descricao)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {

            IconButton(onClick = { lembrete.id?.let { viewModel.deletarLembrete(it) } }) {
                Icon(Icons.Default.Delete, contentDescription = "Deletar", tint = Color.Red.copy(alpha = 0.7f))
            }
        }
    }
}


@Composable
fun AdicionarLembreteDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var hora by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Novo Lembrete", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                OutlinedTextField(
                    value = hora,
                    onValueChange = { hora = it },
                    label = { Text("Horário (ex: 20:00)") }
                )
                OutlinedTextField(
                    value = descricao,
                    onValueChange = { descricao = it },
                    label = { Text("Descrição") }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) { Text("Cancelar") }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { onConfirm(hora, descricao) }) { Text("Salvar") }
                }
            }
        }
    }
}


@Composable
fun SecaoDicas() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Dicas de bem-estar", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Card(
            modifier = Modifier


                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White)


        ) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Medite 3 min. com respiração")
                Text("Faça pausas a cada 90 minutos")
                Text("Ouça algo que você gosta hoje")
            }
        }
    }
}

@Composable
fun SecaoRecursos(navController: NavController) {
    val context = LocalContext.current

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Recursos de apoio", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Button(onClick = {

            val numeroDeTelefone = "188"


            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$numeroDeTelefone")
            }


            context.startActivity(intent) }, modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = AppBlueLight),) {
            Text("Canal de escuta")
        }
        Button(onClick = { navController.navigate("chatsup") }, modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = AppBlueLight),) {
            Text("Chat de Suporte")
        }
    }
}

//@Preview(showSystemUi = true,showBackground = true)
//@Composable
//fun SupportScreenPreview() {
//     val navController = rememberNavController() // cria um navController de teste
//        SupportScreen(navController = navController) }



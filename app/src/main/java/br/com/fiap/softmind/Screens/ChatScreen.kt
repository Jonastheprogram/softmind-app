package br.com.fiap.softmind.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.fiap.softmind.ui.ViewModel.ChatViewModel
import br.com.fiap.softmind.ui.ViewModel.Mensagem
import br.com.fiap.softmind.ui.components.GradientBackground
import br.com.fiap.softmind.ui.theme.AppBlueLight
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController) {
    val viewModel: ChatViewModel = viewModel()
    val mensagens by viewModel.mensagens.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(mensagens.size) {
        coroutineScope.launch {
            if (mensagens.isNotEmpty()) {
                listState.animateScrollToItem(mensagens.size - 1)
            }
        }
    }

    GradientBackground {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("Assistente Softek", fontWeight = FontWeight.Bold, color = Color.White) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),

                )
            },
            bottomBar = { AppBottomBar(navController = navController) }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                    items(mensagens, key = { it.id }) { mensagem ->
                        MessageBubble(mensagem = mensagem)
                    }
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                }


                ChatInput(onSendMessage = { texto ->
                    viewModel.enviarMensagem(texto)
                })
            }
        }
    }
}

@Composable
fun MessageBubble(mensagem: Mensagem) {
    val alignment = if (mensagem.isFromUser) Alignment.CenterEnd else Alignment.CenterStart
    val backgroundColor = if (mensagem.isFromUser) AppBlueLight else Color.White
    val textColor = if (mensagem.isFromUser) Color.White else Color.Black.copy(alpha = 0.8f)
    val shape = if (mensagem.isFromUser) {
        RoundedCornerShape(20.dp, 4.dp, 20.dp, 20.dp)
    } else {
        RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = if (mensagem.isFromUser) 48.dp else 0.dp,
                end = if (mensagem.isFromUser) 0.dp else 48.dp
            ),
        contentAlignment = alignment
    ) {
        Surface(
            shape = shape,
            color = backgroundColor,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Text(
                text = mensagem.texto,
                color = textColor,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
            )
        }
    }
}

@Composable
fun ChatInput(onSendMessage: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    Surface(shadowElevation = 8.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Digite sua mensagem...") },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
            IconButton(onClick = {
                onSendMessage(text)
                text = ""
            }) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Enviar Mensagem",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}


//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun ChatScreenPreview() {
//    val navController = rememberNavController()
//    ChatScreen(navController = navController)
//}
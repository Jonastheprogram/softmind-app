package br.com.fiap.softmind.ui.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.softmind.network.ChatRequest
import br.com.fiap.softmind.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID


data class Mensagem(
    val texto: String,
    val isFromUser: Boolean,
    val id: String = UUID.randomUUID().toString()
)
class ChatViewModel : ViewModel() {
    private val _mensagens = MutableStateFlow<List<Mensagem>>(emptyList())
    val mensagens = _mensagens.asStateFlow()

    init {
        // Adiciona uma mensagem de boas-vindas da IA
        _mensagens.value = listOf(Mensagem("Olá! Sou o assistente SoftMind. Como posso te ajudar hoje?", false))
    }

    fun enviarMensagem(texto: String) {
        if (texto.isBlank()) return

        val novaMensagemUsuario = Mensagem(texto, true)
        _mensagens.value += novaMensagemUsuario

        val digitando = Mensagem("...", false)
        _mensagens.value += digitando

        viewModelScope.launch {
            try {
                val request = ChatRequest(texto)
                val response = RetrofitClient.instance.enviarMensagemChat(request)
                _mensagens.value = _mensagens.value.dropLast(1)

                if (response.isSuccessful) {
                    val respostaIA = response.body()?.response ?: "Não obtive uma resposta."
                    _mensagens.value += Mensagem(respostaIA, false)
                } else {
                    _mensagens.value += Mensagem("Ocorreu um erro ao conectar. Tente novamente.", false)
                }
            } catch (e: Exception) {
                _mensagens.value = _mensagens.value.dropLast(1)
                _mensagens.value += Mensagem("Falha na rede. Verifique sua conexão.", false)
            }
        }
    }
}
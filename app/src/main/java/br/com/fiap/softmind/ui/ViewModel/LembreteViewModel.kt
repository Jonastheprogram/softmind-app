package br.com.fiap.softmind.ui.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.softmind.model.Lembrete
import br.com.fiap.softmind.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LembreteViewModel : ViewModel() {

    private val _lembretes = MutableStateFlow<List<Lembrete>>(emptyList())
    val lembretes = _lembretes.asStateFlow()

    init {
        fetchLembretes()
    }

    fun fetchLembretes() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getLembretes()
                if (response.isSuccessful) {
                    _lembretes.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                println("FALHA AO BUSCAR LEMBRETES: ${e.message}")
            }
        }
    }

    fun criarLembrete(hora: String, descricao: String) {
        viewModelScope.launch {
            try {
                val novoLembrete = Lembrete(id = null, hora = hora, descricao = descricao)
                val response = RetrofitClient.instance.criarLembrete(novoLembrete)
                if (response.isSuccessful) {
                    fetchLembretes()
                }
            } catch (e: Exception) {
                println("FALHA AO CRIAR LEMBRETE: ${e.message}")
            }
        }
    }

    fun deletarLembrete(id: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.deletarLembrete(id)
                if (response.isSuccessful) {
                    fetchLembretes()
                }
            } catch (e: Exception) {
                println("FALHA AO DELETAR LEMBRETE: ${e.message}")
            }
        }
    }
}
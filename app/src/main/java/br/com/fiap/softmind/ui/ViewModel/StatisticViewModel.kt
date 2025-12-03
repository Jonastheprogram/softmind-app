package br.com.fiap.softmind.ui.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.softmind.model.HumorStat
import br.com.fiap.softmind.model.RegistroDiarioHumor
import br.com.fiap.softmind.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log

class StatisticViewModel : ViewModel() {

    private val _humorStats = MutableStateFlow<List<HumorStat>>(emptyList())

    val humorStats = _humorStats.asStateFlow()

    private val _registrosDiarios = MutableStateFlow<List<RegistroDiarioHumor>>(emptyList())
    val registrosDiarios = _registrosDiarios.asStateFlow()

    init {
        fetchHumorStats()
        fetchRegistrosDiarios()
    }

    private fun fetchHumorStats() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getHumorStats()
                if (response.isSuccessful) {
                    _humorStats.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                println("FALHA AO BUSCAR ESTATÍSTICAS: ${e.message}")
            }
        }
    }

    fun fetchRegistrosDiarios() {
        viewModelScope.launch {
            try {
                Log.d("StatisticViewModel", "1. Buscando registros diários da API...")
                val response = RetrofitClient.instance.getRegistros()

                if (response.isSuccessful) {
                    val listaDeRegistros = response.body()
                    Log.d("StatisticViewModel", "2. Resposta com sucesso. Itens recebidos: ${listaDeRegistros?.size}")

                    listaDeRegistros?.forEach { registro ->
                        Log.d("StatisticViewModel", "   - Registro: $registro")
                    }
                    _registrosDiarios.value = listaDeRegistros ?: emptyList()
                } else {
                    Log.e("StatisticViewModel", "3. Resposta com ERRO. Código: ${response.code()} | Corpo do erro: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("StatisticViewModel", "4. FALHA GERAL na chamada de rede.", e)
            }
        }
    }

    fun deletarRegistroDiario(id: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.deletarRegistro(id)
                if (response.isSuccessful) {
                    fetchRegistrosDiarios()
                }
            } catch (e: Exception) {
                println("FALHA AO DELETAR REGISTRO DIÁRIO: ${e.message}")
            }
        }
    }
}
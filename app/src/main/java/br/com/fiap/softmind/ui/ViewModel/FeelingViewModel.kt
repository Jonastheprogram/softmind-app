package br.com.fiap.softmind.ui.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.softmind.model.Humor
import br.com.fiap.softmind.network.RetrofitClient
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FeelingViewModel : ViewModel() {


    fun registrarSentimento(sentimento: String, onSucesso: () -> Unit) {
        viewModelScope.launch {
            try {

                val novoHumor = Humor(id = null, sentimento = sentimento, dataCheckin = null)


                val response = RetrofitClient.instance.criarHumor(novoHumor)

                if (response.isSuccessful) {
                    println("SUCESSO: Sentimento '${response.body()?.sentimento}' registrado com sucesso!")

                    listaHumores()

                    onSucesso()
                } else {

                    println(
                        "ERRO NA API: Código ${response.code()} - ${
                            response.errorBody()?.string()
                        }"
                    )
                }
            } catch (e: Exception) {

                println("FALHA DE REDE: ${e.message}")
            }
        }
    }
    private val _humores = MutableStateFlow<List<Humor>>(emptyList())
    val humores = _humores.asStateFlow()

    fun listaHumores() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getHumores()
                if (response.isSuccessful) {
                    _humores.value =
                        response.body()?.sortedByDescending { it.dataCheckin } ?: emptyList()
                } else {
                    println("ERRO AO BUSCAR HUMORES: ${response.code()}")
                }
            } catch (e: Exception) {
                println("FALHA DE REDE AO BUSCAR HUMORES: ${e.message}")
            }
        }
    }

    fun deletarHumor(id: String) {
        viewModelScope.launch {
            try {

                val response = RetrofitClient.instance.deletarHumor(id)
                if (response.isSuccessful) {
                    println("SUCESSO: Humor com ID $id deletado.")

                    listaHumores()
                } else {
                    println("ERRO AO DELETAR: Código ${response.code()}")
                }
            } catch (e: Exception) {
                println("FALHA DE REDE AO DELETAR: ${e.message}")
            }
        }
    }
}
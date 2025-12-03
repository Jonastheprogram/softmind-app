//package br.com.fiap.softmind.ui
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import br.com.fiap.softmind.model.Humor
//import br.com.fiap.softmind.network.RetrofitClient
//import kotlinx.coroutines.launch
//import java.lang.Exception
//
//class MainViewModel : ViewModel() {
//
//    fun criarNovoHumor(sentimento: String) {
//
//        viewModelScope.launch {
//            try {
//
//                val novoHumor = Humor(id = null, sentimento = sentimento, dataCheckin = null)
//
//
//                val response = RetrofitClient.instance.criarHumor(novoHumor)
//
//                if (response.isSuccessful) {
//
//                    val humorCriado = response.body()
//                    println("SUCESSO: Humor criado com ID: ${humorCriado?.id}")
//                } else {
//
//                    println("ERRO: Código ${response.code()} - ${response.message()}")
//                }
//            } catch (e: Exception) {
//                println("FALHA NA REDE: ${e.message}")
//            }
//        }
//    }
//}
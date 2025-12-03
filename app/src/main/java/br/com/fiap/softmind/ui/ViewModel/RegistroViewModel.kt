package br.com.fiap.softmind.ui.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.softmind.model.RegistroDiarioHumor
import br.com.fiap.softmind.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class SubmissionState {
    object Idle : SubmissionState()
    object Loading : SubmissionState()
    object Success : SubmissionState()
}

class RegistroViewModel : ViewModel() {

    private val _submissionState = MutableStateFlow<SubmissionState>(SubmissionState.Idle)
    val submissionState = _submissionState.asStateFlow()

    fun salvarRegistro(carga: String, apoio: String, desabafo: String) {
        _submissionState.value = SubmissionState.Loading
        viewModelScope.launch {
            try {
                val novoRegistro = RegistroDiarioHumor(
                    id = null,
                    cargaTarefas = carga,
                    apoioEquipe = apoio,
                    desabafo = desabafo,
                    dataRegistro = null
                )

                val response = RetrofitClient.instance.criarRegistro(novoRegistro)
                if (response.isSuccessful) {
                    _submissionState.value = SubmissionState.Success
                } else {
                    _submissionState.value = SubmissionState.Idle
                }
            } catch (e: Exception) {
                _submissionState.value = SubmissionState.Idle
            }
        }
    }

    fun resetState() {
        _submissionState.value = SubmissionState.Idle
    }
}
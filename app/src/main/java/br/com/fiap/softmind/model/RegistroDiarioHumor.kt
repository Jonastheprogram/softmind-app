package br.com.fiap.softmind.model

import com.google.gson.annotations.SerializedName
data class RegistroDiarioHumor(

    val id: String?,
    @SerializedName("cargaTarefas")
    val cargaTarefas: String?,
    @SerializedName("apoioEquipe")
    val apoioEquipe: String?,
    val desabafo: String?,
    val dataRegistro: String?

)
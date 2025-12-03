package br.com.fiap.softmind.network

import br.com.fiap.softmind.model.Humor
import br.com.fiap.softmind.model.HumorStat
import br.com.fiap.softmind.model.Lembrete
import br.com.fiap.softmind.model.RegistroDiarioHumor
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class ChatRequest(val message: String)
data class ChatResponse(val response: String)


interface ApiService {

    //todos os endpoints para chamadas restful da api
    @GET("api/humores")
    suspend fun getHumores(): Response<List<Humor>>


    @POST("api/humores")
    suspend fun criarHumor(@Body novoHumor: Humor): Response<Humor>



    @POST("api/registros")
    suspend fun criarRegistro(@Body novoRegistro: RegistroDiarioHumor): Response<RegistroDiarioHumor>

    @DELETE("api/humores/{id}")
    suspend fun deletarHumor(@Path("id") id: String): Response<Void>

    @GET("api/humores/stats")
    suspend fun getHumorStats(): Response<List<HumorStat>>

    @GET("api/lembretes")
    suspend fun getLembretes(): Response<List<Lembrete>>

    @POST("api/lembretes")
    suspend fun criarLembrete(@Body novoLembrete: Lembrete): Response<Lembrete>

    @DELETE("api/lembretes/{id}")
    suspend fun deletarLembrete(@Path("id") id: String): Response<Unit>

    @POST("api/chat")
    suspend fun enviarMensagemChat(@Body request: ChatRequest): Response<ChatResponse>


    @GET("api/registros")
    suspend fun getRegistros(): Response<List<RegistroDiarioHumor>>


    @DELETE("api/registros/{id}")
    suspend fun deletarRegistro(@Path("id") id: String): Response<Unit>
}





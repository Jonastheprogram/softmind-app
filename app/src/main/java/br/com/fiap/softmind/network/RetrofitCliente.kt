package br.com.fiap.softmind.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://192.168.1.3:8080/" //configure o endereço do servidor aqui sempre terminando com a porta 8080
    private const val API_KEY = "chave-secreta-muito-segura-e-dificil-de-adivinhar-12345" //mesma chave da api do inteliJ (arquivo application.properties)


    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .header("X-API-KEY", API_KEY)
                .build()
            chain.proceed(newRequest)
        }
        .build()


    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}
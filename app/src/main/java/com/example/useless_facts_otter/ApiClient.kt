package com.example.useless_facts_otter

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object ApiClient {
    private val api: Api

    init {
        val gson = GsonBuilder().create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://uselessfacts.jsph.pl/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        api = retrofit.create(Api::class.java)
    }

    suspend fun getFact(): String {
        val response = api.getFact()
        return response.text
    }

    data class FactResponse(
        val id: String,
        val text: String,
        val source: String,
        val source_url: String,
        val language: String,
        val permalink: String
    )

    private interface Api {
        @GET("api/v2/facts/random")
        suspend fun getFact(): FactResponse
    }
}

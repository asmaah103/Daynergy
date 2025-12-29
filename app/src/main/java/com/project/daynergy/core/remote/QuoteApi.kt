package com.project.daynergy.core.remote

import retrofit2.http.GET

interface QuoteApi {

    @GET("api/random")
    suspend fun getRandomQuote(): List<QuoteDto>
}

package com.project.daynergy.repository

import com.project.daynergy.core.remote.QuoteApi
import com.project.daynergy.core.remote.QuoteDto

class QuoteRepository(
    private val api: QuoteApi
) {

    suspend fun getQuote(): QuoteDto {
        return api.getRandomQuote().first()
    }
}

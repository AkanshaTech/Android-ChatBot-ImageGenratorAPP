package com.example.chatbot.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtilities {
    fun apiUtilities():ApiInterface{

        return Retrofit.Builder()
             .baseUrl("https://api.openai.com/")
             .addConverterFactory(GsonConverterFactory.create())
             .build()
             .create(ApiInterface::class.java)

    }
}
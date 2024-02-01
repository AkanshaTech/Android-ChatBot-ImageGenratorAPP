package com.example.chatbot.api

import com.example.chatbot.model.response.chat.ChatModel
import com.example.chatbot.model.response.chat.Response
import com.example.chatbot.model.response.imageresponse.GenrateImageModel
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {

    @POST("/v1/chat/completions")
    suspend fun getChat(
        @Header("Content-Type") contentType:String,
        @Header("Authorization") authorization:String,
        @Body requestBody:RequestBody
    ): Response

    @POST("/v1/images/generations")
    suspend fun generateImage(
        @Header("Content-Type") contentType:String,
        @Header("Authorization") authorization:String,
        @Body requestBody:RequestBody
    ): GenrateImageModel




}
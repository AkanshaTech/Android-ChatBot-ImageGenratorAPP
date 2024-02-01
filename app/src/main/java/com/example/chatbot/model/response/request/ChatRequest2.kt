package com.example.chatbot.model.response.request

data class ChatRequest2(
    val frequency_penalty: Int,
    val max_tokens: Int,
    val messages: List<Message>,
    val model: String,
    val presence_penalty: Int,
    val temperature: Double,
    val top_p: Int
)
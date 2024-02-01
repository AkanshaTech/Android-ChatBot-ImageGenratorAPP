package com.example.chatbot.model.response.request

data class ImageGenerateRequest(
    val n: Int,
    val prompt: String,
    val size: String
)
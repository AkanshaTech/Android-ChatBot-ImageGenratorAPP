package com.example.chatbot.model.response.chat

data class Response(
    val choices: List<ChoiceX>,
    val created: Int,
    val id: String,
    val model: String,
    val `object`: String,
    val system_fingerprint: Any,
    val usage: UsageX
)
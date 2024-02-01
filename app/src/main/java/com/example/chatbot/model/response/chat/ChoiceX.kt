package com.example.chatbot.model.response.chat

data class ChoiceX(
    val finish_reason: String,
    val index: Int,
    val logprobs: Any,
    val message: Message
)
package com.example.chatbot.model.response.chat

data class UsageX(
    val completion_tokens: Int,
    val prompt_tokens: Int,
    val total_tokens: Int
)
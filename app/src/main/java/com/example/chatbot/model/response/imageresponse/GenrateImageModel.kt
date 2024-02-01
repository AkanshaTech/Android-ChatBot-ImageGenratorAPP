package com.example.chatbot.model.response.imageresponse

import com.example.chatbot.model.response.imageresponse.Data

data class GenrateImageModel(
    val created: Int,
    val `data`: List<Data>
)
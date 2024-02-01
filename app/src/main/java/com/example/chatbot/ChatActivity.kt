package com.example.chatbot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbot.adapter.MessageAdapter
import com.example.chatbot.api.ApiUtilities
import com.example.chatbot.databinding.ActivityChatBinding
import com.example.chatbot.model.response.MessageModel
import com.example.chatbot.model.response.request.ChatRequest2
import com.example.chatbot.model.response.request.Message
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody


class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private var list = ArrayList<MessageModel>()
    private lateinit var adapter: MessageAdapter
    private lateinit var mLayoutManger: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mLayoutManger = LinearLayoutManager(this)
        mLayoutManger.stackFromEnd = true
        adapter = MessageAdapter(list)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = mLayoutManger

        binding.backBtn.setOnClickListener() {
            finish()
        }
      //  callApi()
        binding.sendbtn.setOnClickListener {
            if (binding.userMsg.text!!.isEmpty()) {
                Toast.makeText(this, "Please ask your question", Toast.LENGTH_SHORT).show()
            } else {
                callApi()
            }
        }

    }

    private fun callApi() {
        list.add(MessageModel(isUSer = true, isImage = false, binding.userMsg.text.toString()))
        adapter.notifyItemInserted(list.size - 1)
        binding.recyclerView.recycledViewPool.clear()
        binding.recyclerView.smoothScrollToPosition(list.size - 1)
        val apiInterface = ApiUtilities.apiUtilities()
        val requestBody = RequestBody.create(
            MediaType.parse("application/json"),
            Gson().toJson(
                ChatRequest2(
                    0,
                    512,
                    listOf(
                        Message(role = "user", content = binding.userMsg.text.toString())
                    ),
                    "gpt-3.5-turbo",
                   0,
                    0.5,
                    1,

                  /*  250,
                    "gpt-3.5-turbo-instruct",
                    binding.userMsg.text.toString(),
                    0.7*/
                )
            )
        )
        val contentType = "application/json"
        val authorization = "Bearer ${Utils.API_KEY}"

        lifecycleScope.launch(Dispatchers.IO) {
            try {
             //   list.add(MessageModel(false,false,"typing...."))
                val response = apiInterface.getChat(contentType, authorization, requestBody)

                val textResponse = response.choices[0].message.content
               withContext(Dispatchers.Main) {
                    adapter.notifyItemInserted(list.size - 1)
                    binding.recyclerView.recycledViewPool.clear()
                    binding.recyclerView.smoothScrollToPosition(list.size - 1)
                }
               // list.removeAt(list.size - 1)
                //adapter.notifyItemRemoved(list.size-1)
                list.add(MessageModel(false, false, textResponse))
                binding.userMsg.text!!.clear()



            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ChatActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
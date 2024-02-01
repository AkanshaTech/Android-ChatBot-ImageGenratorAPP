package com.example.chatbot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbot.adapter.MessageAdapter
import com.example.chatbot.api.ApiUtilities
import com.example.chatbot.databinding.ActivityChatBinding
import com.example.chatbot.databinding.ActivityImageBinding
import com.example.chatbot.model.response.MessageModel
import com.example.chatbot.model.response.request.ChatRequest
import com.example.chatbot.model.response.request.ImageGenerateRequest
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody

class ImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageBinding
    private var list=ArrayList<MessageModel>()
    private lateinit var adapter: MessageAdapter
    private lateinit var mLayoutManger: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mLayoutManger= LinearLayoutManager(this)
        mLayoutManger.stackFromEnd =true
        adapter= MessageAdapter(list)
        binding.recyclerView.adapter=adapter
        binding.recyclerView.layoutManager=mLayoutManger

        binding.backBtn.setOnClickListener(){
            finish()
        }
        binding.sendbtn.setOnClickListener{
            if(binding.userMsg.text!!.isEmpty()){
                Toast.makeText(this,"Please ask your question", Toast.LENGTH_SHORT).show()
            }
            else{
                callApi()
            }
        }

    }

    private fun callApi() {

        list.add(MessageModel(isUSer = true, isImage = false,binding.userMsg.text.toString()))
        adapter.notifyItemInserted(list.size-1)
        binding.recyclerView.recycledViewPool.clear()
        binding.recyclerView.smoothScrollToPosition(list.size-1)
        val apiInterface= ApiUtilities.apiUtilities()
        val requestBody= RequestBody.create(
            MediaType.parse("application/json"),
            Gson().toJson(
                ImageGenerateRequest(
                    1,
                    binding.userMsg.text.toString(),
                    "1024x1024"
                )
            )
        )
        val contentType= "application/json"
        val authorization="Bearer ${Utils.API_KEY}"

        lifecycleScope.launch(Dispatchers.IO){
            try {

                val response = apiInterface.generateImage(

                    contentType, authorization, requestBody
                )

                val textResponse = response.data.first().url

                withContext(Dispatchers.Main){
                    adapter.notifyItemInserted(list.size - 1)
                    binding.recyclerView.recycledViewPool.clear()
                    binding.recyclerView.smoothScrollToPosition(list.size - 1)
                }
                list.add(MessageModel(false, true, textResponse.trim()))
                binding.userMsg.text!!.clear()
            }

            catch (e:Exception) {
                withContext(Dispatchers.Main){
                    Toast.makeText(this@ImageActivity,e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    }

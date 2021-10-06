package com.example.coroutinesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class MainActivity : AppCompatActivity() {
    lateinit var tvRandomAdvice: TextView
    lateinit var btAdvice: ImageButton
    var advices:Advices?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvRandomAdvice = findViewById(R.id.tvRandomAdvice)
        btAdvice = findViewById(R.id.btAdvice)
        btAdvice.setOnClickListener {
            CoroutineScope(IO).launch {
                val advice = async {
                    getAdvice()

                }.await()
                if (advice!=null)
                {
                    setAdvice()

                }


            }
        }
    }
    private fun getAdvice():Boolean{
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val call: Call<Advices>? = apiInterface!!.doGetListResources()
       try {
           advices=call?.execute()?.body()
       }catch (e:Exception)
       {
           Toast.makeText(applicationContext, "" + e.message, Toast.LENGTH_SHORT).show()
           call?.cancel()
       }

        return true
    }
    private suspend fun setAdvice(){
        withContext(Main){

            tvRandomAdvice.text=advices?.slip?.advice.toString()
        }

    }
}
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
import okhttp3.Dispatcher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class MainActivity : AppCompatActivity() {
    lateinit var tvRandomAdvice: TextView
    lateinit var btAdvice: ImageButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvRandomAdvice = findViewById(R.id.tvRandomAdvice)
        btAdvice = findViewById(R.id.btAdvice)
        btAdvice.setOnClickListener {
            CoroutineScope(IO).launch {

                val advice=getAdvice()

                withContext(Main){
                    tvRandomAdvice.text=advice
                }
            }

        }
    }
    private suspend fun getAdvice():String{
        var advice=""
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val call: Call<advices>? = apiInterface!!.doGetListResources()
        call?.enqueue(object : Callback<advices?> {
            override fun onResponse(
                call: Call<advices?>?,
                response: Response<advices?>
            ) {
               // Log.d("TAG", response.code().toString() + "")
                val resource: advices? = response.body()
                val randomAdvice = resource?.slip
                advice = randomAdvice?.advice?.toString().toString()

               // tvRandomAdvice.text=randomAdvice?.advice?.toString()

            }


            override fun onFailure(call: Call<advices?>, t: Throwable?) {
                advice="Something went wrong :("
                call.cancel()
            }
        })
        return advice
    }
}
package com.example.coroutinesapp
import com.google.gson.annotations.SerializedName

class advices {

    @SerializedName("slip")
    var slip: Advices? = null

    class Advices {

        @SerializedName("id")
        var id: String? = null

        @SerializedName("advice")
        var advice: String? = null
    }
}
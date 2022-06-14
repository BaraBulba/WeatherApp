package android.example.weatherapplication.data.network.response


import com.google.gson.annotations.SerializedName

data class ConditionXX(
    val code: Int,
    val icon: String,
    val text: String
)
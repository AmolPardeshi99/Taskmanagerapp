package com.masai.taskmanagerapp.models.remote.responses
import com.google.gson.annotations.SerializedName


data class LoginResponse (
    @SerializedName("user") val user : User,
    @SerializedName("token") val token : String
)
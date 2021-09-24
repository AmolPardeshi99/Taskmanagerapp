package com.masai.taskmanagerapp.models.remote.requests


import com.google.gson.annotations.SerializedName

data class CreateTaskRequest(
    @SerializedName("description")
    val description: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("title")
    val title: String?
)
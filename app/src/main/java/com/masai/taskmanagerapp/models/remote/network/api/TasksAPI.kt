package com.masai.taskmanagerapp.models.remote.network.api

import com.masai.taskmanagerapp.models.remote.requests.CreateTaskRequest
import com.masai.taskmanagerapp.models.remote.requests.LoginRequestModel
import com.masai.taskmanagerapp.models.remote.responses.CreateTaskResponseModel
import com.masai.taskmanagerapp.models.remote.responses.GetTaskResponseModel
import com.masai.taskmanagerapp.models.remote.responses.LoginResponse
import retrofit2.http.*


interface TasksAPI {
    //@Headers("Accept: application/json")
    @POST("users/login")
    suspend fun login(
        @Body loginRequest: LoginRequestModel
    ): LoginResponse


    // for getting all tasks
    @GET("tasks")
    suspend fun getTaskFromAPI(
        @Header("Authorization") token:String
    ): GetTaskResponseModel


    @POST("tasks")
    suspend fun createTask(
        @Header("Authorization") token:String,
        @Body createTaskRequest: CreateTaskRequest
    ): CreateTaskResponseModel


}



/*@POST("/v2/courses/new")
   suspend fun signUp(
       @Header("Authorization") authToken: String?,
       @Body createNewCourseRequest: CreateNewCourseRequest
   ): CreateNewCourseResponse

   @GET("app/android/version")
   suspend fun getAllTasks(
       @Header("Content-Type") contentType: String
   ): ForceUpdateAppResponseModel*/


/*@POST("v2/data/{code}/register")
suspend fun getPostPaymentDetails(
    @Header("Authorization") authToken: String?,
    @Path("code") code: String,
    @Body postPaymentRequestModel: PostPaymentRequestModel
): PostPaymentRequestModel*/


/*@GET("/v2/videos/generate-live-token")
suspend fun getLiveToken(
    @Header("Authorization") token: String,
    @Header("Content-Type") contentType: String,
    @Query("l_id") lessonId: String,
    @Query("user_id") userId: String,
    @Query("role") role: String
): GetLiveTokenResponseModel*/
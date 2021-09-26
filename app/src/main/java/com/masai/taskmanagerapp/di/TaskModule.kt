package com.masai.taskmanagerapp.di

import android.content.Context
import androidx.room.Room
import com.masai.taskmanagerapp.models.local.TaskRoomDataBase
import com.masai.taskmanagerapp.models.local.TaskappDAO
import com.masai.taskmanagerapp.models.remote.network.api.Network
import com.masai.taskmanagerapp.models.remote.network.api.TasksAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TaskModule {

    @Singleton
    @Provides
    fun provideAPIService(): TasksAPI {
        val builder  = Retrofit.Builder()
            .baseUrl("http://13.232.169.202:8080/" )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return builder.create(TasksAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideRoomDb(@ApplicationContext context: Context):TaskRoomDataBase{
        val builder = Room.databaseBuilder(
            context.applicationContext,
            TaskRoomDataBase::class.java,
            "task_db"
        )
       builder.fallbackToDestructiveMigration()
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideTaskDao(db: TaskRoomDataBase): TaskappDAO{
        return db.getTaskDAO()
    }
}
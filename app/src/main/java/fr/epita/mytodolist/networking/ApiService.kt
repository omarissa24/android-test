package fr.epita.mytodolist.networking

import fr.epita.mytodolist.model.TodoModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
//    get todos for user with userId
     @GET("todos")
        fun getTodos(@Query("userId") userId: Int): Call<List<TodoModel>>

//    add a new todo item
    @POST("todos")
    fun addTodo(@Body todo: TodoModel): Call<TodoModel>
}
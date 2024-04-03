package fr.epita.mytodolist

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import fr.epita.mytodolist.model.TodoModel
import fr.epita.mytodolist.networking.ApiConfig
import retrofit2.Call
import retrofit2.Response

class AddTodoActivity : AppCompatActivity() {

    private var userId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)

        val etTodoItemText: EditText = findViewById(R.id.etTodoItemText)
        val btnDeleteText: Button = findViewById(R.id.btnDeleteText)
        val btnValidate: Button = findViewById(R.id.btnValidate)

        btnDeleteText.setOnClickListener {
            // Clear the EditText when the Delete button is pressed
            etTodoItemText.text.clear()
        }

        btnValidate.setOnClickListener {
            // Code to add the new ToDo item goes here
            // You might want to return the result to the calling activity or save it
            val newTodoText = etTodoItemText.text.toString().trim()
            val completed = false


            if(newTodoText.isNotEmpty()) {
                val newTodo = TodoModel(userId, 0, newTodoText, completed)
                val apiService = ApiConfig.getApiService()
                val call = apiService.addTodo(newTodo)
                call.enqueue(object : retrofit2.Callback<TodoModel> {
                    override fun onResponse(call: Call<TodoModel>, response: Response<TodoModel>) {
                        if (response.isSuccessful) {
                            // Show a success message to the user
                            finish()
                        } else {
                            // Show an error message to the user or shake the EditText to indicate a problem
                        }
                    }

                    override fun onFailure(call: Call<TodoModel>, t: Throwable) {
                        // Show an error message to the user or shake the EditText to indicate a problem
                    }
                })
                finish()
            } else {
                // Show an error message to the user or shake the EditText to indicate a problem
            }
        }
    }
}

package fr.epita.mytodolist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.epita.mytodolist.model.TodoModel
import fr.epita.mytodolist.networking.ApiConfig
import retrofit2.Call
import retrofit2.http.*

class ListActivity : AppCompatActivity() {
    lateinit var todoAdapter: TodoAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var userGreeting: TextView
    private var userId: Int = 0
    private  var completedCount = 0
    private  var uncompletedCount = 0

    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        userId = intent.getIntExtra("userId", 0)
        val username = intent.getStringExtra("username")
        userGreeting = findViewById(R.id.userGreeting)
        recyclerView = findViewById(R.id.todoList)

        userGreeting.text = getString(R.string.user_greeting, username)

        // Initialize RecyclerView with the adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        todoAdapter = TodoAdapter(listOf()) // Initialize with empty list or data from the server
        recyclerView.adapter = todoAdapter

        fetchTodoItems(userId)
    }

    override fun onResume() {
        super.onResume()
        fetchTodoItems(userId)
    }

    private fun fetchTodoItems(userId: Int) {

        val apiService = ApiConfig.getApiService()
        val call = apiService.getTodos(userId)
        call.enqueue(object : retrofit2.Callback<List<TodoModel>> {
            override fun onResponse(call: Call<List<TodoModel>>, response: retrofit2.Response<List<TodoModel>>) {
                if (response.isSuccessful) {
                    val todos = response.body()
                    if (todos != null) {
                        todoAdapter.updateItems(todos).also {
                            completedCount = todoAdapter.getCompletedItemsCount()
                            uncompletedCount = todoAdapter.getUncompletedItemsCount()
                            // Update the UI with the counts

                            val totalItems = findViewById<TextView>(R.id.totalItems)
                            val completedItems = findViewById<TextView>(R.id.totalCompleted)
                            val uncompletedItems = findViewById<TextView>(R.id.totalPending)

                            totalItems.text = getString(R.string.total_items, completedCount + uncompletedCount)
                            completedItems.text = getString(R.string.total_completed, completedCount)
                            uncompletedItems.text = getString(R.string.total_pending, uncompletedCount)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<TodoModel>>, t: Throwable) {
                // Handle network errors here
            }
        })

    }

    // When you click on the add button, start the activity to add a new ToDo item.
    fun onAddTodoClick(view: android.view.View) {
        val intent = Intent(this, AddTodoActivity::class.java)
        intent.putExtra("userId", userId)
        startActivity(intent)
    }
}

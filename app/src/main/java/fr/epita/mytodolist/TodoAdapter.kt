package fr.epita.mytodolist

import android.annotation.SuppressLint
import android.view.LayoutInflater.*
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.epita.mytodolist.model.TodoModel

class TodoAdapter(private var items: List<TodoModel>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<TodoModel>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = from(parent.context).inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        // Set a click listener for the CheckBox
        holder.checkBox.setOnClickListener {
            item.completed = !item.completed
            updateItems(items)
        }
    }

    fun getCompletedItemsCount(): Int {
        return items.count { it.completed }
    }

    fun getUncompletedItemsCount(): Int {
        return items.count { !it.completed }
    }

    override fun getItemCount() = items.size

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBoxTodoItem)
        private val textViewContent: TextView = itemView.findViewById(R.id.textViewTodoContent)

        fun bind(todoItem: TodoModel) {
            checkBox.isChecked = todoItem.completed
            textViewContent.text = todoItem.title
        }

    }
}
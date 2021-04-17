package xone.com.todolist.adapter

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xone.com.todolist.R
import xone.com.todolist.database.entity.TodoEntity

class TodoAdapter() : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    var data = listOf<TodoEntity>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount() = data.size

    class TodoViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val todoTitle: AppCompatTextView = itemView.findViewById(R.id.todoTitle)

        fun bind(item: TodoEntity) {
            todoTitle.text = item.todo
        }

        companion object {
            fun from(parent: ViewGroup): TodoViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.item_todo, parent, false)

                return TodoViewHolder(view)
            }
        }
    }
}
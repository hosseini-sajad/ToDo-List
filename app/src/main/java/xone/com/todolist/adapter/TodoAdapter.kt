package xone.com.todolist.adapter

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xone.com.todolist.R
import xone.com.todolist.database.entity.TodoEntity

class TodoAdapter() : ListAdapter<TodoEntity,
        TodoAdapter.TodoViewHolder>(TodoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

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

    class TodoDiffCallback : DiffUtil.ItemCallback<TodoEntity>() {
        override fun areItemsTheSame(oldItem: TodoEntity, newItem: TodoEntity): Boolean {
            return oldItem.todoId == newItem.todoId
        }

        override fun areContentsTheSame(oldItem: TodoEntity, newItem: TodoEntity): Boolean {
            return oldItem == newItem
        }

    }
}
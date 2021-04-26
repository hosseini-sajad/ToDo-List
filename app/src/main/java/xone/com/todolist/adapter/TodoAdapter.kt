package xone.com.todolist.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xone.com.todolist.R
import xone.com.todolist.database.entity.TodoEntity

class TodoAdapter(private val onClickFunction: (TodoEntity, appCompatTextView: AppCompatTextView) -> Unit) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

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
        holder.bind(item, onClickFunction)
    }

    class TodoViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val todoTitle: AppCompatTextView = itemView.findViewById(R.id.todoTitle)

        fun bind(
            item: TodoEntity,
            clickListener: (TodoEntity, todoTitle: AppCompatTextView) -> Unit
        ) {
            todoTitle.text = item.todo
            if (item.isDone) {
                todoTitle.setTextColor(
                    ContextCompat.getColor(
                        todoTitle.context,
                        R.color.text_hint_color
                    )
                )
                todoTitle.paintFlags = todoTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            else {
                todoTitle.setTextColor(
                    ContextCompat.getColor(
                        todoTitle.context,
                        R.color.text_primary_color
                    )
                )
                todoTitle.paintFlags = todoTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
            itemView.setOnClickListener { clickListener(item, todoTitle) }
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

    override fun getItemCount() = data.size

    /*class TodoDiffCallback : DiffUtil.ItemCallback<TodoEntity>() {
        override fun areItemsTheSame(oldItem: TodoEntity, newItem: TodoEntity): Boolean {
            return oldItem.todoId == newItem.todoId
        }

        override fun areContentsTheSame(oldItem: TodoEntity, newItem: TodoEntity): Boolean {
            return oldItem == newItem
        }

    }*/
}
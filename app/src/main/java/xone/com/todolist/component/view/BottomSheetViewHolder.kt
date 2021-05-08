package xone.com.todolist.component.view

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import xone.com.todolist.R
import xone.com.todolist.database.entity.BottomSheetItemEntity

internal class BottomSheetViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val itemIcon: AppCompatImageView = view.findViewById(R.id.itemIcon)
    private val itemTitle: AppCompatTextView = view.findViewById(R.id.itemTitle)

    fun bind(item: BottomSheetItemEntity, onClick: () -> Unit) {
        if (item.itemIconResource != null) {
            itemIcon.setImageResource(item.itemIconResource)
            itemIcon.visibility = View.VISIBLE
        } else {
            itemIcon.visibility = View.GONE
        }
        itemTitle.setText(item.itemTitleResource)
        itemView.setOnClickListener {
            onClick.invoke()
            item.itemOnClickListener.invoke()
        }
    }
}
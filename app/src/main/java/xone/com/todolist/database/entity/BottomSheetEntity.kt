package xone.com.todolist.database.entity

data class BottomSheetEntity(
    val items: List<BottomSheetItemEntity>,
    val sheetTitle: String = "",
    val dismissOnClick: Boolean = true
)
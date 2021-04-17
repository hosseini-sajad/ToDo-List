package xone.com.todolist.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import xone.com.todolist.R
import xone.com.todolist.database.entity.TodoEntity
import xone.com.todolist.databinding.FragmentAddtodoBinding
import xone.com.todolist.viewmodel.TodoViewModel
import java.util.*

class AddTodoFragment : Fragment() {

    private lateinit var binding: FragmentAddtodoBinding
    private lateinit var date: Date
    private lateinit var calendar: Calendar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_addtodo, container, false)

        calendar = Calendar.getInstance()
        date = calendar.time

        val todoViewModel = ViewModelProvider(
            this, TodoViewModel.Factory(
                requireNotNull(this.activity).application
                //date
            )
        ).get(TodoViewModel::class.java)

        binding.dateClick.setOnClickListener {
            showBottomSheetCalendar()
        }

        binding.todoInput.afterTextChanged {
            binding.saveTodo.isEnabled = it.trim() != ""
        }

        binding.saveTodo.setOnClickListener {
            saveTodoInDatabase(todoViewModel)
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun showBottomSheetCalendar() {
        val bottomSheetCalendar = layoutInflater.inflate(R.layout.view_bottom_sheet_calendar, null)
        val dialog = BottomSheetDialog(this.requireContext())
        dialog.setContentView(bottomSheetCalendar)
        val calendarView = bottomSheetCalendar.findViewById<CalendarView>(R.id.calendarView)
        // Disable past dates
        calendarView.minDate = System.currentTimeMillis() - 1000

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            date = calendar.time

            setDateToTodoDate(calendar, year, month, dayOfMonth)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun setDateToTodoDate(
        calendar: Calendar,
        year: Int,
        month: Int,
        dayOfMonth: Int
    ) {
        binding.todoDate.text = when {
            DateUtils.isToday(calendar.timeInMillis) -> "Today"
            DateUtils.isToday(calendar.timeInMillis - DateUtils.DAY_IN_MILLIS) -> "Tomorrow"
            else -> dateFormatter(year, month, dayOfMonth)
        }
    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }

    private fun dateFormatter(year: Int, month: Int, dayOfMonth: Int): String {
        return year.toString() + "/" + (month + 1) + "/" + dayOfMonth
    }

    private fun saveTodoInDatabase(todoViewModel: TodoViewModel) {
        val todo: String = binding.todoInput.text.toString()
        val todoEntity = TodoEntity(todo, date, false)
        todoViewModel.insertTodo(todoEntity)
    }

    companion object {
        private val TAG = AddTodoFragment::class.simpleName
    }
}
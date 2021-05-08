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
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import xone.com.todolist.R
import xone.com.todolist.database.entity.TodoEntity
import xone.com.todolist.databinding.FragmentAddtodoBinding
import xone.com.todolist.viewmodel.AddTodoViewModel
import java.util.*

class AddTodoFragment : Fragment() {

    private var _binding: FragmentAddtodoBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AddTodoViewModel

    private val calendar = Calendar.getInstance()
    private var date: Date = calendar.time

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddtodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arguments = AddTodoFragmentArgs.fromBundle(requireArguments())
        setUpEditTodoEntity(arguments.todoEntity)
        setUp(arguments.todoEntity)

        viewModel = ViewModelProvider(this).get(AddTodoViewModel::class.java)
    }

    private fun setUp(todoEntity: TodoEntity?) {
        setDate(todoEntity)

        binding.dateClick.setOnClickListener {
            showBottomSheetCalendar()
        }

        binding.todoInput.afterTextChanged {
            binding.saveTodo.isEnabled = it.trim() != ""
        }

        binding.saveTodo.setOnClickListener {
            if (todoEntity == null) {
                insertTodo()
            } else {
                updateTodo(todoEntity)
            }
            findNavController().navigate(AddTodoFragmentDirections.actionAddTodoFragmentToTodoListFragment())
        }
    }

    private fun showBottomSheetCalendar() {
        val bottomSheetCalendar = layoutInflater.inflate(R.layout.view_bottom_sheet_calendar, null)
        val dialog = BottomSheetDialog(requireContext())
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
        calendar: Calendar, year: Int, month: Int, dayOfMonth: Int
    ) {
        binding.todoDate.text = when {
            DateUtils.isToday(calendar.timeInMillis) -> "Today"
            DateUtils.isToday(calendar.timeInMillis - DateUtils.DAY_IN_MILLIS) -> "Tomorrow"
            else -> dateFormatter(year, month, dayOfMonth)
        }
    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }

    private fun setUpEditTodoEntity(todoEntity: TodoEntity?) {
        binding.todoInput.setText(todoEntity?.todo)
        binding.todoDate.text = todoEntity?.date.toString()
    }

    private fun dateFormatter(year: Int, month: Int, dayOfMonth: Int): String {
        return year.toString() + "/" + (month + 1) + "/" + dayOfMonth
    }

    private fun setDate(todoEntity: TodoEntity?) {
        if (todoEntity != null) {
            calendar.time = todoEntity.date
        }
        setDateToTodoDate(
            calendar,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    private fun updateTodo(todoEntity: TodoEntity) {
        val todo: String = binding.todoInput.text.toString()
        setDate(todoEntity)
        viewModel.updateTodo(todo = TodoEntity(todoEntity.todoId, todo, date, todoEntity.isDone))
    }

    private fun insertTodo() {
        val todo: String = binding.todoInput.text.toString()
        val todoEntity = TodoEntity(todo, date, false)
        viewModel.insertTodo(todoEntity)
    }

    companion object {
        private val TAG = AddTodoFragment::class.simpleName
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
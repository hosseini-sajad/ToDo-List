package xone.com.todolist.ui

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import xone.com.todolist.R
import xone.com.todolist.databinding.FragmentAddtodoBinding
import java.util.*

class AddTodoFragment : Fragment() {

    private lateinit var binding: FragmentAddtodoBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_addtodo, container, false)

        binding.dateClick.setOnClickListener {
            showBottomSheetCalendar()
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
            val calendar = Calendar.getInstance()
            Log.d(TAG, "showBottomSheetCalendar : first: " + calendar.time)
            calendar.set(year, month, dayOfMonth)
            Log.d(TAG, "showBottomSheetCalendar : second: " + calendar.time)

            binding.todoDate.text = when {
                DateUtils.isToday(calendar.timeInMillis) -> "Today"
                DateUtils.isToday(calendar.timeInMillis - DateUtils.DAY_IN_MILLIS) -> "Tomorrow"
                else -> dateFormatter(year, month, dayOfMonth)
            }
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun dateFormatter(year: Int, month: Int, dayOfMonth: Int): String {
        return year.toString() + "/" + (month + 1) + "/" + dayOfMonth
    }

    companion object {
        private val TAG = AddTodoFragment::class.simpleName
    }
}
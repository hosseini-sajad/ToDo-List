package xone.com.todolist.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import xone.com.todolist.R
import xone.com.todolist.databinding.FragmentTodolistBinding
import xone.com.todolist.viewmodel.TodoViewModel
import java.util.*


class TodoListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentTodolistBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_todolist, container, false)

        binding.viewRoot.setOnClickListener {
            findNavController().navigate(TodoListFragmentDirections.actionTodoListFragmentToAddTodoFragment())
        }

        val calendar = Calendar.getInstance()
        val date = calendar.time

        val todoViewModel = ViewModelProvider(
            this, TodoViewModel.Factory(
                requireNotNull(this.activity).application
                //date
            )
        ).get(TodoViewModel::class.java)

        allTodosBefor(todoViewModel)

        return binding.root
    }

    private fun allTodosBefor(todoViewModel: TodoViewModel) {
        todoViewModel.allTodosBefor().observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                Log.d(TAG, "onCreateView: todoList>>>>" + it.size)
            }
        })
    }

    companion object {
        private val TAG = TodoListFragment::class.simpleName
    }
}
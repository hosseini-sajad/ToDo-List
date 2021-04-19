package xone.com.todolist.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import xone.com.todolist.adapter.TodoAdapter
import xone.com.todolist.databinding.FragmentTodolistBinding
import xone.com.todolist.viewmodel.TodoViewModel
import java.util.*


class TodoListFragment : Fragment() {

    private var _binding: FragmentTodolistBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodolistBinding.inflate(inflater, container, false)

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

        val recyclerView = binding.todoListRecycler
        recyclerView.layoutManager = LinearLayoutManager(context)
        val todoAdapter = TodoAdapter()
        recyclerView.adapter = todoAdapter

        allTodosBefor(todoViewModel, todoAdapter)

        return binding.root
    }

    private fun allTodosBefor(
        todoViewModel: TodoViewModel,
        todoAdapter: TodoAdapter
    ) {
        todoViewModel.allTodosBefor().observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d(TAG, "onCreateView: todoList>>>>" + it.size)
                todoAdapter.submitList(it)
            }
        })
    }

    companion object {
        private val TAG = TodoListFragment::class.simpleName
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
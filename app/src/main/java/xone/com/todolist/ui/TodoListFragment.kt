package xone.com.todolist.ui

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import xone.com.todolist.R
import xone.com.todolist.adapter.TodoAdapter
import xone.com.todolist.database.entity.TodoEntity
import xone.com.todolist.databinding.FragmentTodolistBinding
import xone.com.todolist.viewmodel.TodoViewModel


class TodoListFragment : Fragment() {

    private var _binding: FragmentTodolistBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TodoViewModel

    private val todoAdapter = setUpTodoAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodolistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        viewModel = ViewModelProvider(requireActivity()).get(TodoViewModel::class.java)

        allTodosBefor(viewModel, todoAdapter)
    }

    private fun setUpRecyclerView() {
        binding.todoListRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = todoAdapter
            alwaysRecyclerViewPositionTop(todoAdapter, this)
        }

        binding.viewRoot.setOnClickListener {
            findNavController().navigate(TodoListFragmentDirections.actionTodoListFragmentToAddTodoFragment())
        }
    }

    private fun allTodosBefor(todoViewModel: TodoViewModel, todoAdapter: TodoAdapter) {
        todoViewModel.allTodosBefor().observe(viewLifecycleOwner, {
            it?.let {
                Log.d(TAG, "allTodosBefor: ${it.size}")
                todoAdapter.data = it
            }
        })
    }

    private fun setUpTodoAdapter() = TodoAdapter { todoEntity, todoTitle ->
        if (!todoEntity.isDone) {
            todoEntity.isDone = true
            updateTodoInDatabase(todoEntity)
            todoTitle.setTextColor(
                ContextCompat.getColor(
                    todoTitle.context,
                    R.color.text_hint_color
                )
            )
            todoTitle.paintFlags = todoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            todoEntity.isDone = false
            updateTodoInDatabase(todoEntity)
            todoTitle.setTextColor(
                ContextCompat.getColor(
                    todoTitle.context,
                    R.color.text_primary_color
                )
            )
            todoTitle.paintFlags = todoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    private fun updateTodoInDatabase(todoEntity: TodoEntity) {
        if (todoEntity.isDone) {
            viewModel.updateTodo(
                TodoEntity(
                    todoEntity.todoId,
                    todoEntity.todo,
                    todoEntity.date,
                    todoEntity.isDone
                )
            )
        } else {
            viewModel.updateTodo(
                TodoEntity(
                    todoEntity.todoId,
                    todoEntity.todo,
                    todoEntity.date,
                    todoEntity.isDone
                )
            )
        }
    }

    private fun alwaysRecyclerViewPositionTop(adapter: TodoAdapter, recyclerView: RecyclerView) {
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                recyclerView.scrollToPosition(0)
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                recyclerView.scrollToPosition(0)
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
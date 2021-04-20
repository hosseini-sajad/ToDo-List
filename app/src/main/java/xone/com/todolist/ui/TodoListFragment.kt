package xone.com.todolist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import xone.com.todolist.adapter.TodoAdapter
import xone.com.todolist.databinding.FragmentTodolistBinding
import xone.com.todolist.viewmodel.TodoViewModel


class TodoListFragment : Fragment() {

    private var _binding: FragmentTodolistBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TodoViewModel

    private val todoAdapter = TodoAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodolistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp()

        viewModel = ViewModelProvider(requireActivity()).get(TodoViewModel::class.java)

        allTodosBefor(viewModel, todoAdapter)
    }

    private fun setUp() {
        binding.todoListRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = todoAdapter
        }

        binding.viewRoot.setOnClickListener {
            findNavController().navigate(TodoListFragmentDirections.actionTodoListFragmentToAddTodoFragment())
        }
    }

    private fun allTodosBefor(todoViewModel: TodoViewModel, todoAdapter: TodoAdapter) {
        todoViewModel.allTodosBefor().observe(viewLifecycleOwner, {
            it?.let {
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
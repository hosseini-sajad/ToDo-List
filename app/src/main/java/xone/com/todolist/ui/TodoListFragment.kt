package xone.com.todolist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import xone.com.todolist.adapter.TodoAdapter
import xone.com.todolist.component.view.BottomSheetView
import xone.com.todolist.database.entity.BottomSheetEntity
import xone.com.todolist.database.entity.TodoEntity
import xone.com.todolist.databinding.FragmentTodolistBinding
import xone.com.todolist.viewmodel.TodoViewModel


class TodoListFragment : Fragment() {

    private var _binding: FragmentTodolistBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TodoViewModel
    private var todoSheet: BottomSheetView? = null
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

        viewModel.apply {
            todoSheetObservable.observe(viewLifecycleOwner, ::observeTodoSheet)
            editTodoEntity.observe(viewLifecycleOwner, ::editTodoEntity)
            allTodosBefor().observe(viewLifecycleOwner, ::allTodosBefor)
        }

        binding.viewRoot.setOnClickListener {
            findNavController().navigate(TodoListFragmentDirections.actionTodoListFragmentToAddTodoFragment(null))
        }
    }

    private fun setUpRecyclerView() {
        binding.todoListRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = todoAdapter
            alwaysRecyclerViewPositionTop(todoAdapter, this)
        }
    }

    private fun setUpTodoAdapter() = TodoAdapter({ todoEntity ->
        viewModel.onClickListener(todoEntity)
    }, { todoEntity: TodoEntity ->
        viewModel.onTodoLongClick(todoEntity)
    })

    private fun allTodosBefor(todoEntity: List<TodoEntity>) {
        todoAdapter.data = todoEntity
    }

    private fun observeTodoSheet(entity: BottomSheetEntity) {
        todoSheet?.apply {
            sheetItems = entity.items
            sheetTitle = entity.sheetTitle
            show()
        } ?: run {
            todoSheet = BottomSheetView(requireContext())
        }
    }

    private fun editTodoEntity(isEdit: Boolean) {
        if (isEdit) {
            viewModel.navigationObservable.observe(viewLifecycleOwner, { todoEntity ->
                findNavController().navigate(TodoListFragmentDirections.actionTodoListFragmentToAddTodoFragment(todoEntity))
            })

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
package xone.com.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import xone.com.todolist.databinding.FragmentAddtodoBinding

class AddTodoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding : FragmentAddtodoBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_addtodo, container, false)

        return binding.root
    }
}
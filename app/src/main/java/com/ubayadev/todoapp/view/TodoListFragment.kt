package com.ubayadev.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubayadev.todoapp.R
import com.ubayadev.todoapp.databinding.FragmentTodoListBinding
import com.ubayadev.todoapp.model.Todo
import com.ubayadev.todoapp.viewmodel.ListTodoViewModel

class TodoListFragment : Fragment(), TodoItemListener {
    private lateinit var binding: FragmentTodoListBinding
    private var adapter: TodoListAdapter = TodoListAdapter(arrayListOf(), this)
    private lateinit var viewmodel: ListTodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // initialize viewmodel
        viewmodel = ViewModelProvider(this)[ListTodoViewModel::class.java]
        viewmodel.refresh()
        observeViewModel()

        // init recycler view
        binding.recViewTodo.layoutManager = LinearLayoutManager(context)
        binding.recViewTodo.adapter = adapter

        binding.btnFab.setOnClickListener {
            val action = TodoListFragmentDirections.actionCreateTodo()
            it.findNavController().navigate(action)
        }
    }

    fun observeViewModel() {
        // dengerin listtodo live data
        viewmodel.todoLD.observe(viewLifecycleOwner, Observer {
            // mengupdate adapter agar isi recycler view muncul/diperbaharui
            adapter.updateTodoList(it)
            if(it.isEmpty()) {
                binding.recViewTodo.visibility = View.GONE
                binding.txtError.visibility = View.VISIBLE
                binding.txtError.text = "Your todo still empty."
            } else {
                binding.recViewTodo.visibility = View.VISIBLE
            }
        })

        // dengerin kondisi progress bar
        viewmodel.loadingLD.observe(viewLifecycleOwner, Observer {
            if(it == false) {
                binding.progressBar.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.VISIBLE
            }
        })

        // dengerin kondisi error
        viewmodel.errorLD.observe(viewLifecycleOwner, Observer {
            if(it == false) {
                binding.txtError.visibility = View.GONE
            } else {
                binding.txtError.visibility = View.VISIBLE
                binding.txtError.text = "Unable to show todo"
            }
        })
    }

    override fun onTodoCheck(todo: Todo) {
        viewmodel.clearTask(todo)
    }
}
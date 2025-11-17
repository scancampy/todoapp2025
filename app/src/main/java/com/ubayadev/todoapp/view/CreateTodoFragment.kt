package com.ubayadev.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ubayadev.todoapp.R
import com.ubayadev.todoapp.databinding.FragmentCreateTodoBinding
import com.ubayadev.todoapp.model.Todo
import com.ubayadev.todoapp.viewmodel.DetailViewModel


class CreateTodoFragment : Fragment() {
    private lateinit var binding: FragmentCreateTodoBinding
    private lateinit var viewmodel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // initialize view model
        viewmodel = ViewModelProvider(this)[DetailViewModel::class.java]

        binding.btnAdd.setOnClickListener {
            val title = binding.txtTitle.text.toString()
            val notes =  binding.txtNotes.text.toString()

            // cek radio group
            /*if(binding.radioGroupPriority.checkedRadioButtonId == R.id.radioHigh){
                priority = 3
            } else if(binding.radioGroupPriority.checkedRadioButtonId == R.id.radioMedium){
                priority = 2
            } else {
                priority = 1
            }*/

            var priority = 0
            val radio = view.findViewById<RadioButton>(
                        binding.radioGroupPriority.checkedRadioButtonId)
            priority = radio.tag.toString().toInt()

            val todo = Todo(title,notes, priority)
            viewmodel.addTodo(todo)
            Snackbar.make(it, "Todo created", Snackbar.LENGTH_SHORT).show()
            it.findNavController().popBackStack()
        }
    }
}
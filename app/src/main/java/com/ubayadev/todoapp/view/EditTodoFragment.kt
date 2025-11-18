package com.ubayadev.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ubayadev.todoapp.R
import com.ubayadev.todoapp.databinding.FragmentCreateTodoBinding
import com.ubayadev.todoapp.model.Todo
import com.ubayadev.todoapp.viewmodel.DetailViewModel


class EditTodoFragment : Fragment() {
    private lateinit var binding: FragmentCreateTodoBinding
    private lateinit var viewmodel: DetailViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // init viewmodel
        viewmodel = ViewModelProvider(this).get(DetailViewModel::class.java)
        binding.txtPageTitle.text = "Edit Todo"
        binding.btnAdd.text = "Save Todo"

        // baca uuid
        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
        viewmodel.fetch(uuid)
        observeViewModel()

        // simpan todo
        binding.btnAdd.setOnClickListener {
            var priority = 0
            val radio = view.findViewById<RadioButton>(
                binding.radioGroupPriority.checkedRadioButtonId)
            priority = radio.tag.toString().toInt()

            val todo = Todo(
                binding.txtTitle.text.toString(),
                binding.txtNotes.text.toString(),
                priority
            )
            todo.uuid = uuid
            viewmodel.update(todo)
            Snackbar.make(it, "Todo updated", Snackbar.LENGTH_SHORT).show()
            it.findNavController().popBackStack()
        }
    }

    fun observeViewModel() {
        viewmodel.todoLD.observe(viewLifecycleOwner, Observer {
            // update UI
            binding.txtTitle.setText(it.title)
            binding.txtNotes.setText(it.notes)
            when(it.priority) {
                1 -> binding.radioLow.isChecked = true
                2 -> binding.radioMedium.isChecked = true
                else -> binding.radioHigh.isChecked = true
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateTodoBinding.inflate(inflater, container,false)
        return  binding.root
    }
}
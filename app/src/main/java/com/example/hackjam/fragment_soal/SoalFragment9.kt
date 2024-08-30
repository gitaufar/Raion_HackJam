package com.example.hackjam.fragment_soal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.hackjam.QuestionActiviyViewModel
import com.example.hackjam.R
class SoalFragment9 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_soal9, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity()).get(QuestionActiviyViewModel::class.java)
        val button1: AppCompatButton = view.findViewById(R.id.tidak_pernah)
        val button2: AppCompatButton = view.findViewById(R.id.jarang)
        val button3: AppCompatButton = view.findViewById(R.id.pernah)

        button1.setOnClickListener(){
            viewModel.updateListAnswer(8,arrayOf(1,0,0))
            button1.setBackgroundResource(R.drawable.answer_selected)
            button1.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            button2.setBackgroundResource(R.drawable.question__btn_bg)
            button2.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            button3.setBackgroundResource(R.drawable.question__btn_bg)
            button3.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }

        button2.setOnClickListener(){
            viewModel.updateListAnswer(8,arrayOf(0,1,0))
            button2.setBackgroundResource(R.drawable.answer_selected)
            button2.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            button1.setBackgroundResource(R.drawable.question__btn_bg)
            button1.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            button3.setBackgroundResource(R.drawable.question__btn_bg)
            button3.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }

        button3.setOnClickListener(){
            viewModel.updateListAnswer(8,arrayOf(0,0,1))
            button3.setBackgroundResource(R.drawable.answer_selected)
            button3.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            button2.setBackgroundResource(R.drawable.question__btn_bg)
            button2.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            button1.setBackgroundResource(R.drawable.question__btn_bg)
            button1.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }

        val listButton = arrayOf(button1,button2,button3)
        viewModel.getAnswerAt(8).observe(viewLifecycleOwner) { answer ->
            for (i in 0..2) {
                update_button(answer[i], listButton[i])
            }
        }

    }

    fun update_button(bool: Int,button: AppCompatButton){
        if(bool == 0){
            button.setBackgroundResource(R.drawable.question__btn_bg)
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            return
        }

        button.setBackgroundResource(R.drawable.answer_selected)
        button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

    }
}
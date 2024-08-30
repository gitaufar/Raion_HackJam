package com.example.hackjam.fragment_soal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.hackjam.QuestionActiviyViewModel
import com.example.hackjam.R

class SoalFragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_soal2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity()).get(QuestionActiviyViewModel::class.java)
        val button1: FrameLayout = view.findViewById(R.id.tidak)
        val button2: FrameLayout = view.findViewById(R.id.mungkin)
        val button3: FrameLayout = view.findViewById(R.id.iya)
        val text1: TextView = view.findViewById(R.id.text_tidak)
        val text2: TextView = view.findViewById(R.id.text_mungkin)
        val text3: TextView = view.findViewById(R.id.text_iya)

        button1.setOnClickListener(){
            viewModel.updateListAnswer(1,arrayOf(1,0,0))
            button1.setBackgroundResource(R.drawable.emoji_bg_selected)
            button2.setBackgroundResource(R.drawable.emoji_bg)
            button3.setBackgroundResource(R.drawable.emoji_bg)
        }

        button2.setOnClickListener(){
            viewModel.updateListAnswer(1,arrayOf(0,1,0))
            button2.setBackgroundResource(R.drawable.emoji_bg_selected)
            button1.setBackgroundResource(R.drawable.emoji_bg)
            button3.setBackgroundResource(R.drawable.emoji_bg)
        }

        button3.setOnClickListener(){
            viewModel.updateListAnswer(1,arrayOf(0,0,1))
            button3.setBackgroundResource(R.drawable.emoji_bg_selected)
            button2.setBackgroundResource(R.drawable.emoji_bg)
            button1.setBackgroundResource(R.drawable.emoji_bg)
        }

        val listButton = arrayOf(button1,button2,button3)
        val listText = arrayOf(text1,text2,text3)
        viewModel.getAnswerAt(1).observe(viewLifecycleOwner) { answer ->
            for (i in 0..2) {
                update_button(answer[i], listButton[i], listText[i])
            }
        }

    }

    fun update_button(bool: Int,button: FrameLayout,text: TextView){
        if(bool == 0){
            button.setBackgroundResource(R.drawable.emoji_bg)
            return
        }

        button.setBackgroundResource(R.drawable.emoji_bg_selected)

    }

}
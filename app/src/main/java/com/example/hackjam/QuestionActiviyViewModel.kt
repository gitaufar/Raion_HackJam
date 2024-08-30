package com.example.hackjam

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuestionActiviyViewModel : ViewModel() {

    private val _question = MutableLiveData<Int>().apply { value = 1 }
    val question: LiveData<Int> get() = _question

    // Menggunakan MutableLiveData untuk listAnswer
    private val _listAnswer = MutableLiveData<Array<Array<Int>>>().apply {
        value = arrayOf(
            arrayOf(0, 0, 0),
            arrayOf(0, 0, 0),
            arrayOf(0, 0, 0),
            arrayOf(0, 0, 0),
            arrayOf(0, 0, 0),
            arrayOf(0, 0, 0),
            arrayOf(0, 0, 0),
            arrayOf(0, 0, 0),
            arrayOf(0, 0, 0),
            arrayOf(0, 0, 0)
        )
    }
    val listAnswer: LiveData<Array<Array<Int>>> get() = _listAnswer

    // Fungsi untuk mengupdate nilai question
    fun updateQuestion(newQuestion: Int) {
        _question.value = newQuestion
    }

    // Fungsi untuk mengupdate listAnswer
    fun updateListAnswer(index: Int, answers: Array<Int>) {
        _listAnswer.value?.let {
            it[index] = answers
            _listAnswer.value = it
        }
    }
    fun getAnswerAt(index: Int): LiveData<Array<Int>> {
        val result = MutableLiveData<Array<Int>>()
        result.value = _listAnswer.value?.get(index)
        return result
    }

    fun incrementQuestion() {
        _question.value = (_question.value ?: 0) + 1
    }

    fun decrementQuestion() {
        _question.value = (_question.value ?: 0) - 1
    }

    fun getQuestion(): Int {
        return _question.value ?: 0
    }

    fun resetAnswer(index: Int){
        _listAnswer.value?.let {
            it[index] = arrayOf(0,0,0)
            _listAnswer.value = it
        }
    }

    fun countPoint(): Int {
        var result = 0
        _listAnswer.value?.forEach { answers ->
            if (answers.getOrNull(1) == 1) {
                result += 20
            } else if (answers.getOrNull(2) == 1) {
                result += 40
            }
        }
        return result
    }

}
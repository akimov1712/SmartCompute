package com.example.jetpack.presentation

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jetpack.data.GameRepositoryImpl
import com.example.jetpack.domain.entity.GameResult
import com.example.jetpack.domain.entity.GameSettings
import com.example.jetpack.domain.entity.Level
import com.example.jetpack.domain.entity.Question
import com.example.jetpack.domain.useCases.GenerateQuestionsUseCase
import com.example.jetpack.domain.useCases.GetGameSettingsUseCase

class GameViewModel: ViewModel() {

    private lateinit var level: Level
    private lateinit var gameSettings: GameSettings
    private var timer: CountDownTimer? = null

    private var countRightAnswers = 0
    private var countQuestions = 0

    private val repository = GameRepositoryImpl

    private val generateQuestionsUseCase = GenerateQuestionsUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private val _countRight = MutableLiveData<String>()
    val countRight: LiveData<String>
        get() = _countRight

    private val _countWrong = MutableLiveData<String>()
    val countWrong: LiveData<String>
        get() = _countWrong

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
    get() = _formattedTime

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _percentRightAnswers = MutableLiveData<Int>()
    val percentRightAnswers: LiveData<Int>
    get() = _percentRightAnswers

    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean>
        get() = _enoughCount

    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughPercent

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    fun startGame(level: Level){
        getGameSettings(level)
        startTimer()
        generateQuestions()
    }

    fun chooseAnswer(number: Int){
        checkAnswer(number)
        updateProgress()
        generateQuestions()
    }

    private fun updateProgress(){
        val percent = calculatePercentOfRightAnswers()
        _percentRightAnswers.value = percent
        _enoughCount.value = countRightAnswers >= gameSettings.minCount
        _enoughPercent.value = percent >= gameSettings.minPercent
    }

    private fun calculatePercentOfRightAnswers():Int{
        return ((countRightAnswers / countQuestions.toDouble()) * 100).toInt()
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) {
            countRightAnswers++
        }
        countQuestions++
        _countRight.value = countRightAnswers.toString()
        _countWrong.value = (countQuestions - countRightAnswers).toString()
    }

    private fun generateQuestions(){
        _question.value = generateQuestionsUseCase(gameSettings.maxSumValue)
    }

    private fun getGameSettings(level: Level) {
        this.level = level
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minPercent
    }

    private fun startTimer(){
        timer = object : CountDownTimer(
            gameSettings.gameTimeSecond * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ){
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun formatTime(millisUntilFinished: Long): String{
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - minutes * SECONDS_IN_MINUTES
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun finishGame(){
        _gameResult.value = GameResult(
            enoughCount.value == true && enoughPercent.value == true,
            countRightAnswers,
            countQuestions,
            gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object{

        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60

    }

}
package com.example.jetpack.data

import com.example.jetpack.domain.entity.GameSettings
import com.example.jetpack.domain.entity.Level
import com.example.jetpack.domain.entity.Question
import com.example.jetpack.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl: GameRepository {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_VISIBLE_NUMBER = 1

    override fun generateQuestion(maxSumValue: Int, countOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_VISIBLE_NUMBER, sum)
        val options = HashSet<Int>()
        val rightNumber = sum - visibleNumber
        options.add(rightNumber)
        val from = max(rightNumber - countOptions, MIN_VISIBLE_NUMBER)
        val to = min(maxSumValue, rightNumber + countOptions)
        while (options.size < countOptions){
            options.add(Random.nextInt(from,to))
        }
        return Question(sum,visibleNumber, options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when(level){
            Level.TEST -> GameSettings(10, 3, 50, 10)
            Level.EASY -> GameSettings(30, 5, 50, 30)
            Level.NORMAL -> GameSettings(80, 10, 70, 45)
            Level.HARD -> GameSettings(280, 20, 95, 60)
        }
    }
}
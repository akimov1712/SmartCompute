package com.example.jetpack.domain.repository

import com.example.jetpack.domain.entity.GameSettings
import com.example.jetpack.domain.entity.Level
import com.example.jetpack.domain.entity.Question

interface GameRepository {

    fun generateQuestion(maxSumValue: Int, countOptions: Int): Question

    fun getGameSettings(level: Level): GameSettings

}
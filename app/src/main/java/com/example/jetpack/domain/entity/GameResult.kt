package com.example.jetpack.domain.entity

import java.io.Serializable

data class GameResult(
    val winner: Boolean,
    val countRightAnswers: Int,
    val countQuestions: Int,
    val gameSettings: GameSettings
): Serializable
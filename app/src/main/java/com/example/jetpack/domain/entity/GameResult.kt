package com.example.jetpack.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(
    val winner: Boolean,
    val countRightAnswers: Int,
    val countQuestions: Int,
    val gameSettings: GameSettings
): Parcelable
package com.example.jetpack.domain.entity

import java.io.Serializable

data class GameSettings (
    val maxSumValue: Int,
    val minCount: Int,
    val minPercents: Int,
    val gameTimeSecond: Int
): Serializable
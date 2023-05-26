package com.example.jetpack.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameSettings (
    val maxSumValue: Int,
    val minCount: Int,
    val minPercent: Int,
    val gameTimeSecond: Int
): Parcelable
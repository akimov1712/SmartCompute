package com.example.jetpack.domain.useCases

import com.example.jetpack.domain.entity.Question
import com.example.jetpack.domain.repository.GameRepository

class GenerateQuestionsUseCase(private val repository: GameRepository) {
    operator fun invoke(maxSumValue: Int): Question{
        return repository.generateQuestion(maxSumValue, COUNT_OPTIONS)
    }

    private companion object{
        private const val COUNT_OPTIONS = 6
    }
}
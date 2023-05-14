package com.example.jetpack.domain.useCases

import com.example.jetpack.domain.entity.GameSettings
import com.example.jetpack.domain.entity.Level
import com.example.jetpack.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }

}
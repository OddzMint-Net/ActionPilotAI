package com.oddzmint.actionpilotai.domain.repository

import com.oddzmint.actionpilotai.domain.model.AIAction

interface AIActionRepository {
    suspend fun getAction(userInput: String): AIAction
}
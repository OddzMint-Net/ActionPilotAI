package com.oddzmint.actionpilotai.domain

interface AIActionService {
    suspend fun getAction(userInput: String): String
}
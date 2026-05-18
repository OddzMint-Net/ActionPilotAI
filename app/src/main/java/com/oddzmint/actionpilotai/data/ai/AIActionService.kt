package com.oddzmint.actionpilotai.data.ai

fun interface AIActionService {
    suspend fun getAction(userInput: String): String
}
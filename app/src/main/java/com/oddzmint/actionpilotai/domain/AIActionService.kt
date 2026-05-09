package com.oddzmint.actionpilotai.domain

fun interface AIActionService {
    suspend fun getAction(userInput: String): String
}
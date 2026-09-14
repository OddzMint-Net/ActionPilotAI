package com.oddzmint.actionpilotai.domain.model

sealed class ActionResult {
    data object Success : ActionResult()
    sealed class Failure : ActionResult() {
        data class MissingData(val field: String) : Failure()
        data object NoHandlerApp : Failure()
        data class Unexpected(val cause: String) : Failure()
    }
}
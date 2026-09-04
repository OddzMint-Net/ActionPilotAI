package com.oddzmint.actionpilotai.domain.extensions

import com.oddzmint.actionpilotai.domain.model.ActionType

fun ActionType.requiresConfirmation(): Boolean {
    return when (this) {
        ActionType.SEARCH_WEB,
        ActionType.OPEN_URL,
        ActionType.OPEN_MAPS,
        ActionType.DIAL_PHONE -> false

        else -> true
    }
}
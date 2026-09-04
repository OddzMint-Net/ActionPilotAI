package com.oddzmint.actionpilotai.presentation.designsystem.styles

import androidx.compose.ui.graphics.Color
import com.oddzmint.actionpilotai.presentation.designsystem.theme.ActionPilotColors

data class ButtonStyle(
    val containerColor: Color,
    val contentColor: Color
)

object ButtonStyles {
    val Primary = ButtonStyle(
        containerColor = ActionPilotColors.Primary,
        contentColor = ActionPilotColors.Background
    )

    val Disabled = ButtonStyle(
        containerColor = ActionPilotColors.Surface,
        contentColor = ActionPilotColors.Secondary
    )
}
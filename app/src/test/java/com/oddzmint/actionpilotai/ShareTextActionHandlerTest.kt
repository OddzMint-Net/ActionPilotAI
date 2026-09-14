package com.oddzmint.actionpilotai

import android.content.Context
import com.oddzmint.actionpilotai.data.actions.IntentLauncher
import com.oddzmint.actionpilotai.data.actions.ShareTextActionHandler
import com.oddzmint.actionpilotai.domain.model.ActionType
import com.oddzmint.actionpilotai.domain.model.AIAction
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class ShareTextActionHandlerTest {
    private lateinit var intentLauncher: IntentLauncher
    private lateinit var handler: ShareTextActionHandler

    @Before
    fun setup() {
        intentLauncher = mockk(relaxed = true)
        handler = ShareTextActionHandler(intentLauncher)
    }

    @Test
    fun `share text is SHARE_TEXT`() {
        assertEquals(
            ActionType.SHARE_TEXT,
            handler.type
        )
    }

    @Test
    fun `execute launches dialog and shares a text`() {
        val action = AIAction(
            type = ActionType.SHARE_TEXT,
            data = mapOf("text" to "share to whatsapp")
        )
        handler.execute(action)
        verify { intentLauncher.launch(any())}
    }

    @Test
    fun `execute does not share text when query is blank`() {
        val action = AIAction(
            type = ActionType.SHARE_TEXT,
            data = mapOf("query" to "")
        )
        try {
            handler.execute(action)
        } catch (_: Exception) {
        }

        verify(exactly = 0) { intentLauncher.launch(any())}
    }
}
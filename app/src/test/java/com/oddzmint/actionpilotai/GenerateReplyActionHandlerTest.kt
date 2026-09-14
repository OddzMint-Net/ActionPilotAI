package com.oddzmint.actionpilotai

import android.content.ClipboardManager
import com.oddzmint.actionpilotai.data.actions.GenerateReplyActionHandler
import com.oddzmint.actionpilotai.domain.model.ActionType
import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.domain.model.ActionResult
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GenerateReplyActionHandlerTest {

    private lateinit var clipboardManager: ClipboardManager
    private lateinit var handler: GenerateReplyActionHandler

    @Before
    fun setup() {
        clipboardManager = mockk(relaxed = true)
        handler = GenerateReplyActionHandler(clipboardManager)
    }

    @Test
    fun `execute copies reply to clipboard when message exists`() {
        val action = AIAction(
            type = ActionType.GENERATE_REPLY,
            data = mapOf("message" to "hello message")
        )
            handler.execute(action)
        verify(exactly = 1) { clipboardManager.setPrimaryClip(any()) }
    }

    @Test
    fun `execute does not copy to clipboard when message is blank`() {
        val action = AIAction(
            type = ActionType.GENERATE_REPLY,
            data = mapOf("message" to "")
        )
        val result = handler.execute(action)

        assertTrue(result is ActionResult.Failure.MissingData)
        verify(exactly = 0) { clipboardManager.setPrimaryClip(any()) }
    }
}
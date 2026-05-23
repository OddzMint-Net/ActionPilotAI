package com.oddzmint.actionpilotai

import android.content.Context
import com.oddzmint.actionpilotai.data.actions.ShareTextActionHandler
import com.oddzmint.actionpilotai.domain.action.ActionType
import com.oddzmint.actionpilotai.domain.model.AIAction
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class ShareTextActionHandlerTest {
    private lateinit var handler: ShareTextActionHandler

    @Before
    fun setup() {
        handler = ShareTextActionHandler()
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
        val context = mockk<Context>(relaxed = true)
        val action = AIAction(
            type = ActionType.SHARE_TEXT,
            data = mapOf("text" to "share to whatsapp")
        )
        handler.execute(context, action)
        verify { context.startActivity(any()) }
    }

    @Test
    fun `execute does not share text when query is blank`() {
        val context = mockk<Context>(relaxed = true)
        val action = AIAction(
            type = ActionType.SHARE_TEXT,
            data = mapOf("query" to "")
        )
        try {
            handler.execute(context, action)
        } catch (_: Exception) {
        }

        verify(exactly = 0) { context.startActivity(any()) }
    }
}
package com.oddzmint.actionpilotai

import android.content.ClipboardManager
import android.content.Context
import com.oddzmint.actionpilotai.data.actions.GenerateReplyActionHandler
import com.oddzmint.actionpilotai.domain.action.ActionType
import android.content.ClipData
import com.oddzmint.actionpilotai.domain.model.AIAction
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class GenerateReplyActionHandlerTest {
    private lateinit var handler: GenerateReplyActionHandler

    @Before
    fun setup() {
        handler = GenerateReplyActionHandler()
    }

    @Test
    fun `execute generate reply when query exist`() {
        val clipboard = mockk<ClipboardManager>(relaxed = true)
        val context = mockk<Context>(relaxed = true)

        every {
            context.getSystemService(Context.CLIPBOARD_SERVICE)
        } returns clipboard

        val action = AIAction(
            type = ActionType.GENERATE_REPLY,
            data = mapOf("message" to "hello message")
        )
        try {
            handler.execute(context,action)
        } catch (_:Exception){}

        verify(exactly = 1) { clipboard.setPrimaryClip(any()) }
    }

    @Test
    fun `execute does not generate reply when query is blank`() {
        val clipboard = mockk<ClipboardManager>(relaxed = true)
        val context = mockk<Context>(relaxed = true)

        every {
            context.getSystemService(Context.CLIPBOARD_SERVICE)
        } returns clipboard

        val action = AIAction(
            type = ActionType.GENERATE_REPLY,
            data = mapOf("message" to "")
        )
        try {
            handler.execute(context, action)
        } catch (_: Exception) {

        }
        verify(exactly = 0) { clipboard.setPrimaryClip(any()) }
    }
}
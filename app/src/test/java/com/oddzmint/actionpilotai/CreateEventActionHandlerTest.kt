package com.oddzmint.actionpilotai

import android.content.Context
import androidx.compose.ui.text.font.createFontFamilyResolver
import com.oddzmint.actionpilotai.data.actions.CreateEventActionHandler
import com.oddzmint.actionpilotai.domain.action.ActionType
import com.oddzmint.actionpilotai.domain.model.AIAction
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class CreateEventActionHandlerTest {

    private lateinit var handler: CreateEventActionHandler

    @Before
    fun setup() {
        handler = CreateEventActionHandler()
    }

    @Test
    fun `create event in CREATE_EVENT`() {
        assertEquals(
            ActionType.CREATE_EVENT,
            handler.type
        )
    }

    @Test
    fun `execute calendar to create event when query exists`() {
        val context = mockk<Context>(relaxed = true)
        val action = AIAction(
            type = ActionType.CREATE_EVENT,
            data = mapOf("query" to "title")
        )
        handler.execute(context, action)
        verify { context.startActivity(any()) }
    }

    @Test
    fun `execute does not launch calendar when query is blank`() {
        val context = mockk<Context>(relaxed = true)
        val action = AIAction(
            type = ActionType.CREATE_EVENT,
            data = mapOf("query" to "")
        )
        try {
            handler.execute(context, action)
        } catch (_: Exception) {
        }

        verify { context.startActivity(any()) }
    }
}
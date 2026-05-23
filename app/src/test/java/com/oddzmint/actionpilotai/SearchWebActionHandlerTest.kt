package com.oddzmint.actionpilotai

import android.content.Context
import com.oddzmint.actionpilotai.data.actions.SearchWebActionHandler
import com.oddzmint.actionpilotai.domain.action.ActionType
import com.oddzmint.actionpilotai.domain.model.AIAction
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class SearchWebActionHandlerTest {
    private lateinit var handler: SearchWebActionHandler

    @Before
    fun setup() {
        handler = SearchWebActionHandler()
    }

    @Test
    fun `type is SEARCH_WEB`() {
        assertEquals(
            ActionType.SEARCH_WEB,
            handler.type
        )
    }

    @Test
    fun `execute launches browser when query exists`() {
        val context = mockk<Context>(relaxed = true)
        val action = AIAction(
            type = ActionType.SEARCH_WEB,
            data = mapOf("query" to "android mvi")
        )
        handler.execute(context, action)

        verify { context.startActivity(any()) }
    }

    @Test
    fun `execute does not launch browser when query is blank`() {
        val context = mockk<Context>(relaxed = true)

        val action = AIAction(
            type = ActionType.SEARCH_WEB,
            data = mapOf("query" to "")
        )
        try {
            handler.execute(context, action)
        } catch (_: Exception) {
        }

        verify(exactly = 0) { context.startActivity(any()) }
    }
}
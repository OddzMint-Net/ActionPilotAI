package com.oddzmint.actionpilotai

import android.content.Context
import com.oddzmint.actionpilotai.data.actions.IntentLauncher
import com.oddzmint.actionpilotai.data.actions.SearchWebActionHandler
import com.oddzmint.actionpilotai.domain.model.ActionType
import com.oddzmint.actionpilotai.domain.model.AIAction
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class SearchWebActionHandlerTest {

    private lateinit var intentLauncher: IntentLauncher
    private lateinit var handler: SearchWebActionHandler

    @Before
    fun setup() {
        intentLauncher = mockk(relaxed = true)
        handler = SearchWebActionHandler(intentLauncher)
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
        val action = AIAction(
            type = ActionType.SEARCH_WEB,
            data = mapOf("query" to "android mvi")
        )
        handler.execute(action)

        verify(exactly = 1) { intentLauncher.launch(any()) }
    }

    @Test
    fun `execute does not launch browser when query is blank`() {
        val action = AIAction(
            type = ActionType.SEARCH_WEB,
            data = emptyMap()
        )
            handler.execute(action)

        verify(exactly = 0) { intentLauncher.launch(any()) }
    }
}
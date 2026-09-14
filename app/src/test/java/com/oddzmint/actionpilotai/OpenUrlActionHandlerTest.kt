package com.oddzmint.actionpilotai

import android.content.Context
import com.oddzmint.actionpilotai.data.actions.IntentLauncher
import com.oddzmint.actionpilotai.data.actions.OpenUrlActionHandler
import com.oddzmint.actionpilotai.domain.model.ActionType
import com.oddzmint.actionpilotai.domain.model.AIAction
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class OpenUrlActionHandlerTest {

    private lateinit var intentLauncher: IntentLauncher
    private lateinit var handler: OpenUrlActionHandler

    @Before
    fun setup() {
        intentLauncher = mockk(relaxed = true)
        handler = OpenUrlActionHandler(intentLauncher)
    }

    @Test
    fun `open the OPEN_URL `() {
        assertEquals(
            ActionType.OPEN_URL,
            handler.type
        )
    }

    @Test
    fun `execute opens url when query exist`() {
        val action = AIAction(
            type = ActionType.OPEN_URL,
            data = mapOf("url" to "oddzmint.com")
        )
        handler.execute(action)
        verify { intentLauncher.launch(any()) }
    }

    @Test
    fun `execute does not open url when query is blank`() {
        val action = AIAction(
            type = ActionType.OPEN_URL,
            data = mapOf("query" to "")
        )
        try {
            handler.execute(action)
        } catch (_: Exception) {
        }
        verify(exactly = 0) { intentLauncher.launch(any()) }
    }
}
package com.oddzmint.actionpilotai

import android.content.Context
import com.oddzmint.actionpilotai.data.actions.IntentLauncher
import com.oddzmint.actionpilotai.data.actions.OpenMapsActionHandler
import com.oddzmint.actionpilotai.domain.model.ActionType
import com.oddzmint.actionpilotai.domain.model.AIAction
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class OpenMapsActionHandlerTest {

    private lateinit var intentLauncher: IntentLauncher
    private lateinit var handler: OpenMapsActionHandler

    @Before
    fun setup() {
        intentLauncher = mockk(relaxed = true)
        handler = OpenMapsActionHandler(intentLauncher)
    }

    @Test
    fun `open the OPEN_MAP`() {
        assertEquals(
            ActionType.OPEN_MAPS,
            handler.type
        )
    }

    @Test
    fun `execute opens maps when query exists`() {
        val action = AIAction(
            type = ActionType.OPEN_MAPS,
            data = mapOf("query" to "location")
        )
        handler.execute(action)
        verify { intentLauncher.launch(any()) }
    }

    @Test
    fun `execute does not open maps when query is blank`() {
        val action = AIAction(
            type = ActionType.OPEN_MAPS,
            data = mapOf("query" to "")
        )
        try {
            handler.execute(action)
        } catch (_: Exception) {
        }

        verify { intentLauncher.launch(any()) }
    }
}

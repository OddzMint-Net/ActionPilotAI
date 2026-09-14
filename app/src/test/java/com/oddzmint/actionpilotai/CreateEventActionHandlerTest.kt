package com.oddzmint.actionpilotai

import android.content.Context
import com.oddzmint.actionpilotai.data.actions.CreateEventActionHandler
import com.oddzmint.actionpilotai.data.actions.IntentLauncher
import com.oddzmint.actionpilotai.domain.model.ActionType
import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.domain.model.ActionResult
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class CreateEventActionHandlerTest {

    private lateinit var intentLauncher: IntentLauncher
    private lateinit var handler: CreateEventActionHandler

    @Before
    fun setup() {
        intentLauncher = mockk(relaxed = true)
        handler = CreateEventActionHandler(intentLauncher)
    }

    @Test
    fun `create event in CREATE_EVENT`() {
        assertEquals(
            ActionType.CREATE_EVENT,
            handler.type
        )
    }

    @Test
    fun `execute launches calendar intent when title exists`() {
        val action = AIAction(
            type = ActionType.CREATE_EVENT,
            data = mapOf("title" to "Team meeting")
        )
        handler.execute(action)
        verify { intentLauncher.launch(any()) }
    }

    @Test
    fun `execute returns MissingData and does not launch when title is absent`() {

        val action = AIAction(
            type = ActionType.CREATE_EVENT,
            data = emptyMap()
        )
        val result = handler.execute(action)
        assertTrue(result is ActionResult.Failure.MissingData)

        verify(exactly = 0) { intentLauncher.launch(any())  }
    }
}
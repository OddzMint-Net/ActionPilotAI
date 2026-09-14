package com.oddzmint.actionpilotai

import android.content.Intent
import com.oddzmint.actionpilotai.data.actions.IntentLauncher
import com.oddzmint.actionpilotai.data.actions.ShareTextActionHandler
import com.oddzmint.actionpilotai.domain.model.ActionResult
import com.oddzmint.actionpilotai.domain.model.ActionType
import com.oddzmint.actionpilotai.domain.model.AIAction
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import junit.framework.TestCase.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test

class ShareTextActionHandlerTest {

    private lateinit var intentLauncher: IntentLauncher
    private lateinit var handler: ShareTextActionHandler
    private val fakeChooser = mockk<Intent>(relaxed = true)

    @Before
    fun setup() {
        intentLauncher = mockk(relaxed = true)
        handler = ShareTextActionHandler(intentLauncher)

        mockkStatic(Intent::class)
        every { Intent.createChooser(any(), any()) } returns fakeChooser
    }

    @After
    fun tearDown() {
        unmockkStatic(Intent::class)
    }

    @Test
    fun `execute launches dialog and shares a text`() {
        val action = AIAction(
            type = ActionType.SHARE_TEXT,
            data = mapOf("text" to "share to whatsapp")
        )
        handler.execute(action)
        verify { intentLauncher.launch(fakeChooser) }
    }

    @Test
    fun `execute returns MissingData when text is absent`() {
        val action = AIAction(type = ActionType.SHARE_TEXT, data = emptyMap())
        val result = handler.execute(action)
        assertTrue(result is ActionResult.Failure.MissingData)
        verify(exactly = 0) { intentLauncher.launch(any()) }
    }
}
package com.oddzmint.actionpilotai

import android.content.Context
import com.oddzmint.actionpilotai.data.actions.DialPhoneActionHandler
import com.oddzmint.actionpilotai.data.actions.IntentLauncher
import com.oddzmint.actionpilotai.domain.model.ActionType
import com.oddzmint.actionpilotai.domain.model.AIAction
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class DialPhoneActionHandlerTest {

    private lateinit var intentLauncher: IntentLauncher
    private lateinit var handler: DialPhoneActionHandler

    @Before
    fun setup() {
        intentLauncher = mockk(relaxed = true)
        handler = DialPhoneActionHandler(intentLauncher)
    }

    @Test
    fun `dial phone number in DIAL_PHONE`() {
        assertEquals(
            ActionType.DIAL_PHONE,
            handler.type
        )
    }

    @Test
    fun `execute launches phone dialer when query exist`() {
        val action = AIAction(
            type = ActionType.DIAL_PHONE,
            data = mapOf("phoneNumber" to "0123456789")
        )
        handler.execute( action)
        verify { intentLauncher.launch(any()) }
    }

    @Test
    fun `execute does not launch dialer when phone number is blank`() {
        val action = AIAction(
            type = ActionType.DIAL_PHONE,
            data = emptyMap()
        )
            handler.execute(action)

        verify(exactly = 0) {intentLauncher.launch(any()) }
    }
}
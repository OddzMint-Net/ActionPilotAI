package com.oddzmint.actionpilotai

import android.content.Context
import com.oddzmint.actionpilotai.data.actions.DialPhoneActionHandler
import com.oddzmint.actionpilotai.domain.action.ActionType
import com.oddzmint.actionpilotai.domain.model.AIAction
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class DialPhoneActionHandlerTest {
    private lateinit var handler: DialPhoneActionHandler

    @Before
    fun setup() {
        handler = DialPhoneActionHandler()
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
        val context = mockk<Context>(relaxed = true)
        val action = AIAction(
            type = ActionType.DIAL_PHONE,
            data = mapOf("phoneNumber" to "0123456789")
        )
        handler.execute(context, action)
        verify { context.startActivity(any()) }
    }

    @Test
    fun `execute does not launch dialer when phone number is blank`() {
        val context = mockk<Context>(relaxed = true)
        val action = AIAction(
            type = ActionType.DIAL_PHONE,
            data = mapOf("phoneNumber" to "")
        )
        try {
            handler.execute(context, action)
        } catch (_: Exception) {
        }

        verify(exactly = 0) { context.startActivity(any()) }
    }
}
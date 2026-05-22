package com.oddzmint.actionpilotai

import com.oddzmint.actionpilotai.data.ai.AIActionService
import com.oddzmint.actionpilotai.data.repository.AIActionRepositoryImpl
import com.oddzmint.actionpilotai.domain.action.ActionType
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class AIActionRepositoryImplTest {
    @Test
    fun `getAction return parsed AIAction on success`() = runTest {
        val fakeService = AIActionService {
            """{"type":"OPEN_MAPS","data":{"location":"Johannesburg"}}"""
        }
        val repository = AIActionRepositoryImpl(fakeService)
        val result = repository.getAction("Take me to Johannesburg")
        assertEquals(ActionType.OPEN_MAPS,result.type)
        assertEquals("Johannesburg",result.data["location"])
    }

    @Test
    fun `getAction throws when service fails`() = runTest {
        val fakeService = AIActionService { throw Exception("Network error") }
        val repository = AIActionRepositoryImpl(fakeService)
        assertThrows(Exception::class.java){
            runBlocking { repository.getAction("Anything") }
        }
    }
}
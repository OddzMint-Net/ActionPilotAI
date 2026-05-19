package com.oddzmint.actionpilotai

import com.oddzmint.actionpilotai.domain.model.AIAction
import com.oddzmint.actionpilotai.domain.model.ActionType
import com.oddzmint.actionpilotai.domain.repository.AIActionRepository
import com.oddzmint.actionpilotai.domain.usecase.GetAiActionUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetAiActionUseCaseTest {
    @Test
    fun `invoke returns action from repository`() = runTest {
        val fakeRepository = object : AIActionRepository {
            override suspend fun getAction(userInput: String) = AIAction(
                type = ActionType.OPEN_MAPS,
                data = mapOf("location" to "Johannesburg")
            )
        }
        val useCase = GetAiActionUseCase(fakeRepository)
        val result = useCase("Take me to Johannesburg")
        assertEquals(ActionType.OPEN_MAPS, result.type)
    }
}

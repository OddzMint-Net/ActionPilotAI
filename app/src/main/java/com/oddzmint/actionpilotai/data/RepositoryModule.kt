package com.oddzmint.actionpilotai.data

import com.oddzmint.actionpilotai.data.ai.AIActionService
import com.oddzmint.actionpilotai.data.ai.GeminiService
import com.oddzmint.actionpilotai.domain.AIActionRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAIActionRepository(impl: AIActionRepositoryImpl): AIActionRepository

    @Binds
    abstract fun bindAIActionService(impl: GeminiService): AIActionService

    companion object {
        @IoDispatcher
        @Provides
        fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
    }
}
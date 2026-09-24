/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.oddzmint.actionpilotai.data.actions

import com.oddzmint.actionpilotai.domain.model.ActionHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class ActionHandlerModule {
    @Binds
    @IntoSet
    abstract fun bindCreateEventHandler(impl: CreateEventActionHandler): ActionHandler

    @Binds
    @IntoSet
    abstract fun bindDialPhoneHandler(impl: DialPhoneActionHandler): ActionHandler

    @Binds
    @IntoSet
    abstract fun bindGenerateReplyHandler(impl: GenerateReplyActionHandler): ActionHandler

    @Binds
    @IntoSet
    abstract fun bindOpenMapsHandler(impl: OpenMapsActionHandler): ActionHandler

    @Binds
    @IntoSet
    abstract fun bindOpenUrlHandler(impl: OpenUrlActionHandler): ActionHandler

    @Binds
    @IntoSet
    abstract fun bindSearchWebHandler(impl: SearchWebActionHandler): ActionHandler

    @Binds
    @IntoSet
    abstract fun bindShareTextHandler(impl: ShareTextActionHandler): ActionHandler

}
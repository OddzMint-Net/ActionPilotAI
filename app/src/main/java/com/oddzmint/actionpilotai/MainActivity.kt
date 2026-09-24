/*
 * Copyright (c) 2026 OddzMint.
 * Licensed under the MIT License - see LICENSE file in the project root.
 */
package com.oddzmint.actionpilotai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.oddzmint.actionpilotai.presentation.ActionPilotApp
import com.oddzmint.actionpilotai.ui.theme.ActionPilotAITheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ActionPilotAITheme {
                ActionPilotApp()
                }
            }
        }
    }

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ActionPilotAITheme {
    }
}
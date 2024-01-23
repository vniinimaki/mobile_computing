package com.example.composetutorial

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BackScreen(
    onNavigateToMessages: () -> Unit
) {
    Button(onClick = onNavigateToMessages) {
        Text("Go to messages")
    }
}
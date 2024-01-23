package com.example.composetutorial

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "messages"
    ){
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("messages") {
            ConversationScreen(
                messages = SampleData.conversationSample,
                onNavigateToBack = { navController.navigate("backScreen") },
                )
            }
        composable("backScreen") {
            BackScreen(
                onNavigateToMessages = { navController.navigate("messages") }
            )
        }
    }
}

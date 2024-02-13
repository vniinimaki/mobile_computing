package com.example.composetutorial

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "messages"
    ){
    val db = Room.databaseBuilder(
        LocalContext.current,
        AppDatabase::class.java, "AppDatabase"
    ).allowMainThreadQueries().build()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("messages") {
            ConversationScreen(
                messages = SampleData.conversationSample,

                onNavigateToBack = {
                    navController.navigate("backScreen") {
                        popUpTo("backScreen") {
                            inclusive = true
                        }
                    } },
                db
                )
            }
        composable("backScreen") {
            BackScreen(
                onNavigateToMessages = { navController.navigate("messages") {
                    popUpTo("messages") {
                        inclusive = true
                    }
                } },
                db
            )
        }
    }
}

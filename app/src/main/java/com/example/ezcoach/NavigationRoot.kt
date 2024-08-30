package com.example.ezcoach

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.auth.presentation.login.LoginScreenRoot
import com.example.workout.presentation.overview.OverviewScreenRoot

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = "auth"
    ) {
        overviewGraph(navController)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun NavGraphBuilder.overviewGraph(navController: NavHostController) {
    navigation(
        startDestination = "login",
        route = "auth"
    ) {
        composable(route = "login") {
            // TODO: pass callbacks for navigation
            LoginScreenRoot(
                onSuccessfulLogin = {
                    navController.navigate("overview")
                }
            )
        }

        composable(route = "overview") {
            // TODO: pass callbacks for navigation
            OverviewScreenRoot()
        }
    }
}
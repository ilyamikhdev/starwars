package com.example.starwars.nav


import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.starwars.ui.screens.people.PeopleScreen
import com.example.starwars.ui.screens.people.PeopleViewModel

const val PERSON_ID_KEY = "person_id"

@Composable
fun AppNavigation(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = ScreenRoutes.PeopleScreen.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }

    ) {
        composable(route = ScreenRoutes.PeopleScreen.route) {
            val viewModel: PeopleViewModel = hiltViewModel()
            PeopleScreen(viewModel, navHostController)
        }
    }
}

sealed class ScreenRoutes(val route: String) {
    data object PeopleScreen : ScreenRoutes("people")

    data object PersonScreen :
        ScreenRoutes(route = "person/$PERSON_ID_KEY={$PERSON_ID_KEY}") {

        fun addArg(personId: String) =
            route.replace(oldValue = "{$PERSON_ID_KEY}", newValue = personId)
    }
}
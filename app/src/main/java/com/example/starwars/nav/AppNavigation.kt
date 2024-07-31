package com.example.starwars.nav

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.starwars.ui.screens.people.PeopleScreen
import com.example.starwars.ui.screens.people.PeopleViewModel
import com.example.starwars.ui.screens.person.PersonScreen
import com.example.starwars.ui.screens.person.PersonViewModel

const val PERSON_ID_KEY = "person_id"

@Composable
fun AppNavigation(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.People.route,
        enterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(500)) +
                    fadeIn()
        },
        exitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(500)) +
                    fadeOut()
        },
        popEnterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(500)) +
                    fadeIn()
        },
        popExitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(500)) +
                    fadeOut()
        }

    ) {
        composable(Routes.People.route) {
            val viewModel: PeopleViewModel = hiltViewModel()
            PeopleScreen(viewModel, navHostController)
        }

        composable(
            route = Routes.Person.route,
            arguments = listOf(navArgument(PERSON_ID_KEY) {
                type = NavType.StringType
            }),
        ) { backStackEntry ->
            val personViewModel: PersonViewModel = hiltViewModel()
            personViewModel.loadPerson(
                id = backStackEntry.arguments?.getString(PERSON_ID_KEY) ?: ""
            )
            PersonScreen(personViewModel, navHostController)
        }
    }
}

sealed class Routes(val route: String) {
    data object People : Routes("people")

    data object Person :
        Routes("person/$PERSON_ID_KEY={$PERSON_ID_KEY}") {

        fun addArg(personId: String) =
            route.replace(oldValue = "{$PERSON_ID_KEY}", newValue = personId)
    }
}
package com.example.starwars.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.starwars.ui.screens.people.PeopleScreen
import com.example.starwars.ui.screens.people.PeopleViewModel
import com.example.starwars.ui.screens.person.PersonScreen
import com.example.starwars.ui.screens.person.PersonViewModel
import kotlinx.serialization.Serializable

@Composable
fun AppNavigation(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = PeopleRoute,
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
        composable<PeopleRoute> {
            val viewModel: PeopleViewModel = hiltViewModel()
            PeopleScreen(viewModel, onNavigate = { id ->
                navHostController.navigate(PersonRoute(id))
            })
        }

        composable<PersonRoute> { backStackEntry ->
            val personId = backStackEntry.toRoute<PersonRoute>().personId
            val personViewModel: PersonViewModel = hiltViewModel()
            personViewModel.loadPerson(personId)
            PersonScreen(personViewModel, onBack = {
                navHostController.popBackStack()
            })
        }
    }
}

@Serializable
object PeopleRoute

@Serializable
data class PersonRoute(val personId: String)
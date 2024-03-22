package ru.ikom.foodies.core

import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.ikom.feature_catalog.presentation.CatalogRouter
import ru.ikom.feature_details.DetailsRouter
import ru.ikom.foodies.presentation.SplashRouter

interface Navigation<T> {
    fun read(): StateFlow<T>
    fun update(value: T)
    fun coup()

    class Base : Navigation<Screen>, SplashRouter, CatalogRouter, DetailsRouter {
        private val screen = MutableStateFlow<Screen>(Screen.Start)

        override fun read(): StateFlow<Screen> = screen.asStateFlow()

        override fun update(value: Screen) {
            screen.value = value
        }

        override fun coup() {
            update(Screen.Coup)
        }

        override fun openCatalog() {
            update(CatalogScreen())
        }

        override fun openDetails(data: String) {
            update(DetailsScreen(data))
        }

        override fun comeback() {
            update(Screen.Pop)
        }
    }
}

interface Screen {
    fun show(navController: NavController) = Unit

    abstract class Replace(
        private val route: String,
    ) : Screen {
        override fun show(navController: NavController) = navController.navigate(route)
    }

    abstract class ReplaceWithArguments(
        private val route: String,
        private val data: String,
    ) : Screen {
        override fun show(navController: NavController) = navController.navigate(
            route.replace(
                "{data}",
                data
            )
        )
    }

    abstract class ReplaceWithClear(
        private val route: String,
    ) : Screen {
        override fun show(navController: NavController) = navController.navigate(route) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }

    data object Start : Screen
    data object Coup : Screen

    data object Pop : Screen {
        override fun show(navController: NavController) {
            navController.popBackStack()
        }
    }
}

class CatalogScreen : Screen.ReplaceWithClear(Screens.Catalog)
class DetailsScreen(
    data: String,
) : Screen.ReplaceWithArguments(Screens.Details, data)
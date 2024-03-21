package ru.ikom.foodies

import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.ikom.feature_catalog.presentation.CatalogRouter

interface Navigation<T> {
    fun read(): StateFlow<T>
    fun update(value: T)
    fun coup()

    class Base : Navigation<Screen>, SplashRouter, CatalogRouter {
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
    }
}

interface Screen {
    fun show(navController: NavController) = Unit

    abstract class Replace(
        private val route: String,
    ): Screen {
        override fun show(navController: NavController) = navController.navigate(route)
    }

    data object Start : Screen
    data object Coup : Screen
}

class SplashScreen : Screen.Replace(Screens.Splash)
class CatalogScreen : Screen.Replace(Screens.Catalog)

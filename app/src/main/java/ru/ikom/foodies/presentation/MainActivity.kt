package ru.ikom.foodies.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import ru.ikom.feature_basket.presentation.BasketScreen
import ru.ikom.feature_catalog.presentation.CatalogScreen
import ru.ikom.feature_details.presentation.DetailsScreen
import ru.ikom.foodies.core.Screens
import ru.ikom.foodies.ui.theme.FoodiesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            FoodiesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Content()
                }
            }
        }
    }
}

@Composable
fun Content(viewModel: MainViewModel = koinViewModel()) {
    val navController = rememberNavController()
    val screen by viewModel.read().collectAsState()

    DisposableEffect(Unit) {
        onDispose {
            viewModel.coup()
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow {
            screen
        }.collect {
            screen.show(navController)
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screens.Splash
    ) {
        composable(Screens.Splash) {
            SplashScreen()
        }

        composable(Screens.Catalog) {
            CatalogScreen()
        }

        composable(Screens.Details) {
            val product = it.arguments?.getString("data") ?: ""
            DetailsScreen(product)
        }

        composable(Screens.Basket) {
            BasketScreen()
        }
    }
}
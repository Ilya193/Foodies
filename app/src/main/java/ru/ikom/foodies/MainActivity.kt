package ru.ikom.foodies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import org.koin.androidx.compose.koinViewModel
import ru.ikom.feature_catalog.presentation.CatalogScreen
import ru.ikom.foodies.ui.theme.FoodiesTheme
import ru.ikom.foodies.ui.theme.Orange

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
            SplashScreen(navController = navController)
        }

        composable(Screens.Catalog) {
            CatalogScreen(navController = navController)
        }
    }
}

@Composable
fun SplashScreen(navController: NavController, viewModel: SplashViewModel = koinViewModel()) {
    var isPlaying by remember { mutableStateOf(false) }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_screen_animation))
    val progress by animateLottieCompositionAsState(composition, isPlaying = isPlaying)

    LaunchedEffect(progress) {
        if (progress == 0f) isPlaying = true
        if (progress == 1f) isPlaying = false
    }

    if (!isPlaying) viewModel.openCatalog()

    Box(
        modifier = Modifier.background(Orange)
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
        )
    }
}
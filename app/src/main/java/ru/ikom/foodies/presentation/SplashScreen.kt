package ru.ikom.foodies.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import org.koin.androidx.compose.koinViewModel
import ru.ikom.foodies.R
import ru.ikom.foodies.ui.theme.Orange

@Composable
fun SplashScreen(viewModel: SplashViewModel = koinViewModel()) {
    var isPlaying by remember { mutableStateOf(true) }

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
package ru.ikom.feature_catalog.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun CounterCard(painter: Painter, action: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Card(
        modifier = Modifier.size(40.dp).clickable(
            interactionSource = interactionSource,
            indication = null
        ) { action() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painter,
                contentDescription = null
            )
        }
    }
}
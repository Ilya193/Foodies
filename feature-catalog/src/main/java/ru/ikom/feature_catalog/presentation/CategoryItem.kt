package ru.ikom.feature_catalog.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import ru.ikom.common.Orange

@Composable
fun CategoryItem(item: CategoryUi, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Card(
        modifier = Modifier.wrapContentSize().padding(8.dp).clickable(
            interactionSource = interactionSource,
            indication = null
        ) { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (item.selected) Orange else Color.Transparent
        )
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = item.name,
            style = TextStyle(color = if (item.selected) Color.White else Color.Black)
        )
    }
}
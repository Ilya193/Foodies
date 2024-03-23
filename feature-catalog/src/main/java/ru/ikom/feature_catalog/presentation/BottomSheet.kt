package ru.ikom.feature_catalog.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.ikom.common.Orange
import ru.ikom.feature_catalog.R
import ru.ikom.feature_catalog.presentation.ui.FilterItem

@Composable
fun BottomSheet(initial: List<Int>, onReady: (List<Int>) -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val selected = remember {
        mutableStateListOf<Int>().apply {
            addAll(initial)
        }
    }
    Column(modifier = Modifier
        .wrapContentHeight()
        .padding(16.dp)) {
        Text(
            stringResource(R.string.pick_up_dishes),
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
        )

        FilterItem(stringResource(R.string.without_meat), 1 in initial) {
            if (it)
                selected.add(1)
            else
                selected.remove(1)
        }
        FilterItem(stringResource(R.string.acute), 2 in initial) {
            if (it)
                selected.add(2)
            else
                selected.remove(2)
        }
        FilterItem(stringResource(R.string.with_discount), 3 in initial, false) {
            if (it)
                selected.add(3)
            else
                selected.remove(3)
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    onReady(selected.toList())
                    selected.clear()
                },
            colors = CardDefaults.cardColors(
                containerColor = Orange
            ),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.ready),
                    style = TextStyle(color = Color.White, fontSize = 16.sp)
                )
            }
        }
    }
}
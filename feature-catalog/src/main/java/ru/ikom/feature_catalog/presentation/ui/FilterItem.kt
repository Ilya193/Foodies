package ru.ikom.feature_catalog.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.ikom.common.Orange

@Composable
fun FilterItem(text: String, initial: Boolean, line: Boolean = true, choose: (Boolean) -> Unit) {
    var checkedState by remember { mutableStateOf(initial) }
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)) {
        Text(
            modifier = Modifier.align(Alignment.CenterStart),
            text = text,
            style = TextStyle(fontSize = 16.sp)
        )

        Checkbox(
            modifier = Modifier.size(20.dp).align(Alignment.CenterEnd),
            checked = checkedState,
            onCheckedChange = { value ->
                checkedState = value
                choose(value)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = Orange,
                checkmarkColor = Color.White,
            )
        )

        if (line)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .align(Alignment.BottomCenter)
                    .background(Color.Gray)
            )
    }
}
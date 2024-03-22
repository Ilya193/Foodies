package ru.ikom.feature_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.ikom.common.R

@Composable
fun DetailsScreen(data: String, viewModel: DetailsViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.init(data)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        uiState.product?.let {
            Card(
                modifier = Modifier
            ) {

            }
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(375.dp),
                painter = painterResource(id = R.drawable.photo),
                contentDescription = null
            )
        }
    }
}
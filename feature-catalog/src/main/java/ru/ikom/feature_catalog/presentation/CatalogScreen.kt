package ru.ikom.feature_catalog.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.ikom.common.Orange
import ru.ikom.feature_catalog.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(navController: NavController, viewModel: CatalogViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    Image(painter = painterResource(R.drawable.filter), contentDescription = null)
                },
                title = {
                    Image(painter = painterResource(R.drawable.logo), contentDescription = null)
                },
                actions = {
                    Image(painter = painterResource(R.drawable.search), contentDescription = null)
                }
            )
        },
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (uiState.categories.isNotEmpty()) {
                LazyRow {
                    items(items = uiState.categories, key = { item -> item.id }) { item ->
                        CategoryItem(item) {
                            viewModel.onClickCategory(item)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItem(item: CategoryUi, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Card(
        modifier = Modifier.wrapContentSize().clickable(
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
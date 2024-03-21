package ru.ikom.feature_catalog.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
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
            Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                if (uiState.categories.isNotEmpty()) {
                    LazyRow {
                        items(items = uiState.categories, key = { item -> item.id }) { item ->
                            CategoryItem(item) {
                                viewModel.onClickCategory(item)
                            }
                        }
                    }
                }

                if (uiState.products.isNotEmpty()) {
                    LazyVerticalGrid(columns = GridCells.Adaptive(170.dp)) {
                        itemsIndexed(
                            items = uiState.products,
                            key = { index, item -> item.id }) { index, item ->
                            ProductItem(item, index) {
                                viewModel.onClickProduct(item, index)
                            }
                        }
                    }
                }
            }

            uiState.sum?.let {
                Box(
                    modifier = Modifier.fillMaxWidth().height(72.dp).align(Alignment.BottomCenter)
                        .background(Color.White)
                ) {
                    ProductsAmount(it)
                }
            }
        }
    }
}
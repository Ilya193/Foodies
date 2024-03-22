package ru.ikom.feature_catalog.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.ikom.feature_catalog.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(navController: NavController, viewModel: CatalogViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    var searchMode by remember { mutableStateOf(false) }
    var dish by remember { mutableStateOf("") }

    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()

    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(8.dp),
                navigationIcon = {
                    if (!searchMode)
                        Image(
                            modifier = Modifier.clickable { showBottomSheet = true },
                            painter = painterResource(R.drawable.filter),
                            contentDescription = null
                        )
                    else
                        Image(
                            modifier = Modifier.clickable {
                                searchMode = false
                                viewModel.changeMode(false)
                                dish = ""
                            },
                            painter = painterResource(R.drawable.back), contentDescription = null
                        )
                },
                title = {
                    if (!searchMode)
                        Image(painter = painterResource(R.drawable.logo), contentDescription = null)
                    else {
                        var showCancel by remember { mutableStateOf(false) }
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = dish,
                            onValueChange = { value ->
                                showCancel = value.isNotEmpty()
                                dish = value
                                viewModel.inputDish(dish)
                            },
                            placeholder = { Text(text = stringResource(R.string.search_dish)) },
                            shape = RoundedCornerShape(0.dp),
                            colors = TextFieldDefaults.colors(
                                unfocusedIndicatorColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                            ),
                            trailingIcon = {
                                if (showCancel)
                                    Icon(
                                        modifier = Modifier.clickable {
                                            searchMode = false
                                            viewModel.changeMode(false)
                                            dish = ""
                                        },
                                        painter = painterResource(R.drawable.cancel),
                                        contentDescription = null
                                    )
                            }
                        )
                    }

                },
                actions = {
                    if (!searchMode)
                        Image(
                            modifier = Modifier.clickable {
                                searchMode = true
                                viewModel.changeMode(true)
                            },
                            painter = painterResource(R.drawable.search), contentDescription = null
                        )
                }
            )
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {

                if (uiState.categories.isNotEmpty() && !searchMode) {
                    LazyRow {
                        items(items = uiState.categories, key = { item -> item.id }) { item ->
                            CategoryItem(item) {
                                viewModel.onClickCategory(item)
                            }
                        }
                    }
                }

                if (uiState.searchMode && uiState.products.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = stringResource(id = R.string.enter_name_dish))
                    }
                }

                if ((uiState.products.isNotEmpty() && uiState.searchMode || !uiState.searchMode)) {
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .align(Alignment.BottomCenter)
                        .background(Color.White)
                ) {
                    ProductsAmount(it)
                }
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = bottomSheetState
            ) {
                Box(modifier = Modifier.height(300.dp)) {
                    Button(onClick = {  }) {
                        Text(text = "Click")
                    }
                }
            }
        }
    }
}
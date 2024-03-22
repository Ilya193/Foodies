package ru.ikom.feature_catalog.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.ikom.common.Orange
import ru.ikom.feature_catalog.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(navController: NavController, viewModel: CatalogViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    val interactionSource = remember { MutableInteractionSource() }

    var searchMode by remember { mutableStateOf(false) }
    var dish by remember { mutableStateOf("") }

    val filterMode = remember { mutableStateListOf<Int>() }

    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(8.dp),
                navigationIcon = {
                    if (!searchMode)
                        Box(
                            modifier = Modifier.width(40.dp)
                        ) {
                            if (filterMode.isNotEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .size(17.dp)
                                        .offset(y = (-10).dp)
                                        .clip(CircleShape)
                                        .align(Alignment.TopEnd)
                                        .background(color = Orange),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = filterMode.size.toString(),
                                        style = TextStyle(color = Color.White, fontSize = 11.sp),
                                    )
                                }
                            }
                            Image(
                                modifier = Modifier.clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) { showBottomSheet = true },
                                painter = painterResource(R.drawable.filter),
                                contentDescription = null
                            )
                        }
                    else
                        Image(
                            modifier = Modifier.clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
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
                            modifier = Modifier.clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                searchMode = true
                                viewModel.changeMode(true)
                                filterMode.clear()
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
                                filterMode.clear()
                            }
                        }
                    }
                }

                if (uiState.searchMode && uiState.products.isEmpty()) {
                    when (uiState.nothingFound) {
                        is NothingFound.Initial -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = stringResource(id = R.string.enter_name_dish))
                            }
                        }

                        is NothingFound.Search -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = stringResource(id = R.string.nothing_found))
                            }
                        }

                        is NothingFound.Filter -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = stringResource(id = R.string.no_dishes_found))
                            }
                        }
                    }

                }

                if ((uiState.products.isNotEmpty() && uiState.searchMode || !uiState.searchMode)) {
                    LazyVerticalGrid(columns = GridCells.Adaptive(170.dp)) {
                        itemsIndexed(
                            items = uiState.products,
                            key = { index, item -> item.id }) { index, item ->
                            ProductItem(
                                item,
                                index,
                                openDetails = viewModel::openDetails,
                                onClickBuy = {
                                    viewModel.onClickBuy(item, index)
                                })
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
                    ProductsAmount(it) {
                        viewModel.add()
                    }
                }
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
            ) {
                BottomSheet(filterMode.toList()) {
                    showBottomSheet = false
                    filterMode.clear()
                    filterMode.addAll(it)
                    viewModel.filter(it)
                }
            }
        }
    }
}
package ru.ikom.feature_basket.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import ru.ikom.common.ErrorMessage
import ru.ikom.common.LoadData
import ru.ikom.feature_basket.R
import ru.ikom.feature_basket.presentation.ui.CardProduct
import ru.ikom.feature_basket.presentation.ui.ProductsAmount

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasketScreen(viewModel: BasketViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    if (uiState.isLoading)
        LoadData()
    else if (uiState.isError)
        ErrorMessage { viewModel.fetchProducts() }
    else {
        Scaffold(
            topBar = {
                TopAppBar(modifier = Modifier.padding(8.dp), navigationIcon = {
                    Image(
                        modifier = Modifier.clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            viewModel.comeback()
                        }, painter = painterResource(R.drawable.back), contentDescription = null
                    )
                }, title = {
                    Text(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        text = stringResource(R.string.basket),
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    )
                })
            }
        ) { padding ->

            Box(modifier = Modifier
                .fillMaxSize()
                .padding(padding)) {
                if (uiState.products.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .background(Color(0xFFF5F5F5))
                    ) {
                        itemsIndexed(
                            items = uiState.products,
                            key = { _, item -> item.id }) { index, item ->
                            CardProduct(item = item,
                                minus = {
                                    viewModel.minus(item, index)
                                },
                                plus = {
                                    viewModel.plus(item, index)
                                })
                        }
                    }

                    if (uiState.sum != 0) {
                        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                            ProductsAmount(uiState.sum)
                        }
                    }
                } else {
                    Box(modifier = Modifier.align(Alignment.Center)) {
                        Text(text = stringResource(R.string.empty_dish))
                    }
                }
            }
        }
    }
}
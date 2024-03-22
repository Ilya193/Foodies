package ru.ikom.feature_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import ru.ikom.common.Orange

@Composable
fun DetailsScreen(data: String, viewModel: DetailsViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(Unit) {
        viewModel.init(data)
    }

    Column(modifier = Modifier.fillMaxSize().padding(top = 16.dp).verticalScroll(ScrollState(0))) {
        uiState.product?.let { product ->
            Column(modifier = Modifier.padding(16.dp)) {
                Box {
                    Card(
                        modifier = Modifier.align(Alignment.TopStart).clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) { viewModel.pop() },
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = CircleShape,
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Image(
                            modifier = Modifier.padding(12.dp),
                            painter = painterResource(id = R.drawable.arrowleft),
                            contentDescription = null
                        )
                    }
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(375.dp),
                        painter = painterResource(id = ru.ikom.common.R.drawable.photo),
                        contentDescription = null
                    )
                }

                Text(text = product.name, style = TextStyle(color = Color.Black, fontSize = 35.sp))
                Text(text = product.description, style = TextStyle(fontSize = 16.sp))
            }
            Line()
            ProductInformation(
                stringResource(R.string.weight),
                product.measure.toString() + " " + product.measureUnit
            )
            Line()
            ProductInformation(
                stringResource(R.string.energy_value),
                product.energyPer100Grams.toString() + " ккал"
            )
            Line()
            ProductInformation(
                stringResource(R.string.squirrels),
                product.proteinsPer100Grams.toString() + " " + product.measureUnit
            )
            Line()
            ProductInformation(
                stringResource(R.string.fats),
                product.fatsPer100Grams.toString() + " " + product.measureUnit
            )
            Line()
            ProductInformation(
                stringResource(R.string.carbohydrates),
                product.carbohydratesPer100Grams.toString() + " " + product.measureUnit
            )
            Line()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .height(72.dp)
            ) {
                ProductsAmount(product.priceCurrent) {
                    viewModel.add(product)
                }
            }
        }
    }
}

@Composable
fun Line() {
    Box(
        modifier = Modifier.fillMaxWidth().height(1.dp)
            .background(Color.Gray)
    )
}

@Composable
fun ProductInformation(characteristic: String, grams: String) {
    Row(
        modifier = Modifier.fillMaxWidth().height(50.dp).padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = characteristic, style = TextStyle(color = Color.Gray, fontSize = 16.sp))
        Text(text = grams, style = TextStyle(color = Color.Black, fontSize = 16.sp))
    }
}

@Composable
fun ProductsAmount(sum: Int, add: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Card(
        modifier = Modifier.fillMaxSize().padding(8.dp).clickable(
            interactionSource = interactionSource,
            indication = null
        ) { add() },
        colors = CardDefaults.cardColors(
            containerColor = Orange
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.price_current) + " $sum " + stringResource(R.string.currency),
                style = TextStyle(color = Color.White, fontSize = 16.sp)
            )
        }
    }
}
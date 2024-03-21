package ru.ikom.feature_catalog.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import ru.ikom.feature_catalog.R

@Composable
fun ProductItem(
    item: ProductUi,
    index: Int,
    viewModel: CatalogViewModel = koinViewModel(),
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier.height(290.dp).padding(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F5))
        ) {
            Column {
                Box {
                    if (item.priceOld != null)
                        Image(
                            modifier = Modifier.align(Alignment.TopStart).padding(4.dp),
                            painter = painterResource(R.drawable.discount),
                            contentDescription = null
                        )
                    Image(painter = painterResource(R.drawable.photo), contentDescription = null)
                }
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = item.name,
                    style = TextStyle(fontSize = 14.sp)
                )
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = item.measure.toString(),
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                )
            }

            if (item.buy) {
                Row(
                    modifier = Modifier.padding(vertical = 10.dp).fillMaxWidth().height(40.dp)
                        .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CounterCard(
                        painterResource(R.drawable.minus),
                        action = { viewModel.onClickMinus(item, index) })
                    Text(text = item.count.toString())
                    CounterCard(
                        painterResource(R.drawable.plus),
                        action = { viewModel.onClickPlus(item, index) })
                }
            } else {
                Card(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp).fillMaxWidth()
                        .height(40.dp).align(Alignment.BottomCenter)
                        .clickable { onClick() },
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = item.priceCurrent.toString() + " ₽")
                        if (item.priceOld != null)
                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                text = item.priceOld.toString() + " ₽",
                                style = TextStyle(
                                    textDecoration = TextDecoration.LineThrough,
                                    color = Color.Gray
                                )
                            )
                    }
                }
            }
        }
    }
}
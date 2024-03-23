package ru.ikom.feature_basket.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.ikom.feature_basket.R
import ru.ikom.feature_basket.presentation.ProductUi

@Composable
fun CardProduct(item: ProductUi, minus: () -> Unit, plus: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().height(130.dp).padding(vertical = 2.dp),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            Box(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(96.dp),
                    painter = painterResource(ru.ikom.common.R.drawable.photo),
                    contentDescription = null
                )
            }

            Box(
                modifier = Modifier.weight(2f).fillMaxHeight().padding(vertical = 12.dp),
            ) {
                Text(modifier = Modifier.align(Alignment.TopStart), text = item.name)
                Row(
                    modifier = Modifier.fillMaxWidth(0.6f).align(Alignment.BottomStart),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    CounterCard(painterResource(R.drawable.minus), action = minus)
                    Text(text = item.count.toString(), style = TextStyle(fontSize = 16.sp))
                    CounterCard(painterResource(R.drawable.plus), action = plus)
                }
            }

            Box(
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.BottomEnd)
                        .padding(vertical = 24.dp, horizontal = 6.dp),
                    text = item.priceCurrent.toString() + " " + stringResource(R.string.currency),
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )
                item.priceOld?.let {
                    Text(
                        modifier = Modifier.align(Alignment.BottomEnd).padding(6.dp),
                        text = it.toString() + " " + stringResource(R.string.currency),
                        textDecoration = TextDecoration.LineThrough,
                        style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                    )
                }
            }
        }
    }
}
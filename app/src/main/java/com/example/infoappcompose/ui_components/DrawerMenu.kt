package com.example.infoappcompose.ui_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.infoappcompose.R
import com.example.infoappcompose.ui.theme.BgTransparent
import com.example.infoappcompose.ui.theme.MainBlue
import com.example.infoappcompose.utils.DrawerEvents

/**
 * Последовательность события при нажатии:
 * 1. Жму на кнопку, создаётся onEvent с Индексом и Заголовком
 * 2. Событие отправляется в Body
 * 3. Далее событие отправляем, через ф-ю onEvent на MainActivity
 */
@Composable
fun DrawerMenu(onEvent: (DrawerEvents) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(
                id = R.drawable.back_products
            ),
            contentDescription = "Main bg image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.fillMaxSize()) {
            Header()
            Body() { drawerEvents ->
                onEvent(drawerEvents)
            }
        }
    }

}

@Composable
fun Header() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, MainBlue)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.header_products
                ),
                contentDescription = "Header image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )  //сохраняем пропорции картинки , заполняя всё пространство, лишнее обрезаем
            Text(
                text = "-Справочник продуктов-",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MainBlue)
                    .padding(10.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun Body(onEvent: (DrawerEvents) -> Unit) {
    val list = stringArrayResource(id = R.array.drawer_list)
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(list) { index, title ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp),
                backgroundColor = BgTransparent
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onEvent(DrawerEvents.OnItemClick(title, index))
                        }
                        .padding(10.dp)
                        .wrapContentWidth(),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
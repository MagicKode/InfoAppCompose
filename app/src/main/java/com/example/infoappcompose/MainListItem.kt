package com.example.infoappcompose

import android.graphics.BitmapFactory
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.infoappcompose.ui.theme.BgTransparent2
import com.example.infoappcompose.ui.theme.Gray
import com.example.infoappcompose.ui.theme.MainBlue
import com.example.infoappcompose.ui.theme.MainRed
import com.example.infoappcompose.utils.ListItem

@Composable
fun MainListItem(
    mainViewModel: MainViewModel,
    item: ListItem,
    onClick: (ListItem) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(5.dp)
            .clickable {
                onClick(item)
            },
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, MainBlue)
    ) {
        ConstraintLayout(    // констрэйнт для жёсткого прикрепления эелемнтов друг к другу
            modifier = Modifier.fillMaxSize()
        ) {
            /**
             * даём названия элементам, которые бдудут прикрепляться к родительскому
             *
             */
            val (image, text, favoriteButton) = createRefs()

            AssetImage(    //используем AssetImage вместо конкретной картинки
                imageName = item.imageName,
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    }
            )
            Text(
                text = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MainBlue)
                    .padding(10.dp)
                    .constrainAs(text) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            IconButton(
                onClick = {
                          mainViewModel.insertItem(
                              item.copy(favorites = !item.favorites) //при изменении значения, то перезаписывается в базе
                          )
                },
                modifier = Modifier.constrainAs(favoriteButton) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite",
                    tint = if (item.favorites) MainRed else Gray,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(BgTransparent2)
                        .padding(5.dp)
                )
            }
        }
    }
}

@Composable
fun AssetImage(imageName: String, contentDescription: String, modifier: Modifier) {
    val context = LocalContext.current
    val assetManager = context.assets  //нужен , чтобы открыть inputStream поток
    val inputStream = assetManager.open(imageName)  //нужен, чтобы получить картинку по названию
    val bitMap = BitmapFactory.decodeStream(inputStream)

    Image(
        bitmap = bitMap.asImageBitmap(),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,  //чтобы картинка сохраняла пропорции
        modifier = modifier
    )
}
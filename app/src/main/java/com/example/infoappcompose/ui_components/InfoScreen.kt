package com.example.infoappcompose.ui_components

import android.graphics.BitmapFactory
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.infoappcompose.AssetImage
import com.example.infoappcompose.utils.ListItem

@Composable
fun InfoScreen(item: ListItem) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
        ) {
            AssetImage(
                imageName = item.imageName,
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            HtmlLoader(htmlName = item.htmlName)
        }
    }
}

@Composable
fun HtmlLoader(htmlName: String) {
    val context = LocalContext.current
    val assetManager = context.assets  //нужен , чтобы открыть inputStream поток
    val inputStream = assetManager.open("html/$htmlName")  //нужен, чтобы получить картинку по названию
    val size = inputStream.available() // узнаю размер стрима, чтобы считаь байты
    val buffer = ByteArray(size) //создаю буффер, чтобы приянть считанные байты
    inputStream.read(buffer)  // считываю инпутстрим
    val htmlString = String(buffer)  //превращаем байты в строки

    AndroidView(factory = {
        WebView(it).apply {
            webViewClient = WebViewClient()
            loadData(htmlString, "text/html", "utf-8")  //считывание текста в стандартной кодировке
        }
    })
}


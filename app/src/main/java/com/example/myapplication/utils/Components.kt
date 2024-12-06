package com.example.myapplication.utils

import android.webkit.WebView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WebViewPage(url: String, onDismiss: () -> Unit) {
    val context = LocalContext.current
    val webView = remember { WebView(context) }
    webView.settings.javaScriptEnabled = true
    AndroidView(
        factory = {
            webView.apply {
                loadUrl(url)
            }
        }
    )
    DisposableEffect(webView) {
        onDispose {
            webView.destroy()
        }
    }
    Button(onClick = onDismiss) { Text("Close") }
}

@Composable
fun SearchField(items: List<String>,
                onItemSelected: (String) -> Unit) {
    var searchTextState by remember { mutableStateOf(TextFieldValue("")) }
    var active by remember { mutableStateOf(false) }
    var filteredItems by remember { mutableStateOf(items) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(searchTextState.text) {
        scope.launch {
            delay(300) // Debounce delay (adjust as needed)
            filteredItems = items.filter { it.contains(searchTextState.text, ignoreCase = true) }
        }
    }
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon",
                tint = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = searchTextState,
                onValueChange = {
                    searchTextState = it
                    active = true },
                label = { Text("Search") },
                modifier = Modifier.weight(1f),
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (filteredItems.isEmpty() && searchTextState.text.isNotBlank()) {
            Text("No results found.", style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray))
        } else if(active) {
            LazyColumn {
                items(filteredItems.size) { ind ->
                    ListItem(items[ind], onItemSelected)
                }
            }
        }
    }

}

@Composable
fun ListItem(item: String, onItemSelected: (String) -> Unit) {
    Button(onClick = {onItemSelected(item)}, modifier = Modifier.fillMaxWidth()) {
        Text(text = item)
    }

}
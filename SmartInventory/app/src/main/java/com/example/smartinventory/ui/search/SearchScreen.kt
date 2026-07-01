package com.example.smartinventory.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartinventory.ui.barang.ProductItem
import com.example.smartinventory.ui.barang.ProductViewModel
import com.example.smartinventory.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBack: () -> Unit,
    viewModel: ProductViewModel = viewModel()
) {
    val products by viewModel.products.collectAsState()
    var query by remember { mutableStateOf("") }
    
    val filteredProducts = if (query.isEmpty()) emptyList() else products.filter {
        it.name.contains(query, ignoreCase = true) || it.code.contains(query, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            Surface(color = BrownPrimary, shadowElevation = 8.dp) {
                Column(modifier = Modifier.padding(bottom = 8.dp)) {
                    TopAppBar(
                        title = { Text("PENCARIAN DATA", fontWeight = FontWeight.ExtraBold, color = Color.White) },
                        navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back", tint = Color.White) } },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                    )
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        placeholder = { Text("Ketik nama atau kode barang...", color = Color.White.copy(alpha = 0.6f)) },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        leadingIcon = { Icon(Icons.Default.Search, null, tint = Color.White) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.4f),
                            cursorColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true
                    )
                }
            }
        },
        containerColor = AppBackground
    ) { padding ->
        if (query.isEmpty()) {
            Box(modifier = Modifier.padding(padding).fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Search, null, modifier = Modifier.size(64.dp), tint = TextSecondary.copy(alpha = 0.3f))
                    Text("Cari barang untuk melihat detail", color = TextSecondary)
                }
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(filteredProducts) { product ->
                    ProductItem(product, onEdit = {}, onDelete = {})
                }
            }
        }
    }
}

package com.example.smartinventory.ui.stok

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartinventory.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockInScreen(
    onBack: () -> Unit,
    viewModel: TransactionViewModel = viewModel()
) {
    val products by viewModel.products.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    
    var selectedProductId by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var info by remember { mutableStateOf("") }
    
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("STOCK IN", fontWeight = FontWeight.ExtraBold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back", tint = Color.White) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DiscordCanvas, titleContentColor = Color.White)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }, containerColor = DiscordGreen, contentColor = DiscordInkDark) {
                Icon(Icons.Default.Add, "Add Stock In")
            }
        },
        containerColor = DiscordCanvas
    ) { padding ->
        // List of Stock In History (Filtered)
        val history by viewModel.history.collectAsState()
        val stockInHistory = history.filter { it.type == "Masuk" }

        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(stockInHistory) { item ->
                Card(colors = CardDefaults.cardColors(containerColor = DiscordSurfaceIndigo)) {
                    Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text(item.productName ?: "Unknown", color = Color.White, fontWeight = FontWeight.Bold)
                            Text(item.date, color = Color.Gray, fontSize = 12.sp)
                        }
                        Text("+${item.quantity}", color = DiscordGreen, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            containerColor = DiscordSurfaceIndigo,
            title = { Text("New Stock In", color = Color.White, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Select Product", color = Color.Gray)
                    products.forEach { p ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(selected = selectedProductId == p.id, onClick = { selectedProductId = p.id ?: "" })
                            Text(p.name, color = Color.White)
                        }
                    }
                    OutlinedTextField(
                        value = quantity, onValueChange = { quantity = it }, label = { Text("Quantity", color = Color.Gray) },
                        modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )
                    OutlinedTextField(
                        value = info, onValueChange = { info = it }, label = { Text("Info", color = Color.Gray) },
                        modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White)
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.addStockIn(selectedProductId, quantity.toIntOrNull() ?: 0, date, info)
                    showDialog = false
                }, colors = ButtonDefaults.buttonColors(containerColor = DiscordBlurple)) { Text("Save") }
            }
        )
    }
}

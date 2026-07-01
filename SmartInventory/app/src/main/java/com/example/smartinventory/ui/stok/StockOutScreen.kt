package com.example.smartinventory.ui.stok

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.RemoveCircle
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
fun StockOutScreen(
    onBack: () -> Unit,
    viewModel: TransactionViewModel = viewModel()
) {
    val products by viewModel.products.collectAsState()
    val message by viewModel.message.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    
    var showDialog by remember { mutableStateOf(false) }
    
    var selectedProductId by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("STOCK OUT", fontWeight = FontWeight.ExtraBold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back", tint = Color.White) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = Color.White)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }, containerColor = ErrorRed, contentColor = Color.White) {
                Icon(Icons.Default.RemoveCircle, "Add Stock Out")
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        val history by viewModel.history.collectAsState()
        val stockOutHistory = history.filter { it.type == "Keluar" }

        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(stockOutHistory) { item ->
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), elevation = CardDefaults.cardElevation(2.dp)) {
                    Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column {
                            Text(item.productName ?: "Unknown", color = TextPrimary, fontWeight = FontWeight.Bold)
                            Text(item.date, color = TextSecondary, fontSize = 12.sp)
                        }
                        Text("-${item.quantity}", color = ErrorRed, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            containerColor = MaterialTheme.colorScheme.surface,
            title = { Text("New Stock Out", color = TextPrimary, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Select Product", color = TextSecondary)
                    products.forEach { p ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(selected = selectedProductId == p.id, onClick = { selectedProductId = p.id ?: "" })
                            Text("${p.name} (Stok: ${p.stock})", color = TextPrimary)
                        }
                    }
                    OutlinedTextField(
                        value = quantity, onValueChange = { quantity = it }, label = { Text("Quantity", color = TextSecondary) },
                        modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary)
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.addStockOut(selectedProductId, quantity.toIntOrNull() ?: 0, date)
                    showDialog = false
                }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) { Text("Save") }
            }
        )
    }

    LaunchedEffect(message) {
        message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMessage()
        }
    }
}

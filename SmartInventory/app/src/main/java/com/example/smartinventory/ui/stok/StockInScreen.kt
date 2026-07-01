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
    val message by viewModel.message.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    
    var showDialog by remember { mutableStateOf(false) }
    
    var selectedProductId by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var info by remember { mutableStateOf("") }
    
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("STOK MASUK", fontWeight = FontWeight.ExtraBold, color = Color.White) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back", tint = Color.White) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrownPrimary)
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    selectedProductId = ""
                    quantity = ""
                    info = ""
                    showDialog = true
                },
                containerColor = SuccessGreen,
                contentColor = Color.White,
                icon = { Icon(Icons.Default.Add, null) },
                text = { Text("Input Stok Masuk") }
            )
        },
        containerColor = AppBackground
    ) { padding ->
        val history by viewModel.history.collectAsState()
        val stockInHistory = history.filter { it.type == "in" }

        if (stockInHistory.isEmpty()) {
            Box(modifier = Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Belum ada riwayat stok masuk", color = TextSecondary)
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(stockInHistory) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = AppSurface),
                        elevation = CardDefaults.cardElevation(2.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor)
                    ) {
                        Row(modifier = Modifier.padding(20.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                color = SuccessGreen.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.size(48.dp)
                            ) {
                                Icon(Icons.Default.Add, null, tint = SuccessGreen, modifier = Modifier.padding(12.dp))
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.productName ?: "Produk Terhapus", color = TextPrimary, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                                Text(item.date, color = TextSecondary, fontSize = 12.sp)
                                if (!item.description.isNullOrEmpty()) {
                                    Text(item.description, color = TextSecondary, fontSize = 11.sp, maxLines = 1)
                                }
                            }
                            Text("+${item.quantity}", color = SuccessGreen, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            containerColor = AppSurface,
            title = { Text("Input Stok Masuk Baru", color = TextPrimary, fontWeight = FontWeight.ExtraBold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    var expanded by remember { mutableStateOf(false) }
                    val selectedProductName = products.find { it.id == selectedProductId }?.name ?: "Pilih Produk"

                    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                        OutlinedTextField(
                            value = selectedProductName, onValueChange = {}, readOnly = true,
                            label = { Text("Produk") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.menuAnchor().fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            products.forEach { p ->
                                DropdownMenuItem(
                                    text = { Text(p.name) },
                                    onClick = { selectedProductId = p.id ?: ""; expanded = false }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = quantity, onValueChange = { quantity = it }, label = { Text("Jumlah (Qty)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = info, onValueChange = { info = it }, label = { Text("Keterangan") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.addStockIn(selectedProductId, quantity.toIntOrNull() ?: 0, date, info)
                        showDialog = false
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen)
                ) { Text("SIMPAN STOK") }
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

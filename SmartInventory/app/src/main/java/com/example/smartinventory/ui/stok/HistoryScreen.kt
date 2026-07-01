package com.example.smartinventory.ui.stok

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartinventory.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onBack: () -> Unit,
    viewModel: TransactionViewModel = viewModel()
) {
    val history by viewModel.history.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("RIWAYAT TRANSAKSI", fontWeight = FontWeight.ExtraBold, color = Color.White) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back", tint = Color.White) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrownPrimary)
            )
        },
        containerColor = AppBackground
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(history) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = AppSurface), 
                    elevation = CardDefaults.cardElevation(2.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor)
                ) {
                    Row(modifier = Modifier.padding(20.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = when(item.type) {
                                "in" -> SuccessGreen.copy(alpha = 0.1f)
                                "out" -> ErrorRed.copy(alpha = 0.1f)
                                else -> Color.Gray.copy(alpha = 0.1f)
                            },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = if (item.type == "in") Icons.Default.History else Icons.Default.History, // Use appropriate icons
                                contentDescription = null,
                                tint = when(item.type) {
                                    "in" -> SuccessGreen
                                    "out" -> ErrorRed
                                    else -> Color.Gray
                                },
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.productName ?: "Produk Terhapus", color = TextPrimary, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                            Text(item.date, color = TextSecondary, fontSize = 12.sp)
                            if (!item.description.isNullOrEmpty()) {
                                Text(item.description, color = TextSecondary, fontSize = 11.sp, maxLines = 1)
                            }
                        }
                        
                        Text(
                            text = when(item.type) {
                                "in" -> "+${item.quantity}"
                                "out" -> "-${item.quantity}"
                                else -> "${item.quantity}"
                            },
                            color = when(item.type) {
                                "in" -> SuccessGreen
                                "out" -> ErrorRed
                                else -> Color.Gray
                            },
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}

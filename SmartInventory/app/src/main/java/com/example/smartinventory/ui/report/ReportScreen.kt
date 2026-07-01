package com.example.smartinventory.ui.report

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartinventory.ui.stok.TransactionViewModel
import com.example.smartinventory.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    onBack: () -> Unit,
    viewModel: TransactionViewModel = viewModel()
) {
    val history by viewModel.history.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Grouping by product
    val reportData = history.groupBy { it.productName ?: "Unknown" }.mapValues { entry ->
        val masuk = entry.value.filter { it.type == "in" }.sumOf { it.quantity }
        val keluar = entry.value.filter { it.type == "out" }.sumOf { it.quantity }
        val reject = entry.value.filter { it.type == "reject" }.sumOf { it.quantity }
        Triple(masuk, keluar, reject)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("LAPORAN STOK", fontWeight = FontWeight.ExtraBold, color = Color.White) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back", tint = Color.White) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrownPrimary)
            )
        },
        containerColor = AppBackground
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (isLoading && history.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = BrownPrimary)
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = BrownAccent.copy(alpha = 0.2f)),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                                Surface(color = BrownPrimary, shape = RoundedCornerShape(12.dp), modifier = Modifier.size(44.dp)) {
                                    Icon(Icons.Default.Assessment, null, tint = Color.White, modifier = Modifier.padding(10.dp))
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Column {
                                    Text("Ringkasan Laporan", fontWeight = FontWeight.ExtraBold, color = TextPrimary, fontSize = 18.sp)
                                    Text("Pergerakan stok akumulatif", color = TextSecondary, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                    items(reportData.toList()) { (name, stats) ->
                        ReportItem(name, stats.first, stats.second, stats.third)
                    }
                }
            }
        }
    }
}

@Composable
fun ReportItem(name: String, inQty: Int, outQty: Int, rejectQty: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AppSurface),
        elevation = CardDefaults.cardElevation(2.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(name, fontWeight = FontWeight.ExtraBold, color = TextPrimary, fontSize = 17.sp)
            Divider(modifier = Modifier.padding(vertical = 12.dp), color = DividerColor)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                StatColumn("Masuk", "$inQty", SuccessGreen)
                StatColumn("Keluar", "$outQty", ErrorRed)
                StatColumn("Reject", "$rejectQty", Color.Gray)
                StatColumn("Netto", "${inQty - outQty - rejectQty}", BrownPrimary)
            }
        }
    }
}

@Composable
fun StatColumn(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Medium)
        Text(value, color = color, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
    }
}

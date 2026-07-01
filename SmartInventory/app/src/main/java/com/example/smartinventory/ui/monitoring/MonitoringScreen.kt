package com.example.smartinventory.ui.monitoring

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartinventory.data.model.Product
import com.example.smartinventory.ui.barang.ProductViewModel
import com.example.smartinventory.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonitoringScreen(
    onBack: () -> Unit,
    viewModel: ProductViewModel = viewModel()
) {
    val products by viewModel.products.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    
    val lowStockProducts = products.filter { it.stock <= 5 }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MONITORING STOK", fontWeight = FontWeight.ExtraBold, color = Color.White) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back", tint = Color.White) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrownPrimary)
            )
        },
        containerColor = AppBackground
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (isLoading && products.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = BrownPrimary)
            } else if (lowStockProducts.isEmpty()) {
                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.CheckCircle, null, tint = SuccessGreen, modifier = Modifier.size(80.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Semua stok aman!", color = TextPrimary, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
                    Text("Tidak ada barang dengan stok menipis.", color = TextSecondary)
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = ErrorRed.copy(alpha = 0.1f)),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Warning, null, tint = ErrorRed)
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("Ditemukan ${lowStockProducts.size} barang dengan stok <= 5", color = ErrorRed, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    items(lowStockProducts) { product ->
                        MonitoringProductItem(product)
                    }
                }
            }
        }
    }
}

@Composable
fun MonitoringProductItem(product: Product) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AppSurface),
        elevation = CardDefaults.cardElevation(2.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor)
    ) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Surface(
                color = ErrorRed.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.size(60.dp)
            ) {
                Icon(Icons.Default.Warning, null, tint = ErrorRed, modifier = Modifier.padding(16.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(product.name, color = TextPrimary, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                Text(product.code, color = BrownPrimary, fontWeight = FontWeight.Medium, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Category, null, tint = TextSecondary, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(product.categoryName ?: "-", color = TextSecondary, fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.BrandingWatermark, null, tint = TextSecondary, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(product.brandName ?: "-", color = TextSecondary, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text("Stok Tersisa: ${product.stock} | Rak: ${product.shelfLocation ?: "-"}", color = ErrorRed, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

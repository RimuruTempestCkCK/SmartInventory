package com.example.smartinventory.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartinventory.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onLogout: () -> Unit,
    onMenuClick: (String) -> Unit,
    viewModel: DashboardViewModel = viewModel()
) {
    val stats by viewModel.stats.collectAsState()
    val prediction by viewModel.prediction.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SMART INVENTORY", fontWeight = FontWeight.ExtraBold, color = Color.White) },
                actions = { IconButton(onClick = onLogout) { Icon(Icons.Default.ExitToApp, "Keluar", tint = Color.White) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrownPrimary)
            )
        },
        containerColor = AppBackground
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            // 1. Welcome Card
            item {
                Card(modifier = Modifier.fillMaxWidth().height(120.dp), shape = RoundedCornerShape(20.dp)) {
                    Box(modifier = Modifier.fillMaxSize().background(Brush.horizontalGradient(listOf(BrownPrimary, BrownSecondary))).padding(20.dp)) {
                        Column {
                            Text("Selamat Datang,", color = Color.White, fontSize = 14.sp)
                            Text("RianPhoneShop Admin", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // 2. Info Cards (Monochrome Style)
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatBox("Barang", stats?.totalProducts?.toString() ?: "0", Modifier.weight(1f))
                    StatBox("Brands", stats?.totalSuppliers?.toString() ?: "0", Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // 3. AI Prediction Summary
            item {
                Text("ANALISIS PREDIKSI STOK (AI)", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = AppSurface), elevation = CardDefaults.cardElevation(2.dp)) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AutoAwesome, "AI", tint = BrownPrimary, modifier = Modifier.size(32.dp))
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(prediction?.result ?: "Menganalisis...", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text("Probabilitas: ${(prediction?.probability?.times(100))?.toInt()}%", color = TextSecondary, fontSize = 12.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // 4. Notifikasi Stok Menipis
            if (stats?.lowStockProducts?.isNotEmpty() == true) {
                item {
                    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = ErrorRed.copy(alpha = 0.1f))) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Warning, "Peringatan", tint = ErrorRed)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("${stats?.lowStockProducts?.size} Barang stok menipis!", color = ErrorRed, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            // 5. Menu Grid
            item {
                Text("MENU UTAMA", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.height(300.dp)) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item { SimpleMenu("Barang", Icons.Default.Inventory) { onMenuClick("barang") } }
                        item { SimpleMenu("Brands", Icons.Default.Category) { onMenuClick("kategori") } }
                        item { SimpleMenu("Masuk", Icons.Default.AddCircle) { onMenuClick("stok_masuk") } }
                        item { SimpleMenu("Keluar", Icons.Default.RemoveCircle) { onMenuClick("stok_keluar") } }
                        item { SimpleMenu("Riwayat", Icons.Default.History) { onMenuClick("riwayat") } }
                        item { SimpleMenu("Prediksi", Icons.Default.AutoAwesome) { onMenuClick("prediksi") } }
                    }
                }
            }
        }
    }
}

@Composable
fun StatBox(title: String, value: String, modifier: Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = AppSurface), elevation = CardDefaults.cardElevation(2.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, color = TextSecondary, fontSize = 12.sp)
            Text(value, color = BrownPrimary, fontWeight = FontWeight.Bold, fontSize = 24.sp)
        }
    }
}

@Composable
fun SimpleMenu(title: String, icon: ImageVector, onClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.height(90.dp), colors = CardDefaults.cardColors(containerColor = AppSurface), elevation = CardDefaults.cardElevation(1.dp)) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, title, tint = BrownPrimary, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(title, color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Medium)
        }
    }
}

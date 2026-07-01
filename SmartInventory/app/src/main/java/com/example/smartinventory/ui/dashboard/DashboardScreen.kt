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
                Card(
                    modifier = Modifier.fillMaxWidth().height(140.dp),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize().background(Brush.linearGradient(listOf(BrownPrimary, BrownSecondary))).padding(24.dp)) {
                        Column(modifier = Modifier.align(Alignment.CenterStart)) {
                            Text("Smart Inventory System", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp, fontWeight = FontWeight.Medium)
                            Text("Halo, Admin", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 26.sp)
                            Text("Kelola stok aksesoris dengan mudah", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                        }
                        Icon(
                            Icons.Default.Inventory2, 
                            null, 
                            tint = Color.White.copy(alpha = 0.2f),
                            modifier = Modifier.size(100.dp).align(Alignment.CenterEnd).offset(x = 20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // 2. Info Cards (Refined)
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatBox("Total Produk", stats?.totalProducts?.toString() ?: "0", Icons.Default.Inventory, Modifier.weight(1f))
                    StatBox("Total Brand", stats?.totalBrands?.toString() ?: "0", Icons.Default.BrandingWatermark, Modifier.weight(1f))
                    StatBox("Supplier", stats?.totalSuppliers?.toString() ?: "0", Icons.Default.LocalShipping, Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatBox("Masuk Hari Ini", stats?.stockInToday?.toString() ?: "0", Icons.Default.AddBox, Modifier.weight(1f), SuccessGreen)
                    StatBox("Keluar Hari Ini", stats?.stockOutToday?.toString() ?: "0", Icons.Default.IndeterminateCheckBox, Modifier.weight(1f), ErrorRed)
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // 3. AI Prediction Summary (Enhanced)
            item {
                Text("AI INSIGHTS", color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = BrownAccent.copy(alpha = 0.3f)),
                    shape = RoundedCornerShape(20.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BrownPrimary.copy(alpha = 0.2f))
                ) {
                    Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = BrownPrimary,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(Icons.Default.AutoAwesome, "AI", tint = Color.White, modifier = Modifier.padding(10.dp))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(prediction?.result ?: "Menganalisis stok...", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text("Akurasi Prediksi: ${(prediction?.probability?.times(100))?.toInt()}%", color = TextSecondary, fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = { onMenuClick("prediksi") }) {
                            Icon(Icons.Default.ChevronRight, null, tint = BrownPrimary)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // 4. Notifikasi Stok Menipis (Professional Alert)
            if (stats?.lowStockProducts?.isNotEmpty() == true) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = ErrorRed.copy(alpha = 0.05f)),
                        shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, ErrorRed.copy(alpha = 0.2f))
                    ) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Warning, "Peringatan", tint = ErrorRed, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("${stats?.lowStockProducts?.size} item butuh restock segera!", color = ErrorRed, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            // 5. Menu Grid (Optimized)
            item {
                MenuSection("MASTER DATA") {
                    MenuRow(
                        listOf(
                            Triple("Barang", Icons.Default.Inventory, "barang"),
                            Triple("Kategori", Icons.Default.Category, "kategori"),
                            Triple("Brand", Icons.Default.BrandingWatermark, "brand")
                        ), onMenuClick
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    MenuRow(
                        listOf(
                            Triple("Supplier", Icons.Default.LocalShipping, "supplier"),
                            null,
                            null
                        ), onMenuClick
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                MenuSection("TRANSAKSI STOK") {
                    MenuRow(
                        listOf(
                            Triple("Stok Masuk", Icons.Default.AddCircle, "stok_masuk"),
                            Triple("Stok Keluar", Icons.Default.RemoveCircle, "stok_keluar"),
                            Triple("Riwayat", Icons.Default.History, "riwayat")
                        ), onMenuClick
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                MenuSection("MONITORING & LAPORAN") {
                    MenuRow(
                        listOf(
                            Triple("Monitor", Icons.Default.MonitorHeart, "monitoring"),
                            Triple("Cari Barang", Icons.Default.Search, "search"),
                            Triple("Notifikasi", Icons.Default.NotificationsActive, "monitoring")
                        ), onMenuClick
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    MenuRow(
                        listOf(
                            Triple("Laporan", Icons.Default.Assessment, "laporan"),
                            null,
                            null
                        ), onMenuClick
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                MenuSection("PENGATURAN") {
                    MenuRow(
                        listOf(
                            Triple("Manajemen User", Icons.Default.People, "users"),
                            null,
                            null
                        ), onMenuClick
                    )
                }
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun MenuSection(title: String, content: @Composable () -> Unit) {
    Column {
        Text(title, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp)
        Spacer(modifier = Modifier.height(12.dp))
        content()
    }
}

@Composable
fun MenuRow(items: List<Triple<String, ImageVector, String>?>, onMenuClick: (String) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items.forEach { item ->
            if (item != null) {
                SimpleMenu(item.first, item.second, Modifier.weight(1f)) { onMenuClick(item.third) }
            } else {
                Box(Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun StatBox(title: String, value: String, icon: ImageVector, modifier: Modifier, color: Color = BrownPrimary) {
    Card(
        modifier = modifier, 
        colors = CardDefaults.cardColors(containerColor = AppSurface), 
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icon, null, tint = color.copy(alpha = 0.6f), modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Medium)
            Text(value, color = color, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp)
        }
    }
}

@Composable
fun SimpleMenu(title: String, icon: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        onClick = onClick, 
        modifier = modifier.height(100.dp), 
        colors = CardDefaults.cardColors(containerColor = AppSurface), 
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(0.5.dp, DividerColor)
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                color = BrownPrimary.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.size(40.dp)
            ) {
                Icon(icon, title, tint = BrownPrimary, modifier = Modifier.padding(8.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}


package com.example.smartinventory.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
    
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = when(selectedTab) {
                            0 -> "DASHBOARD"
                            1 -> "MASTER DATA"
                            2 -> "TRANSAKSI STOK"
                            else -> "LAPORAN & MONITORING"
                        }, 
                        fontWeight = FontWeight.ExtraBold, 
                        color = Color.White
                    ) 
                },
                actions = { 
                    IconButton(onClick = onLogout) { 
                        Icon(Icons.Default.ExitToApp, "Keluar", tint = Color.White) 
                    } 
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrownPrimary)
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = AppSurface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Dashboard, contentDescription = "Dashboard") },
                    label = { Text("Home", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = BrownPrimary,
                        indicatorColor = BrownPrimary,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.Layers, contentDescription = "Master") },
                    label = { Text("Master", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = BrownPrimary,
                        indicatorColor = BrownPrimary,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.SwapVert, contentDescription = "Stok") },
                    label = { Text("Stok", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = BrownPrimary,
                        indicatorColor = BrownPrimary,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = { Icon(Icons.Default.Assessment, contentDescription = "Laporan") },
                    label = { Text("Laporan", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = BrownPrimary,
                        indicatorColor = BrownPrimary,
                        unselectedIconColor = TextSecondary,
                        unselectedTextColor = TextSecondary
                    )
                )
            }
        },
        containerColor = AppBackground
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when (selectedTab) {
                0 -> DashboardHomeTab(
                    stats = stats,
                    prediction = prediction,
                    isLoading = isLoading,
                    onMenuClick = onMenuClick,
                    onRefreshPrediction = { viewModel.refreshData() }
                )
                1 -> MasterDataTab(onMenuClick = onMenuClick)
                2 -> TransactionTab(onMenuClick = onMenuClick)
                3 -> ReportTab(stats = stats, onMenuClick = onMenuClick)
            }
        }
    }
}

@Composable
fun DashboardHomeTab(
    stats: com.example.smartinventory.data.model.DashboardStats?,
    prediction: com.example.smartinventory.data.model.PredictionResponse?,
    isLoading: Boolean,
    onMenuClick: (String) -> Unit,
    onRefreshPrediction: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Welcome Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.linearGradient(listOf(BrownPrimary, BrownSecondary)))
                        .padding(20.dp)
                ) {
                    Column(modifier = Modifier.align(Alignment.CenterStart)) {
                        Text(
                            "Smart Inventory System",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            "Halo, Admin & Staff",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 24.sp
                        )
                        Text(
                            "Kelola stok aksesoris dengan mudah & cepat",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                    }
                    Icon(
                        Icons.Default.Inventory2,
                        null,
                        tint = Color.White.copy(alpha = 0.15f),
                        modifier = Modifier
                            .size(90.dp)
                            .align(Alignment.CenterEnd)
                            .offset(x = 10.dp)
                    )
                }
            }
        }

        // 2. Info Cards
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatBox("Total Produk", stats?.totalProducts?.toString() ?: "0", Icons.Default.Inventory, Modifier.weight(1f))
                    StatBox("Total Brand", stats?.totalBrands?.toString() ?: "0", Icons.Default.BrandingWatermark, Modifier.weight(1f))
                    StatBox("Supplier", stats?.totalSuppliers?.toString() ?: "0", Icons.Default.LocalShipping, Modifier.weight(1f))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatBox("Masuk Hari Ini", stats?.stockInToday?.toString() ?: "0", Icons.Default.AddBox, Modifier.weight(1f), SuccessGreen)
                    StatBox("Keluar Hari Ini", stats?.stockOutToday?.toString() ?: "0", Icons.Default.IndeterminateCheckBox, Modifier.weight(1f), ErrorRed)
                }
            }
        }

        // 3. AI Prediction Summary
        item {
            Column {
                Text(
                    "AI INSIGHTS (PREDIKSI STOK)",
                    color = TextPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = BrownAccent.copy(alpha = 0.25f)),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BrownPrimary.copy(alpha = 0.15f))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = BrownPrimary,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                Icons.Default.AutoAwesome,
                                "AI",
                                tint = Color.White,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            val predictionResult = prediction?.result ?: "Menganalisis stok..."
                            val accuracyPercent = if (prediction?.probability != null) {
                                "${(prediction.probability * 100).toInt()}%"
                            } else {
                                "..."
                            }
                            Text(
                                predictionResult,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Text(
                                "Akurasi Prediksi: $accuracyPercent",
                                color = TextSecondary,
                                fontSize = 12.sp
                            )
                            
                            if (prediction != null) {
                                Divider(modifier = Modifier.padding(vertical = 8.dp), color = BrownPrimary.copy(alpha = 0.1f))
                                Text("Data Historis: ${prediction.historicalData ?: "-"}", color = TextSecondary, fontSize = 11.sp)
                                Text("Status: ${prediction.safetyStatus ?: "-"}", color = TextSecondary, fontSize = 11.sp)
                                
                                if (!prediction.fastDepleting.isNullOrEmpty()) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Prediksi Cepat Habis:", color = ErrorRed, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                    prediction.fastDepleting.forEach { item ->
                                        Text("• $item", color = TextSecondary, fontSize = 10.sp)
                                    }
                                }
                            }
                        }
                        IconButton(onClick = onRefreshPrediction) {
                            Icon(Icons.Default.Refresh, "Analisis Ulang", tint = BrownPrimary)
                        }
                        IconButton(onClick = { onMenuClick("prediksi") }) {
                            Icon(Icons.Default.ChevronRight, null, tint = BrownPrimary)
                        }
                    }
                }
            }
        }

        // 4. Notifikasi Stok Menipis
        if (stats?.lowStockProducts?.isNotEmpty() == true) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = ErrorRed.copy(alpha = 0.05f)),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ErrorRed.copy(alpha = 0.2f))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Warning,
                            "Peringatan",
                            tint = ErrorRed,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "${stats.lowStockProducts.size} item mendekati batas minimum stok!",
                            color = ErrorRed,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MasterDataTab(onMenuClick: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                "MASTER DATA MANAGEMENT",
                color = TextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Kelola data utama inventaris barang dan relasi databasenya.",
                color = TextSecondary,
                fontSize = 12.sp
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SimpleMenu("Data Barang", Icons.Default.Inventory, Modifier.weight(1f)) {
                        onMenuClick("barang")
                    }
                    SimpleMenu("Kategori", Icons.Default.Category, Modifier.weight(1f)) {
                        onMenuClick("kategori")
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SimpleMenu("Supplier", Icons.Default.LocalShipping, Modifier.weight(1f)) {
                        onMenuClick("supplier")
                    }
                    SimpleMenu("Brand / Merk", Icons.Default.BrandingWatermark, Modifier.weight(1f)) {
                        onMenuClick("brand")
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SimpleMenu("Manajemen User", Icons.Default.People, Modifier.weight(1f)) {
                        onMenuClick("users")
                    }
                    Box(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun TransactionTab(onMenuClick: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                "TRANSAKSI STOK IN & OUT",
                color = TextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Catat alur masuk dan keluar barang serta pantau riwayat aktivitas.",
                color = TextSecondary,
                fontSize = 12.sp
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SimpleMenu("Stok Masuk", Icons.Default.AddCircle, Modifier.weight(1f)) {
                        onMenuClick("stok_masuk")
                    }
                    SimpleMenu("Stok Keluar", Icons.Default.RemoveCircle, Modifier.weight(1f)) {
                        onMenuClick("stok_keluar")
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SimpleMenu("Riwayat Transaksi", Icons.Default.History, Modifier.weight(1f)) {
                        onMenuClick("riwayat")
                    }
                    Box(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun ReportTab(
    stats: com.example.smartinventory.data.model.DashboardStats?,
    onMenuClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                "MONITORING & LAPORAN",
                color = TextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Pantau ketersediaan barang secara realtime dan cetak laporan stok.",
                color = TextSecondary,
                fontSize = 12.sp
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SimpleMenu("Laporan Stok", Icons.Default.Assessment, Modifier.weight(1f)) {
                        onMenuClick("laporan")
                    }
                    SimpleMenu("Monitoring Stok", Icons.Default.MonitorHeart, Modifier.weight(1f)) {
                        onMenuClick("monitoring")
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SimpleMenu("Cari Barang", Icons.Default.Search, Modifier.weight(1f)) {
                        onMenuClick("search")
                    }
                    SimpleMenu(
                        title = "Notifikasi (" + (stats?.lowStockProducts?.size ?: 0) + ")",
                        icon = Icons.Default.NotificationsActive,
                        modifier = Modifier.weight(1f)
                    ) {
                        onMenuClick("monitoring")
                    }
                }
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


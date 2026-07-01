package com.example.smartinventory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartinventory.preference.PreferenceManager
import com.example.smartinventory.ui.barang.ProductScreen
import com.example.smartinventory.ui.dashboard.DashboardScreen
import com.example.smartinventory.ui.kategori.CategoryScreen
import com.example.smartinventory.ui.login.LoginScreen
import com.example.smartinventory.ui.prediksi.PredictionScreen
import com.example.smartinventory.ui.report.ReportScreen
import com.example.smartinventory.ui.stok.HistoryScreen
import com.example.smartinventory.ui.stok.StockInScreen
import com.example.smartinventory.ui.stok.StockOutScreen
import com.example.smartinventory.ui.supplier.SupplierScreen
import com.example.smartinventory.ui.theme.SmartInventoryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartInventoryTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val prefManager = PreferenceManager(context)
    
    val startDestination = if (prefManager.isLoggedIn()) "dashboard" else "login"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("dashboard") {
            DashboardScreen(
                onLogout = {
                    prefManager.clear()
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                },
                onMenuClick = { menu ->
                    navController.navigate(menu)
                }
            )
        }
        composable("barang") { ProductScreen(onBack = { navController.popBackStack() }) }
        composable("kategori") { CategoryScreen(onBack = { navController.popBackStack() }) }
        composable("supplier") { SupplierScreen(onBack = { navController.popBackStack() }) }
        composable("stok_masuk") { StockInScreen(onBack = { navController.popBackStack() }) }
        composable("stok_keluar") { StockOutScreen(onBack = { navController.popBackStack() }) }
        composable("riwayat") { HistoryScreen(onBack = { navController.popBackStack() }) }
        composable("prediksi") { PredictionScreen(onBack = { navController.popBackStack() }) }
        composable("laporan") { ReportScreen(onBack = { navController.popBackStack() }) }
    }
}

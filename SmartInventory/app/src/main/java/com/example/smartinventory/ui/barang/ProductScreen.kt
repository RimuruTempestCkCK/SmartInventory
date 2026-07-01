package com.example.smartinventory.ui.barang

import androidx.compose.foundation.background
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
import com.example.smartinventory.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    onBack: () -> Unit,
    viewModel: ProductViewModel = viewModel()
) {
    val products by viewModel.products.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val brands by viewModel.brands.collectAsState()
    val suppliers by viewModel.suppliers.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val message by viewModel.message.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var searchQuery by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    
    var code by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var selectedCatId by remember { mutableStateOf("") }
    var selectedBrandId by remember { mutableStateOf("") }
    var selectedSupplierId by remember { mutableStateOf("") }
    var buyPrice by remember { mutableStateOf("") }
    var sellPrice by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var rak by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var condition by remember { mutableStateOf("Baru") }

    val filteredProducts = products.filter { 
        it.name.contains(searchQuery, ignoreCase = true) || it.code.contains(searchQuery, ignoreCase = true)
    }

    LaunchedEffect(message) {
        message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMessage()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Surface(
                color = BrownPrimary,
                shadowElevation = 8.dp
            ) {
                Column(modifier = Modifier.padding(bottom = 8.dp)) {
                    TopAppBar(
                        title = { Text("MANAJEMEN BARANG", fontWeight = FontWeight.ExtraBold, color = Color.White, fontSize = 20.sp) },
                        navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back", tint = Color.White) } },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                    )
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Cari kode atau nama barang...", color = Color.White.copy(alpha = 0.6f), fontSize = 14.sp) },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        leadingIcon = { Icon(Icons.Default.Search, null, tint = Color.White) },
                        trailingIcon = if (searchQuery.isNotEmpty()) {
                            { IconButton(onClick = { searchQuery = "" }) { Icon(Icons.Default.Close, null, tint = Color.White) } }
                        } else null,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.4f),
                            cursorColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true
                    )
                }
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    selectedProduct = null
                    code = ""; name = ""; selectedCatId = ""; selectedBrandId = ""; selectedSupplierId = ""
                    buyPrice = ""; sellPrice = ""; stock = ""; rak = ""; color = ""; condition = "Baru"
                    showDialog = true
                },
                containerColor = BrownPrimary,
                contentColor = Color.White,
                icon = { Icon(Icons.Default.Add, null) },
                text = { Text("Produk Baru") }
            )
        },
        containerColor = AppBackground
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (isLoading && products.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = BrownPrimary)
            } else if (filteredProducts.isEmpty()) {
                Column(modifier = Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.SearchOff, null, modifier = Modifier.size(64.dp), tint = TextSecondary.copy(alpha = 0.5f))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Barang tidak ditemukan", color = TextSecondary)
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(filteredProducts) { product ->
                        ProductItem(product, onEdit = {
                            selectedProduct = product
                            code = product.code; name = product.name; selectedCatId = product.categoryId; selectedBrandId = product.brandId
                            selectedSupplierId = product.supplierId ?: ""
                            buyPrice = product.buyPrice.toInt().toString(); sellPrice = product.sellPrice.toInt().toString(); stock = product.stock.toString()
                            rak = product.shelfLocation ?: ""; color = product.color ?: ""; condition = product.condition ?: "Baru"
                            showDialog = true
                        }, onDelete = { viewModel.removeProduct(product.id ?: "") })
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            containerColor = MaterialTheme.colorScheme.surface,
            title = { Text(if (selectedProduct == null) "Tambah Barang" else "Edit Barang", color = TextPrimary, fontWeight = FontWeight.Bold) },
            text = {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    item { ProductField("Nama Barang", name) { name = it } }
                    item { ProductField("Kode Barang", code) { code = it } }
                    
                    item {
                        DropdownSelector("Pilih Kategori", selectedCatId, categories.map { it.id!! to it.name }) { selectedCatId = it }
                    }
                    item {
                        DropdownSelector("Pilih Merk/Brand", selectedBrandId, brands.map { it.id!! to it.name }) { selectedBrandId = it }
                    }
                    item {
                        DropdownSelector("Pilih Supplier", selectedSupplierId, suppliers.map { it.id!! to it.name }) { selectedSupplierId = it }
                    }

                    item { ProductField("Harga Beli", buyPrice) { buyPrice = it } }
                    item { ProductField("Harga Jual", sellPrice) { sellPrice = it } }
                    item { ProductField("Stok", stock) { stock = it } }
                    item { ProductField("Lokasi Rak", rak) { rak = it } }
                    item { ProductField("Warna", color) { color = it } }
                    item {
                        DropdownSelector("Kondisi", condition, listOf("Baru" to "Baru", "Second" to "Second")) { condition = it }
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    val bp = buyPrice.toDoubleOrNull() ?: 0.0
                    val sp = sellPrice.toDoubleOrNull() ?: 0.0
                    val s = stock.toIntOrNull() ?: 0
                    if (selectedProduct == null) viewModel.addProduct(code, name, selectedCatId, selectedBrandId, selectedSupplierId, bp, sp, s, rak, color, condition)
                    else viewModel.editProduct(selectedProduct!!.id!!, code, name, selectedCatId, selectedBrandId, selectedSupplierId, bp, sp, s, rak, color, condition)
                    showDialog = false
                }, shape = RoundedCornerShape(12.dp)) { Text("Simpan") }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(label: String, selectedId: String, items: List<Pair<String, String>>, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val selectedName = items.find { it.first == selectedId }?.second ?: label

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            value = selectedName, onValueChange = {}, readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEach { (id, name) ->
                DropdownMenuItem(
                    text = { Text(name) },
                    onClick = { onSelect(id); expanded = false }
                )
            }
        }
    }
}

@Composable
fun ProductField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value, onValueChange = onValueChange, label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
fun ProductItem(product: Product, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AppSurface),
        elevation = CardDefaults.cardElevation(2.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor)
    ) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Surface(
                color = BrownAccent.copy(alpha = 0.2f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.size(60.dp)
            ) {
                Icon(Icons.Default.Image, null, tint = BrownPrimary.copy(alpha = 0.5f), modifier = Modifier.padding(16.dp))
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
                Text("Stok: ${product.stock} | Rak: ${product.shelfLocation ?: "-"}", color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Text("Rp ${String.format("%,.0f", product.sellPrice)}", color = SuccessGreen, fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
            }
            Column {
                IconButton(onClick = onEdit) { Icon(Icons.Default.Edit, "Edit", tint = BrownPrimary) }
                IconButton(onClick = onDelete) { Icon(Icons.Default.Delete, "Hapus", tint = ErrorRed.copy(alpha = 0.7f)) }
            }
        }
    }
}

package com.example.smartinventory.ui.barang

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
    val suppliers by viewModel.suppliers.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    
    var code by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var catName by remember { mutableStateOf("") }
    var supName by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }

    val message by viewModel.message.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

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
            Column(modifier = Modifier.background(MaterialTheme.colorScheme.primary)) {
                TopAppBar(
                    title = { Text("PRODUK", fontWeight = FontWeight.ExtraBold, color = Color.White) },
                    navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back", tint = Color.White) } },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
                )
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Cari aksesoris...", color = Color.LightGray) },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    leadingIcon = { Icon(Icons.Default.Search, null, tint = Color.White) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White, unfocusedTextColor = Color.White,
                        focusedBorderColor = Color.White, unfocusedBorderColor = Color.White.copy(alpha = 0.5f)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                selectedProduct = null
                code = ""; name = ""; catName = ""; supName = ""; price = ""; stock = ""
                showDialog = true
            }, containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White) {
                Icon(Icons.Default.Add, "Tambah")
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (isLoading && products.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(filteredProducts) { product ->
                        ProductItem(product, onEdit = {
                            selectedProduct = product
                            code = product.code; name = product.name; catName = product.categoryName ?: ""
                            supName = product.supplierName ?: ""; price = product.price.toString(); stock = product.stock.toString()
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
                    item { ProductField("Kode Rak", code) { code = it } }
                    
                    // Dropdown untuk Kategori (Rak)
                    item {
                        var expanded by remember { mutableStateOf(false) }
                        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                            OutlinedTextField(
                                value = catName, onValueChange = {}, readOnly = true,
                                label = { Text("Pilih Lokasi Rak") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier.menuAnchor().fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )
                            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                categories.forEach { cat ->
                                    DropdownMenuItem(
                                        text = { Text(cat.name) },
                                        onClick = { catName = cat.name; expanded = false }
                                    )
                                }
                            }
                        }
                    }

                    // Dropdown untuk Merk/Supplier
                    item {
                        var expanded by remember { mutableStateOf(false) }
                        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                            OutlinedTextField(
                                value = supName, onValueChange = {}, readOnly = true,
                                label = { Text("Pilih Merk / Brand") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier.menuAnchor().fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )
                            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                suppliers.forEach { sup ->
                                    DropdownMenuItem(
                                        text = { Text(sup.name) },
                                        onClick = { supName = sup.name; expanded = false }
                                    )
                                }
                            }
                        }
                    }

                    item { ProductField("Harga Jual", price) { price = it } }
                    item { ProductField("Stok Awal", stock) { stock = it } }
                }
            },
            confirmButton = {
                Button(onClick = {
                    val p = price.toDoubleOrNull() ?: 0.0
                    val s = stock.toIntOrNull() ?: 0
                    
                    // Backend Anda menggunakan String (Varchar) untuk Merk dan Kategori.
                    // Jadi kita kirimkan Nama-nya langsung, bukan ID angkanya.
                    if (selectedProduct == null) {
                        viewModel.addProduct(code, name, catName, supName, p, s)
                    } else {
                        viewModel.editProduct(selectedProduct!!.id!!, code, name, catName, supName, p, s)
                    }
                    showDialog = false
                }, shape = RoundedCornerShape(12.dp)) { Text("Simpan") }
            }
        )
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
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), elevation = CardDefaults.cardElevation(2.dp)) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(product.name, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("${product.supplierName} • ${product.categoryName}", color = TextSecondary, fontSize = 12.sp)
                Text("Rak: ${product.code} | Stok: ${product.stock}", color = BrownPrimary, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Text("Rp ${String.format("%,.0f", product.price)}", color = SuccessGreen, fontWeight = FontWeight.Bold)
            }
            IconButton(onClick = onEdit) { Icon(Icons.Default.Edit, "Edit", tint = BrownSecondary) }
            IconButton(onClick = onDelete) { Icon(Icons.Default.Delete, "Hapus", tint = ErrorRed) }
        }
    }
}

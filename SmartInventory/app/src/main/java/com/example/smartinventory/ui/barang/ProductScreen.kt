package com.example.smartinventory.ui.barang

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

    var showDialog by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    
    var code by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var catId by remember { mutableStateOf("") }
    var supId by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PRODUCTS", fontWeight = FontWeight.ExtraBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DiscordCanvas, titleContentColor = Color.White)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                selectedProduct = null
                code = ""; name = ""; catId = ""; supId = ""; price = ""; stock = ""
                showDialog = true
            }, containerColor = DiscordBlurple, contentColor = Color.White) {
                Icon(Icons.Default.Add, contentDescription = "Add Product")
            }
        },
        containerColor = DiscordCanvas
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (isLoading && products.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = DiscordBlurple)
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(products) { product ->
                        ProductItem(product, onEdit = {
                            selectedProduct = product
                            code = product.code; name = product.name; catId = product.categoryId; supId = product.supplierId
                            price = product.price.toString(); stock = product.stock.toString()
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
            containerColor = DiscordSurfaceIndigo,
            title = { Text(if (selectedProduct == null) "Add Product" else "Edit Product", color = Color.White, fontWeight = FontWeight.Bold) },
            text = {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item { ProductField("Code", code) { code = it } }
                    item { ProductField("Name", name) { name = it } }
                    item { 
                        Text("Category", color = Color.Gray, fontSize = 12.sp)
                        categories.forEach { cat ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(selected = catId == cat.id, onClick = { catId = cat.id ?: "" })
                                Text(cat.name, color = Color.White)
                            }
                        }
                    }
                    item { 
                        Text("Supplier", color = Color.Gray, fontSize = 12.sp)
                        suppliers.forEach { sup ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(selected = supId == sup.id, onClick = { supId = sup.id ?: "" })
                                Text(sup.name, color = Color.White)
                            }
                        }
                    }
                    item { ProductField("Price", price) { price = it } }
                    item { ProductField("Stock", stock) { stock = it } }
                }
            },
            confirmButton = {
                Button(onClick = {
                    val p = price.toDoubleOrNull() ?: 0.0
                    val s = stock.toIntOrNull() ?: 0
                    if (selectedProduct == null) viewModel.addProduct(code, name, catId, supId, p, s)
                    else viewModel.editProduct(selectedProduct!!.id!!, code, name, catId, supId, p, s)
                    showDialog = false
                }, colors = ButtonDefaults.buttonColors(containerColor = DiscordBlurple)) { Text("Save") }
            }
        )
    }
}

@Composable
fun ProductField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value, onValueChange = onValueChange, label = { Text(label, color = Color.Gray) },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White, focusedBorderColor = DiscordBlurple, unfocusedBorderColor = Color.Gray)
    )
}

@Composable
fun ProductItem(product: Product, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = DiscordSurfaceIndigo)) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(product.name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("Code: ${product.code} | Stock: ${product.stock}", color = Color.LightGray, fontSize = 14.sp)
                Text("Price: Rp ${product.price}", color = DiscordGreen, fontWeight = FontWeight.SemiBold)
            }
            IconButton(onClick = onEdit) { Icon(Icons.Default.Edit, "Edit", tint = DiscordBlurple) }
            IconButton(onClick = onDelete) { Icon(Icons.Default.Delete, "Delete", tint = Color.Red) }
        }
    }
}

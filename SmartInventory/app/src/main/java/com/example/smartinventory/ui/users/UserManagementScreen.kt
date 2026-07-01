package com.example.smartinventory.ui.users

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
import com.example.smartinventory.data.model.User
import com.example.smartinventory.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserManagementScreen(
    onBack: () -> Unit,
    viewModel: UserViewModel = viewModel()
) {
    val users by viewModel.users.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val message by viewModel.message.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var showDialog by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf<User?>(null) }
    
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("admin") }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("MANAJEMEN USER", fontWeight = FontWeight.ExtraBold, color = Color.White) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back", tint = Color.White) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrownPrimary)
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    selectedUser = null
                    username = ""; password = ""; role = "admin"
                    showDialog = true
                },
                containerColor = BrownPrimary,
                contentColor = Color.White,
                icon = { Icon(Icons.Default.PersonAdd, null) },
                text = { Text("Tambah User") }
            )
        },
        containerColor = AppBackground
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (isLoading && users.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = BrownPrimary)
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(users) { user ->
                        UserItem(user, onEdit = {
                            selectedUser = user
                            username = user.username; password = ""; role = user.role ?: "admin"
                            showDialog = true
                        }, onDelete = { viewModel.removeUser(user.id ?: "") })
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            containerColor = MaterialTheme.colorScheme.surface,
            title = { Text(if (selectedUser == null) "Tambah User" else "Edit User", color = TextPrimary, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Username") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                    OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password (isi jika ingin ganti)") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                    
                    Text("Role", color = TextSecondary, fontSize = 12.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = role == "admin", onClick = { role = "admin" })
                        Text("Admin", color = TextPrimary)
                        Spacer(modifier = Modifier.width(16.dp))
                        RadioButton(selected = role == "staff", onClick = { role = "staff" })
                        Text("Staff", color = TextPrimary)
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (selectedUser == null) viewModel.addUser(username, password, role)
                    else viewModel.editUser(selectedUser!!.id!!, username, password, role)
                    showDialog = false
                }, shape = RoundedCornerShape(12.dp)) { Text("Simpan") }
            }
        )
    }

    LaunchedEffect(message) {
        message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMessage()
        }
    }
}

@Composable
fun UserItem(user: User, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AppSurface),
        elevation = CardDefaults.cardElevation(2.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor)
    ) {
        Row(modifier = Modifier.padding(20.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Surface(
                color = BrownPrimary.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.size(50.dp)
            ) {
                Icon(Icons.Default.AccountCircle, null, tint = BrownPrimary, modifier = Modifier.padding(10.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(user.username, color = TextPrimary, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                Surface(
                    color = if (user.role == "admin") BrownPrimary else BrownSecondary,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        user.role?.uppercase() ?: "STAFF", 
                        color = Color.White, 
                        fontSize = 10.sp, 
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }
            Row {
                IconButton(onClick = onEdit) { Icon(Icons.Default.Edit, "Edit", tint = BrownPrimary) }
                IconButton(onClick = onDelete) { Icon(Icons.Default.Delete, "Hapus", tint = ErrorRed.copy(alpha = 0.7f)) }
            }
        }
    }
}

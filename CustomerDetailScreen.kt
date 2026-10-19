package com.bombaywala.tailor.bharuch.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bombaywala.tailor.bharuch.viewmodel.TailorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerDetailScreen(
    navController: NavController,
    viewModel: TailorViewModel,
    customerId: Long
) {
    val context = LocalContext.current
    val customers by viewModel.customers.collectAsState()
    val customer = customers.find { it.id == customerId }
    val measurements by viewModel.getMeasurementsForCustomer(customerId).collectAsState(initial = emptyList())
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (customer == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Customer not found")
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(customer.name, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFFCA5A5))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1E293B))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF8FAFC))
        ) {
            // Customer Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(14.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = customer.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color(0xFF0F172A)
                        )
                        Row {
                            IconButton(onClick = {
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${customer.mobileNumber}"))
                                context.startActivity(intent)
                            }) {
                                Icon(Icons.Default.Phone, contentDescription = "Call", tint = Color(0xFF16A34A))
                            }
                        }
                    }

                    Text("Mobile: ${customer.mobileNumber}", color = Color(0xFF475569), fontSize = 14.sp)
                    if (customer.address.isNotBlank()) {
                        Text("Address: ${customer.address}", color = Color(0xFF64748B), fontSize = 13.sp)
                    }
                    if (customer.notes.isNotBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Notes: ${customer.notes}", color = Color(0xFFD97706), fontSize = 12.sp)
                    }
                }
            }

            // Action Buttons to Add Measurements
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { navController.navigate("add_measurement/${customer.id}/gents") },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E3A8A)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Straighten, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("+ Gents", fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = { navController.navigate("add_measurement/${customer.id}/ladies") },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9D174D)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Straighten, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("+ Ladies", fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Measurement History (${measurements.size})",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF1E293B),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // Measurement Records List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(measurements, key = { it.id }) { record ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = record.garmentType,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = if (record.gender == "ladies") Color(0xFF9D174D) else Color(0xFF1E3A8A)
                                )
                                Text(text = record.date, fontSize = 12.sp, color = Color(0xFF64748B))
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            if (record.gender == "ladies") {
                                Text("Bust: ${record.bust}\" | Waist: ${record.waist}\" | Hip: ${record.hip}\" | Shoulder: ${record.shoulder}\"", fontSize = 13.sp)
                                Text("Sleeve: ${record.sleeveLength}\" | Blouse L: ${record.blouseLength}\" | Kameez L: ${record.kameezLength}\"", fontSize = 13.sp)
                                if (record.neckDesignNotes.isNotBlank()) {
                                    Text("Neck: ${record.neckDesignNotes}", fontSize = 12.sp, color = Color(0xFF64748B))
                                }
                            } else {
                                Text("Chest: ${record.chest}\" | Waist: ${record.waist}\" | Hip: ${record.hip}\" | Shoulder: ${record.shoulder}\"", fontSize = 13.sp)
                                Text("Sleeve: ${record.sleeveLength}\" | Shirt L: ${record.shirtLength}\" | Trouser L: ${record.trouserLength}\" | Neck: ${record.neck}\"", fontSize = 13.sp)
                            }

                            if (record.specialNotes.isNotBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Note: ${record.specialNotes}", fontSize = 12.sp, color = Color(0xFFD97706))
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Customer?") },
            text = { Text("Are you sure you want to delete ${customer.name} and all associated measurement records? This cannot be undone.") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteCustomer(customer)
                    showDeleteDialog = false
                    navController.popBackStack()
                }) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

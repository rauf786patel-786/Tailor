package com.bombaywala.tailor.bharuch.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bombaywala.tailor.bharuch.viewmodel.TailorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditCustomerScreen(
    navController: NavController,
    viewModel: TailorViewModel,
    existingCustomerId: Long = 0L,
    initialName: String = "",
    initialMobile: String = "",
    initialAddress: String = "",
    initialNotes: String = ""
) {
    var name by remember { mutableStateOf(initialName) }
    var mobile by remember { mutableStateOf(initialMobile) }
    var address by remember { mutableStateOf(initialAddress) }
    var notes by remember { mutableStateOf(initialNotes) }

    var nameError by remember { mutableStateOf(false) }
    var mobileError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (existingCustomerId == 0L) "New Customer Registration" else "Edit Customer Record", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    if (it.isNotBlank()) nameError = false
                },
                label = { Text("Customer Name *") },
                isError = nameError,
                supportingText = { if (nameError) Text("Name is required", color = MaterialTheme.colorScheme.error) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )

            OutlinedTextField(
                value = mobile,
                onValueChange = {
                    val digits = it.filter { char -> char.isDigit() }.take(10)
                    mobile = digits
                    if (digits.length == 10) mobileError = false
                },
                label = { Text("Mobile Number (10 Digits) *") },
                isError = mobileError,
                supportingText = {
                    if (mobileError) {
                        Text("Please enter a valid 10-digit mobile number", color = MaterialTheme.colorScheme.error)
                    } else {
                        Text("E.g. 98250 12345 (Bharuch local / WhatsApp)")
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Address / Area in Bharuch") },
                placeholder = { Text("E.g. Station Road, Zadeshwar, Bholav, Sevashram...") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                shape = RoundedCornerShape(10.dp)
            )

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Customer Notes & Preferences") },
                placeholder = { Text("E.g. Prefers loose fit, urgent delivery, regular customer") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    var hasError = false
                    if (name.isBlank()) {
                        nameError = true
                        hasError = true
                    }
                    if (mobile.length != 10) {
                        mobileError = true
                        hasError = true
                    }

                    if (!hasError) {
                        viewModel.saveCustomer(name, mobile, address, notes, existingCustomerId) { id ->
                            navController.popBackStack()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD97706)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Check, contentDescription = "Save")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Save Customer", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

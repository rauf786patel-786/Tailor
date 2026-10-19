package com.bombaywala.tailor.bharuch.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bombaywala.tailor.bharuch.data.model.MeasurementRecord
import com.bombaywala.tailor.bharuch.viewmodel.TailorViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeasurementFormScreen(
    navController: NavController,
    viewModel: TailorViewModel,
    customerId: Long,
    initialGender: String
) {
    var selectedGender by remember { mutableStateOf(initialGender) }
    var garmentType by remember { mutableStateOf(if (initialGender == "ladies") "Salwar Suit" else "Shirt & Trouser") }

    // Ladies measurement state
    var bust by remember { mutableStateOf("") }
    var waistLadies by remember { mutableStateOf("") }
    var hipLadies by remember { mutableStateOf("") }
    var shoulderLadies by remember { mutableStateOf("") }
    var sleeveLengthLadies by remember { mutableStateOf("") }
    var blouseLength by remember { mutableStateOf("") }
    var kameezLength by remember { mutableStateOf("") }
    var salwarLength by remember { mutableStateOf("") }
    var neckDesignNotes by remember { mutableStateOf("") }

    // Gents measurement state
    var chest by remember { mutableStateOf("") }
    var waistGents by remember { mutableStateOf("") }
    var hipGents by remember { mutableStateOf("") }
    var shoulderGents by remember { mutableStateOf("") }
    var sleeveLengthGents by remember { mutableStateOf("") }
    var shirtLength by remember { mutableStateOf("") }
    var trouserLength by remember { mutableStateOf("") }
    var neck by remember { mutableStateOf("") }

    // Common
    var specialNotes by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (selectedGender == "ladies") "Ladies Measurements" else "Gents Measurements", color = Color.White) },
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Gender Switcher Tabs
            TabRow(
                selectedTabIndex = if (selectedGender == "ladies") 0 else 1,
                containerColor = Color.White,
                shape = RoundedCornerShape(10.dp)
            ) {
                Tab(
                    selected = selectedGender == "ladies",
                    onClick = {
                        selectedGender = "ladies"
                        garmentType = "Salwar Suit"
                    },
                    text = { Text("Ladies (સ્ત્રીઓ)") }
                )
                Tab(
                    selected = selectedGender == "gents",
                    onClick = {
                        selectedGender = "gents"
                        garmentType = "Shirt & Trouser"
                    },
                    text = { Text("Gents (પુરુષો)") }
                )
            }

            OutlinedTextField(
                value = garmentType,
                onValueChange = { garmentType = it },
                label = { Text("Garment / Dress Type") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )

            if (selectedGender == "ladies") {
                Text("Ladies Body Measurements (Inches)", fontWeight = FontWeight.Bold, color = Color(0xFF9D174D), fontSize = 15.sp)

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(value = bust, onValueChange = { bust = it }, label = { Text("Bust") }, modifier = Modifier.weight(1f))
                    OutlinedTextField(value = waistLadies, onValueChange = { waistLadies = it }, label = { Text("Waist") }, modifier = Modifier.weight(1f))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(value = hipLadies, onValueChange = { hipLadies = it }, label = { Text("Hip") }, modifier = Modifier.weight(1f))
                    OutlinedTextField(value = shoulderLadies, onValueChange = { shoulderLadies = it }, label = { Text("Shoulder") }, modifier = Modifier.weight(1f))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(value = sleeveLengthLadies, onValueChange = { sleeveLengthLadies = it }, label = { Text("Sleeve Length") }, modifier = Modifier.weight(1f))
                    OutlinedTextField(value = blouseLength, onValueChange = { blouseLength = it }, label = { Text("Blouse Length") }, modifier = Modifier.weight(1f))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(value = kameezLength, onValueChange = { kameezLength = it }, label = { Text("Kameez Length") }, modifier = Modifier.weight(1f))
                    OutlinedTextField(value = salwarLength, onValueChange = { salwarLength = it }, label = { Text("Salwar Length") }, modifier = Modifier.weight(1f))
                }
                OutlinedTextField(
                    value = neckDesignNotes,
                    onValueChange = { neckDesignNotes = it },
                    label = { Text("Neck Design Notes (Front / Back / Pattern)") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )
            } else {
                Text("Gents Body Measurements (Inches)", fontWeight = FontWeight.Bold, color = Color(0xFF1E3A8A), fontSize = 15.sp)

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(value = chest, onValueChange = { chest = it }, label = { Text("Chest") }, modifier = Modifier.weight(1f))
                    OutlinedTextField(value = waistGents, onValueChange = { waistGents = it }, label = { Text("Waist") }, modifier = Modifier.weight(1f))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(value = hipGents, onValueChange = { hipGents = it }, label = { Text("Hip") }, modifier = Modifier.weight(1f))
                    OutlinedTextField(value = shoulderGents, onValueChange = { shoulderGents = it }, label = { Text("Shoulder") }, modifier = Modifier.weight(1f))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(value = sleeveLengthGents, onValueChange = { sleeveLengthGents = it }, label = { Text("Sleeve Length") }, modifier = Modifier.weight(1f))
                    OutlinedTextField(value = neck, onValueChange = { neck = it }, label = { Text("Neck (Collar)") }, modifier = Modifier.weight(1f))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(value = shirtLength, onValueChange = { shirtLength = it }, label = { Text("Shirt Length") }, modifier = Modifier.weight(1f))
                    OutlinedTextField(value = trouserLength, onValueChange = { trouserLength = it }, label = { Text("Trouser Length") }, modifier = Modifier.weight(1f))
                }
            }

            OutlinedTextField(
                value = specialNotes,
                onValueChange = { specialNotes = it },
                label = { Text("Special Measurement Notes & Fitting Style") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Button(
                onClick = {
                    val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                    val record = MeasurementRecord(
                        customerId = customerId,
                        date = today,
                        garmentType = garmentType.ifBlank { if (selectedGender == "ladies") "Ladies Dress" else "Gents Suit" },
                        gender = selectedGender,
                        unit = "inches",
                        bust = bust,
                        waist = if (selectedGender == "ladies") waistLadies else waistGents,
                        hip = if (selectedGender == "ladies") hipLadies else hipGents,
                        shoulder = if (selectedGender == "ladies") shoulderLadies else shoulderGents,
                        sleeveLength = if (selectedGender == "ladies") sleeveLengthLadies else sleeveLengthGents,
                        blouseLength = blouseLength,
                        kameezLength = kameezLength,
                        salwarLength = salwarLength,
                        neckDesignNotes = neckDesignNotes,
                        chest = chest,
                        shirtLength = shirtLength,
                        trouserLength = trouserLength,
                        neck = neck,
                        specialNotes = specialNotes
                    )
                    viewModel.saveMeasurement(record) {
                        navController.popBackStack()
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
                Text("Save Measurement Record", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

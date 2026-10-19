package com.bombaywala.tailor.bharuch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bombaywala.tailor.bharuch.ui.screens.AddEditCustomerScreen
import com.bombaywala.tailor.bharuch.ui.screens.CustomerDetailScreen
import com.bombaywala.tailor.bharuch.ui.screens.HomeScreen
import com.bombaywala.tailor.bharuch.ui.screens.MeasurementFormScreen
import com.bombaywala.tailor.bharuch.ui.theme.BombaywalaTailorTheme
import com.bombaywala.tailor.bharuch.viewmodel.TailorViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BombaywalaTailorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel: TailorViewModel = viewModel()

                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            HomeScreen(navController = navController, viewModel = viewModel)
                        }
                        composable("add_customer") {
                            AddEditCustomerScreen(navController = navController, viewModel = viewModel)
                        }
                        composable("customer_detail/{customerId}") { backStackEntry ->
                            val customerId = backStackEntry.arguments?.getString("customerId")?.toLongOrNull() ?: 0L
                            CustomerDetailScreen(navController = navController, viewModel = viewModel, customerId = customerId)
                        }
                        composable("add_measurement/{customerId}/{gender}") { backStackEntry ->
                            val customerId = backStackEntry.arguments?.getString("customerId")?.toLongOrNull() ?: 0L
                            val gender = backStackEntry.arguments?.getString("gender") ?: "gents"
                            MeasurementFormScreen(navController = navController, viewModel = viewModel, customerId = customerId, initialGender = gender)
                        }
                    }
                }
            }
        }
    }
}

package com.synergeticsciences.emicalculator.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synergeticsciences.emicalculator.ui.components.GradientButton
import com.synergeticsciences.emicalculator.ui.components.NeonOutlinedTextField
import com.synergeticsciences.emicalculator.ui.theme.*
import com.synergeticsciences.emicalculator.ui.viewmodel.EMIViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: EMIViewModel,
    onNavigateToResult: () -> Unit,
    onNavigateToHistory: () -> Unit
) {
    val loanAmount by viewModel.loanAmount.collectAsState()
    val interestRate by viewModel.interestRate.collectAsState()
    val tenureYears by viewModel.tenureYears.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val selectedCurrency by viewModel.selectedCurrency.collectAsState()
    
    // Navigate to result screen when calculation is complete (one-time event)
    LaunchedEffect(Unit) {
        viewModel.navigateToResult.collect {
            onNavigateToResult()
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "EMI Calculator",
                        style = MaterialTheme.typography.headlineLarge,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Calculate your loan EMI",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
                
                IconButton(
                    onClick = onNavigateToHistory,
                    modifier = Modifier
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(GradientStart, GradientEnd)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = "History",
                        tint = TextPrimary
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
            
            // Input Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = DarkCard
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Loan Details",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Loan Amount
                    NeonOutlinedTextField(
                        value = loanAmount,
                        onValueChange = { viewModel.updateLoanAmount(it) },
                        label = "Loan Amount",
                        prefix = selectedCurrency.symbol,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    // Interest Rate
                    NeonOutlinedTextField(
                        value = interestRate,
                        onValueChange = { viewModel.updateInterestRate(it) },
                        label = "Interest Rate (p.a.)",
                        suffix = "%",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    // Tenure
                    NeonOutlinedTextField(
                        value = tenureYears,
                        onValueChange = { viewModel.updateTenureYears(it) },
                        label = "Loan Tenure",
                        suffix = "Years",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Error message
            AnimatedVisibility(
                visible = errorMessage != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = ErrorRed.copy(alpha = 0.2f)
                    )
                ) {
                    Text(
                        text = errorMessage ?: "",
                        color = ErrorRed,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Calculate Button
            GradientButton(
                text = "Calculate EMI",
                icon = Icons.Default.Calculate,
                onClick = { viewModel.calculateEMI() },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Quick Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = DarkCard.copy(alpha = 0.5f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "💡 Quick Tip",
                        style = MaterialTheme.typography.titleMedium,
                        color = NeonCyan,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "A lower interest rate or longer tenure will reduce your monthly EMI, but may increase the total interest paid.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}


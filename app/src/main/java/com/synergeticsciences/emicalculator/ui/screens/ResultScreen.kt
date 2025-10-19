package com.synergeticsciences.emicalculator.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.synergeticsciences.emicalculator.ui.components.GradientButton
import com.synergeticsciences.emicalculator.ui.components.PieChart
import com.synergeticsciences.emicalculator.ui.components.PieChartData
import com.synergeticsciences.emicalculator.ui.theme.*
import com.synergeticsciences.emicalculator.ui.viewmodel.EMIViewModel
import com.synergeticsciences.emicalculator.utils.EMICalculator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    viewModel: EMIViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToAmortization: () -> Unit
) {
    val emiResult by viewModel.emiResult.collectAsState()
    val selectedCurrency by viewModel.selectedCurrency.collectAsState()
    
    // Animation
    var startAnimation by remember { mutableStateOf(false) }
    val animatedScale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )
    
    LaunchedEffect(Unit) {
        startAnimation = true
    }
    
    if (emiResult == null) {
        // If no result, go back
        LaunchedEffect(Unit) {
            onNavigateBack()
        }
        return
    }
    
    val result = emiResult!!
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Top Bar
            TopAppBar(
                title = { Text("EMI Calculation Result") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBackground,
                    titleContentColor = TextPrimary
                )
            )
            
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                // Main EMI Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = DarkCard
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        GradientStart.copy(alpha = 0.2f),
                                        GradientEnd.copy(alpha = 0.1f)
                                    )
                                )
                            )
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Monthly EMI",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextSecondary
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = EMICalculator.formatCurrency(result.emiAmount, selectedCurrency),
                            style = MaterialTheme.typography.displayMedium,
                            color = NeonCyan,
                            fontWeight = FontWeight.Bold,
                            fontSize = 42.sp
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Summary Cards Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Principal Amount Card
                    SummaryCard(
                        title = "Principal",
                        value = EMICalculator.formatAmount(result.principalAmount, selectedCurrency),
                        color = NeonCyan,
                        modifier = Modifier.weight(1f)
                    )
                    
                    // Total Interest Card
                    SummaryCard(
                        title = "Interest",
                        value = EMICalculator.formatAmount(result.totalInterest, selectedCurrency),
                        color = PurpleAccent,
                        modifier = Modifier.weight(1f)
                    )
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Total Payable Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = DarkCard
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total Payable",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextSecondary
                        )
                        
                        Text(
                            text = EMICalculator.formatCurrency(result.totalPayable, selectedCurrency),
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Pie Chart Card
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
                            text = "Loan Breakdown",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        PieChart(
                            data = listOf(
                                PieChartData(
                                    label = "Principal Amount",
                                    value = result.principalAmount,
                                    color = NeonCyan
                                ),
                                PieChartData(
                                    label = "Total Interest",
                                    value = result.totalInterest,
                                    color = PurpleAccent
                                )
                            ),
                            currency = selectedCurrency
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // View Amortization Button
                GradientButton(
                    text = "View Amortization Schedule",
                    icon = Icons.Default.Timeline,
                    onClick = onNavigateToAmortization,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun SummaryCard(
    title: String,
    value: String,
    color: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = DarkCard
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                color = color,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


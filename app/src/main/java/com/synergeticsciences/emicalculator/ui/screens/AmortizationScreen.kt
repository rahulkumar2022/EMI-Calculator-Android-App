package com.synergeticsciences.emicalculator.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.synergeticsciences.emicalculator.data.model.AmortizationEntry
import com.synergeticsciences.emicalculator.ui.theme.*
import com.synergeticsciences.emicalculator.ui.viewmodel.EMIViewModel
import com.synergeticsciences.emicalculator.utils.EMICalculator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmortizationScreen(
    viewModel: EMIViewModel,
    onNavigateBack: () -> Unit
) {
    val emiResult by viewModel.emiResult.collectAsState()
    val selectedCurrency by viewModel.selectedCurrency.collectAsState()
    
    if (emiResult == null) {
        // If no result, go back
        onNavigateBack()
        return
    }
    
    val schedule = emiResult!!.amortizationSchedule
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top Bar
            TopAppBar(
                title = { Text("Amortization Schedule") },
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
            
            // Info Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = DarkCard
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Total Months",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                        Text(
                            text = "${schedule.size}",
                            style = MaterialTheme.typography.titleMedium,
                            color = NeonCyan,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Monthly EMI",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                        Text(
                            text = EMICalculator.formatAmount(schedule.firstOrNull()?.emiAmount ?: 0.0, selectedCurrency),
                            style = MaterialTheme.typography.titleMedium,
                            color = PurpleAccent,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Table Header
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = DarkCard
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TableHeaderCell("Month", Modifier.weight(0.7f))
                    TableHeaderCell("Principal", Modifier.weight(1f))
                    TableHeaderCell("Interest", Modifier.weight(1f))
                    TableHeaderCell("Balance", Modifier.weight(1f))
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Amortization List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(schedule) { index, entry ->
                    AmortizationRow(entry = entry, currency = selectedCurrency, index = index)
                }
                
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
fun TableHeaderCell(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = NeonCyan,
        fontWeight = FontWeight.Bold,
        modifier = modifier,
        textAlign = TextAlign.Center
    )
}

@Composable
fun AmortizationRow(
    entry: AmortizationEntry,
    currency: com.synergeticsciences.emicalculator.data.model.Currency,
    index: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (index % 2 == 0) DarkCard else DarkSurface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Month
            Text(
                text = "${entry.month}",
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(0.7f),
                textAlign = TextAlign.Center
            )
            
            // Principal
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = formatShortCurrency(entry.principalComponent, currency),
                    style = MaterialTheme.typography.bodySmall,
                    color = NeonCyan,
                    textAlign = TextAlign.Center
                )
            }
            
            // Interest
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = formatShortCurrency(entry.interestComponent, currency),
                    style = MaterialTheme.typography.bodySmall,
                    color = PurpleAccent,
                    textAlign = TextAlign.Center
                )
            }
            
            // Outstanding Balance
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = formatShortCurrency(entry.outstandingPrincipal, currency),
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

fun formatShortCurrency(amount: Double, currency: com.synergeticsciences.emicalculator.data.model.Currency): String {
    return when (currency.format) {
        com.synergeticsciences.emicalculator.data.model.CurrencyFormat.INDIAN -> {
            when {
                amount >= 10000000 -> String.format("%.1fCr", amount / 10000000)
                amount >= 100000 -> String.format("%.1fL", amount / 100000)
                amount >= 1000 -> String.format("%.1fK", amount / 1000)
                else -> String.format("%.0f", amount)
            }
        }
        com.synergeticsciences.emicalculator.data.model.CurrencyFormat.WESTERN -> {
            when {
                amount >= 1000000000 -> String.format("%.1fB", amount / 1000000000)
                amount >= 1000000 -> String.format("%.1fM", amount / 1000000)
                amount >= 1000 -> String.format("%.1fK", amount / 1000)
                else -> String.format("%.0f", amount)
            }
        }
        else -> String.format("%.0f", amount)
    }
}


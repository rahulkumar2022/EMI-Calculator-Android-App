package com.synergeticsciences.emicalculator.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.synergeticsciences.emicalculator.data.model.Currency
import com.synergeticsciences.emicalculator.data.model.SupportedCurrencies
import com.synergeticsciences.emicalculator.ui.theme.*
import com.synergeticsciences.emicalculator.ui.viewmodel.EMIViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: EMIViewModel,
    onNavigateBack: () -> Unit
) {
    val selectedCurrency by viewModel.selectedCurrency.collectAsState()
    var showCurrencyDialog by remember { mutableStateOf(false) }
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
                title = { Text("Settings") },
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
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp)
            ) {
                // App Info Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = DarkCard
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Calculate,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = NeonCyan
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "EMI Calculator",
                            style = MaterialTheme.typography.headlineSmall,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "Version 1.0.0",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "A modern, futuristic loan EMI calculator with instant calculations and detailed amortization schedules.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextTertiary,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Settings Items
                Text(
                    text = "App Settings",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextSecondary,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
                )
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = DarkCard
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SettingsItem(
                            icon = Icons.Default.Palette,
                            title = "Theme",
                            subtitle = "Futuristic Dark (Default)",
                            onClick = { /* Theme settings could be added here */ }
                        )
                        
                        Divider(color = DarkElevated, modifier = Modifier.padding(horizontal = 16.dp))
                        
                        SettingsItem(
                            icon = Icons.Default.AttachMoney,
                            title = "Currency",
                            subtitle = "${selectedCurrency.name} (${selectedCurrency.symbol})",
                            onClick = { showCurrencyDialog = true }
                        )
                        
                        Divider(color = DarkElevated, modifier = Modifier.padding(horizontal = 16.dp))
                        
                        SettingsItem(
                            icon = Icons.Default.Storage,
                            title = "Data Storage",
                            subtitle = "Local device storage",
                            onClick = { /* Storage settings */ }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // About Section
                Text(
                    text = "About",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextSecondary,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
                )
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = DarkCard
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SettingsItem(
                            icon = Icons.Default.Info,
                            title = "About App",
                            subtitle = "Learn more about this app",
                            onClick = { /* About screen */ }
                        )
                        
                        Divider(color = DarkElevated, modifier = Modifier.padding(horizontal = 16.dp))
                        
                        SettingsItem(
                            icon = Icons.Default.PrivacyTip,
                            title = "Privacy Policy",
                            subtitle = "Your data is stored locally",
                            onClick = { /* Privacy policy */ }
                        )
                        
                        Divider(color = DarkElevated, modifier = Modifier.padding(horizontal = 16.dp))
                        
                        SettingsItem(
                            icon = Icons.Default.StarRate,
                            title = "Rate This App",
                            subtitle = "Share your feedback",
                            onClick = { /* Rate app */ }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Footer
                Text(
                    text = "Made with ❤️ using Jetpack Compose",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTertiary,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
        
        // Currency Selection Dialog
        if (showCurrencyDialog) {
            CurrencySelectionDialog(
                selectedCurrency = selectedCurrency,
                onDismiss = { showCurrencyDialog = false },
                onCurrencySelected = { currency ->
                    viewModel.setCurrency(currency)
                    showCurrencyDialog = false
                }
            )
        }
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = NeonCyan,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextPrimary,
                    fontWeight = FontWeight.Medium
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }
        
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = TextTertiary,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun CurrencySelectionDialog(
    selectedCurrency: Currency,
    onDismiss: () -> Unit,
    onCurrencySelected: (Currency) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 500.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = DarkCard
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Dialog Title
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Select Currency",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextSecondary
                        )
                    }
                }
                
                Divider(color = DarkElevated)
                
                // Currency List
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .weight(1f, fill = false)
                ) {
                    SupportedCurrencies.ALL.forEach { currency ->
                        CurrencyItem(
                            currency = currency,
                            isSelected = currency.code == selectedCurrency.code,
                            onClick = { onCurrencySelected(currency) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CurrencyItem(
    currency: Currency,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(
                if (isSelected) NeonCyan.copy(alpha = 0.1f) else androidx.compose.ui.graphics.Color.Transparent
            )
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            // Currency Symbol
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = if (isSelected) NeonCyan.copy(alpha = 0.2f) else DarkElevated,
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = currency.symbol,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isSelected) NeonCyan else TextSecondary,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    text = currency.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isSelected) NeonCyan else TextPrimary,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = currency.code,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }
        
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Selected",
                tint = NeonCyan,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


package com.synergeticsciences.emicalculator.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.synergeticsciences.emicalculator.data.model.Currency
import com.synergeticsciences.emicalculator.ui.theme.*
import com.synergeticsciences.emicalculator.utils.EMICalculator

data class PieChartData(
    val label: String,
    val value: Double,
    val color: Color
)

@Composable
fun PieChart(
    data: List<PieChartData>,
    currency: Currency,
    modifier: Modifier = Modifier
) {
    val animatedProgress = remember { Animatable(0f) }
    
    LaunchedEffect(data) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
    }
    
    val total = data.sumOf { it.value }
    
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Pie chart canvas
        Canvas(
            modifier = Modifier.size(200.dp)
        ) {
            val canvasSize = size.minDimension
            val radius = canvasSize / 2
            val strokeWidth = 40.dp.toPx()
            
            var currentAngle = -90f
            
            data.forEach { item ->
                val sweepAngle = ((item.value / total).toFloat() * 360f) * animatedProgress.value
                
                // Draw arc
                drawArc(
                    color = item.color,
                    startAngle = currentAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = Offset(
                        (size.width - canvasSize) / 2 + strokeWidth / 2,
                        (size.height - canvasSize) / 2 + strokeWidth / 2
                    ),
                    size = Size(canvasSize - strokeWidth, canvasSize - strokeWidth),
                    style = Stroke(
                        width = strokeWidth,
                        cap = StrokeCap.Round
                    )
                )
                
                currentAngle += sweepAngle
            }
            
            // Draw center circle for donut effect
            drawCircle(
                color = DarkCard,
                radius = radius - strokeWidth,
                center = center
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Legend
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            data.forEach { item ->
                LegendItem(
                    color = item.color,
                    label = item.label,
                    value = EMICalculator.formatCurrency(item.value, currency),
                    percentage = ((item.value / total) * 100).toInt()
                )
            }
        }
    }
}

@Composable
fun LegendItem(
    color: Color,
    label: String,
    value: String,
    percentage: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Color indicator
            Canvas(modifier = Modifier.size(16.dp)) {
                drawCircle(color = color)
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
        }
        
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "$percentage%",
                style = MaterialTheme.typography.bodySmall,
                color = color
            )
        }
    }
}


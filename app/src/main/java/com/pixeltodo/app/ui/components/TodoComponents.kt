package com.pixeltodo.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pixeltodo.app.domain.model.Priority
import com.pixeltodo.app.domain.model.Todo
import com.pixeltodo.app.ui.theme.*

@Composable
fun TodoItem(
    todo: Todo,
    onToggleComplete: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val priorityColor = when (todo.priority) {
        Priority.HIGH -> PixelRed
        Priority.MEDIUM -> PixelYellow
        Priority.LOW -> PixelGreen
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .border(2.dp, if (todo.isCompleted) PixelGray else PixelBlue, RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(containerColor = PixelCardBackground),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Checkbox pixel style
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .border(2.dp, if (todo.isCompleted) PixelGreen else PixelGray, RoundedCornerShape(4.dp))
                    .background(if (todo.isCompleted) PixelGreen else PixelCardBackground)
                    .clickable { onToggleComplete() },
                contentAlignment = Alignment.Center
            ) {
                if (todo.isCompleted) {
                    Text("✓", color = PixelBackground, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = todo.title,
                    color = if (todo.isCompleted) PixelGray else PixelText,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 16.sp,
                    textDecoration = if (todo.isCompleted) androidx.compose.ui.text.style.TextDecoration.LineThrough else null
                )
                if (todo.description.isNotEmpty()) {
                    Text(
                        text = todo.description,
                        color = PixelGray,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                }
                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Priority pixel
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(priorityColor, RoundedCornerShape(2.dp))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    if (todo.isAlarmEnabled) {
                        Icon(
                            Icons.Default.Alarm,
                            contentDescription = "Alarm",
                            tint = PixelYellow,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    if (todo.isWeatherAlarmEnabled) {
                        Icon(
                            Icons.Default.Cloud,
                            contentDescription = "Weather",
                            tint = PixelBlue,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PixelButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color = PixelBlue
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .border(2.dp, color, RoundedCornerShape(4.dp)),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            disabledContainerColor = PixelGray
        ),
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = text,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PixelTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontFamily = FontFamily.Monospace) },
        modifier = modifier
            .border(2.dp, PixelBlue, RoundedCornerShape(4.dp)),
        textStyle = TextStyle(fontFamily = FontFamily.Monospace),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PixelBlue,
            unfocusedBorderColor = PixelGray,
            focusedTextColor = PixelText,
            unfocusedTextColor = PixelText,
            cursorColor = PixelYellow
        ),
        singleLine = singleLine,
        shape = RoundedCornerShape(4.dp)
    )
}

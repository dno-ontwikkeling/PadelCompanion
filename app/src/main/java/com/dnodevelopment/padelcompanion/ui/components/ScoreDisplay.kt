package com.dnodevelopment.padelcompanion.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material.IconButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.MaterialTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ClockDisplay() {
    var currentTime by remember { mutableStateOf("") }

    // Format time as HH:mm:ss
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    // Update the time every second
    LaunchedEffect(key1 = true) {
        while (true) {
            currentTime = formatter.format(Date())
            delay(1000) // Update every second
        }
    }

    // Display the time
    Text(
        text = currentTime,
        fontSize = 14.sp,
        color = Color.White,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ScoreDisplay(
    team1Score: Int,
    team2Score: Int,
    team1Advantage: Boolean,
    team2Advantage: Boolean,
    serveRight: Boolean,
    team1Serving: Boolean,
    team1GamePoints: Int,
    team2GamePoints: Int,
    team1SetScore: Int,
    team2SetScore: Int,
    isTiebreak: Boolean,
    onTeam1Click: () -> Unit,
    onTeam2Click: () -> Unit,
    onReset: () -> Unit,
    onUndo: () -> Unit,
    getScoreDisplay: (Int) -> String
) {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Top icon buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onReset,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reset",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = onUndo,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Undo,
                        contentDescription = "Undo",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            // Score display
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Team 1 Score
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = onTeam1Click)
                ) {
                    // Game points (top left)
                    Text(
                        text = team1GamePoints.toString(),
                        fontSize = 16.sp,
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(x = 4.dp, y = 4.dp)
                    )
                    // Set score (bottom left)
                    Text(
                        text = team1SetScore.toString(),
                        fontSize = 16.sp,
                        color = Color.Blue,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .offset(x = 4.dp, y = (-4).dp)
                    )
                    // Score
                    Text(
                        text = if (isTiebreak) team1Score.toString() else getScoreDisplay(team1Score),
                        fontSize = 40.sp,
                        color = Color(0xFFEEEEEE),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    // Serve indicator (top right, tight)
                    if (team1Serving) {
                        Text(
                            text = if (serveRight) "R" else "L",
                            color = Color.Yellow,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = (-2).dp, y = 1.dp)
                        )
                    }
                    // Advantage indicator (bottom right, tight)
                    if (team1Advantage) {
                        Box(
                            modifier = Modifier
                                .size(9.dp)
                                .clip(CircleShape)
                                .background(Color.Blue)
                                .align(Alignment.BottomEnd)
                                .offset(x = (-1).dp, y = (-1).dp)
                        )
                    }
                }
                // Dash
                Text(
                    text = "-",
                    fontSize = 40.sp,
                    color = Color(0xFFEEEEEE),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                // Team 2 Score
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = onTeam2Click)
                ) {
                    // Game points (top left)
                    Text(
                        text = team2GamePoints.toString(),
                        fontSize = 16.sp,
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(x = 4.dp, y = 4.dp)
                    )
                    // Set score (bottom left)
                    Text(
                        text = team2SetScore.toString(),
                        fontSize = 16.sp,
                        color = Color.Blue,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .offset(x = 4.dp, y = (-4).dp)
                    )
                    // Score
                    Text(
                        text = if (isTiebreak) team2Score.toString() else getScoreDisplay(team2Score),
                        fontSize = 40.sp,
                        color = Color(0xFFEEEEEE),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    // Serve indicator (top right, tight)
                    if (!team1Serving) {
                        Text(
                            text = if (serveRight) "R" else "L",
                            color = Color.Yellow,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = (-2).dp, y = 1.dp)
                        )
                    }
                    // Advantage indicator (bottom right, tight)
                    if (team2Advantage) {
                        Box(
                            modifier = Modifier
                                .size(9.dp)
                                .clip(CircleShape)
                                .background(Color.Blue)
                                .align(Alignment.BottomEnd)
                                .offset(x = (-1).dp, y = (-1).dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))

            // Add the clock at the bottom middle
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                ClockDisplay()
            }
        }
    }
}